package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberUpdateRequest {
    @NotNull
    private Long projectId;
    @NotNull
    private Long accountId;
    @NotNull
    private Long projectRoleId;

}
