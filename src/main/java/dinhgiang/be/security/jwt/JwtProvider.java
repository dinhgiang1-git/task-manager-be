package dinhgiang.be.security.jwt;

import dinhgiang.be.model.entity.account.AccountRole;
import dinhgiang.be.model.entity.account.Permission;
import dinhgiang.be.model.entity.account.Role;
import dinhgiang.be.model.entity.account.RolePermission;
import dinhgiang.be.repository.AccountRoleRepository;
import dinhgiang.be.repository.PermissionRepository;
import dinhgiang.be.repository.RolePermissionRepository;
import dinhgiang.be.repository.RoleRepository;
import dinhgiang.be.security.model.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final AccountRoleRepository accountRoleRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    // Tạo AccessToken chứa thông tin username, userId, roles, permissions
    public String generateAccessToken(String username, Long accountId) {
        Date now = new Date();
        Date expiryDate = new Date(
                now.getTime() + jwtProperties.getAccessTokenExpiration()
        );

        List<String> roles = new ArrayList<>();
        Set<String> permissionSet = new HashSet<>();
        List<AccountRole> accountRoles = accountRoleRepository.findAllByAccountId(accountId);
        for (AccountRole accountRole : accountRoles) {
            Role role;
            role = roleRepository.findById(accountRole.getRoleId()).orElse(null);
            if (role == null || !Boolean.TRUE.equals(role.getIsActive())) {
                continue;
            }

            roles.add(role.getCode());
            List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleId(role.getId());
            for (RolePermission rolePermission : rolePermissions) {
                Permission permission = permissionRepository.findById(rolePermission.getPermissionId()).orElse(null);
                if (permission != null && permission.getCode() != null && !permission.getCode().isBlank() && Boolean.TRUE.equals(permission.getIsActive())) {
                    permissionSet.add(permission.getCode());
                }
            }
        }

        return Jwts.builder()
                .header()
                    .add("typ", "JWT")
                    .and()
                .subject(accountId.toString())
                .claim("username", username)
                .claim("roles", roles)
                .claim("permissions", new ArrayList<>(permissionSet))
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getAccessSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Tạo Refresh Token độc lập
     */
    public String generateRefreshToken(String username, Long accountId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getRefreshTokenExpiration());

        return Jwts.builder()
                .header()
                    .add("typ", "JWT")
                    .and()
                .subject(accountId.toString())
                .claim("username", username)
                .claim("jti", UUID.randomUUID().toString())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getRefreshSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Trích xuất thông tin UserPrincipal từ token đã xác thực
     */
    public UserPrincipal parseToken(String token) {
        Claims claims = parseClaims(token);

        String username = claims.get("username", String.class);
        Long accountId = Long.valueOf(claims.getSubject());

        List<String> roles = extractStringList(claims, "roles");
        List<String> permissions = extractStringList(claims, "permissions");

        //Tạo authorities từ roles và permissions để Spring Security phân quyền
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        return new UserPrincipal(accountId, username, null, roles, permissions, authorities);
    }

    // -------------------Helper-------------------------------
    private SecretKey getAccessSigningKey() {
        return buildKey(jwtProperties.getSecretKey());
    }

    private SecretKey getRefreshSigningKey() {
        return buildKey(jwtProperties.getRefreshKey());
    }

    private SecretKey buildKey(String secret) {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (Exception e) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // -------------------Helper Parse Claims--------------
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getAccessSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims parseRefreshClaims(String token) {
        return Jwts.parser()
                .verifyWith(getRefreshSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private List<String> extractStringList(Claims claims, String claimName) {
        Object raw = claims.get(claimName);

        if (raw == null) {
            return Collections.emptyList();
        }

        if (!(raw instanceof Collection<?> collection)) {
            throw new JwtException("Claim phải là danh sách: " + claimName);
        }

        return collection.stream()
                .map(item -> {
                    if (item instanceof String value && !value.isBlank()) {
                        return value;
                    }
                    throw new JwtException(
                            "Claim phải chứa code dạng chuỗi: " + claimName
                    );
                })
                .toList();
    }

    // ------------------Helper check validate ----------------------------------
    public boolean validateAccessToken(String token) {
        return validateToken(token, getAccessSigningKey());
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, getRefreshSigningKey());
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Chữ ký JWT không hợp lệ: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token đã hết hạn: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token không được hỗ trợ: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Chuỗi JWT claims rỗng hoặc null: {}", e.getMessage());
        }
        return false;
    }

}
