package dinhgiang.be.model.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountCreateRequest {
    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Tên hiển thị không được để trống")
    private String displayName;

    @NotBlank(message = "Password không được để trống")
    private String password;

    private String email;
}
