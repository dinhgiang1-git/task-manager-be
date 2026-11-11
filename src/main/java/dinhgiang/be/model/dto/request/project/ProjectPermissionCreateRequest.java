package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectPermissionCreateRequest {
    @NotBlank(message = "Mã quyền không được để trống")
    private String code;
    @NotBlank(message = "Tên quyền không được để trống")
    private String name;
    private Boolean isActive;
}
