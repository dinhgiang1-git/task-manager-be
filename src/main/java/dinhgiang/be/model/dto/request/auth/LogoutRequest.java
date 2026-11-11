package dinhgiang.be.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LogoutRequest {

    @NotBlank(message = "Access Token không được để trống")
    private String accessToken;

    @NotBlank(message = "Refresh Token không được để trống")
    private String refreshToken;
}
