package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectPermissionUpdateRequest {
    @NotNull(message = "Id không được để trống")
    private Long id;
    private String code;
    private String name;
    private Boolean isActive;
}
