package dinhgiang.be.model.dto.response.project;

import lombok.Data;

import java.time.Instant;

@Data
public class ProjectMemberListResponse {
    private Long id;
    private Instant joinAt;

    //Project:
    private Long projectId;

    //Account:
    private Long accountId;
    private String username;
    private String displayName;
    private String email;
}
