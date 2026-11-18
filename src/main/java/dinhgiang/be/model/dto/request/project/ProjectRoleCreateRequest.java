package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRoleCreateRequest {

    @NotBlank(message = "Mã vai trò không được để trống")
    private String code;

    @NotBlank(message = "Tên vai trò không được để trống")
    private String name;

    @NotNull(message = "Mã dự án không được để trống")
    private Long projectId;

    private Boolean isActive;
}
