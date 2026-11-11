package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.dto.request.auth.LoginRequest;
import dinhgiang.be.model.dto.request.auth.LogoutRequest;
import dinhgiang.be.model.dto.response.auth.LoginResponse;
import dinhgiang.be.model.entity.account.Account;
import dinhgiang.be.model.entity.auth.RefreshToken;
import dinhgiang.be.repository.AccountRepository;
import dinhgiang.be.repository.RefreshTokenRepository;
import dinhgiang.be.security.jwt.JwtProperties;
import dinhgiang.be.security.jwt.JwtProvider;
import dinhgiang.be.security.model.UserPrincipal;
import dinhgiang.be.security.util.SecurityUtil;
import dinhgiang.be.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;


    @Transactional
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("Tài khoản hoặc mật khẩu không chính xác"));

        boolean matched = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (!matched) throw new BusinessException("Tài khoản hoặc mật khẩu không chính xác");

        account.setLastLoginAt(Instant.now());
        account = accountRepository.save(account);

        String accessTokenKey = jwtProvider.generateAccessToken(account.getUsername(), account.getId());
        String refreshTokenKey = jwtProvider.generateRefreshToken(account.getUsername(), account.getId());

        RefreshToken refreshToken = RefreshToken.builder()
                .accountId(account.getId())
                .tokenHash(SecurityUtil.hashToken(refreshTokenKey))
                .expiresAt(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiration()))
                .build();
        refreshTokenRepository.save(refreshToken);

        UserPrincipal userPrincipal = jwtProvider.parseToken(accessTokenKey);

        LoginResponse response = LoginResponse.builder()
                .accessToken(accessTokenKey)
                .refreshToken(refreshTokenKey)
                .expiresAt(jwtProperties.getAccessTokenExpiration())
                .tokenType("Bearer")
                .account(LoginResponse.Account.builder()
                        .id(userPrincipal.getId())
                        .username(userPrincipal.getUsername())
                        .roles(userPrincipal.getRoles())
                        .permissions(userPrincipal.getPermissions())
                        .build())
                .build();

        return ApiResponse.success(response, "Đăng nhập thành công");
    }

    @Transactional
    public ApiResponse<Void> logout(LogoutRequest request) {
        String tokenHash = SecurityUtil.hashToken(request.getRefreshToken());

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new BusinessException("Mã refresh token không tồn tại!"));

        if (refreshToken.getRevokedAt() != null) {
            throw new BusinessException("Mã refresh token đã bị thu hồi!");
        }

        refreshToken.setRevokedAt(Instant.now());
        refreshTokenRepository.save(refreshToken);

        return ApiResponse.success("Đăng xuất thành công");
    }
}
