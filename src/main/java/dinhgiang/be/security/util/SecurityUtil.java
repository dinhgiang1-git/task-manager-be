package dinhgiang.be.security.util;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.security.model.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Optional;

public class SecurityUtil {

    public static Optional<UserPrincipal> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal userPrincipal) {
            return Optional.of(userPrincipal);
        }

        return Optional.empty();
    }

    public static Optional<Long> getCurrentAccountId() {
        return getCurrentUser().map(UserPrincipal::getId);
    }

    public static Long getRequireAccountId() {
        return getCurrentAccountId().orElseThrow(() -> new BusinessException("Tài khoản không tồn tại"));
    }


    public static String hashToken(String token) {
        if (token == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi thuật toán SHA-256", e);
        }
    }
}
