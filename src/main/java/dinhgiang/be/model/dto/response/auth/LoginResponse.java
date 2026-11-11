package dinhgiang.be.model.dto.response.auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresAt;
    private String tokenType;
    private Account account;

    @Data
    @Builder
    public static class Account {
        private Long id;
        private String username;
        private List<String> roles;
        private List<String> permissions;
    }
}
