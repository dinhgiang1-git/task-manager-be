package dinhgiang.be.model.dto.request.project;

import lombok.Data;

@Data
public class ProjectMemberCreateRequest {
    private Long projectId;
    private Long accountId;
    private Long projectRoleId;
}
