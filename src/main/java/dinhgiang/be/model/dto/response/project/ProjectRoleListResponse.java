package dinhgiang.be.model.dto.response.project;

import lombok.Data;

@Data
public class ProjectRoleListResponse {
    private Long id;
    private String code;
    private String name;
    private Boolean isActive;
}
