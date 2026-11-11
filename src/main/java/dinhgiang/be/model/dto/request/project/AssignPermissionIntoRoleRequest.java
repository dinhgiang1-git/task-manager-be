package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AssignPermissionIntoRoleRequest {
    @NotNull(message = "Id vai trò không được để trống")
    private Long projectRoleId;

    @NotNull(message = "Bảng thư viện quyền không được để trống")
    private Long projectPermissionSchemaId;

    private List<Long> projectPermissionIds;
}
