package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRoleCreateRequest {

    @NotBlank(message = "Mã vai trò không được để trống")
    private String code;

    @NotBlank(message = "Tên vai trò không được để trống")
    private String name;

    private Boolean isActive;
}
