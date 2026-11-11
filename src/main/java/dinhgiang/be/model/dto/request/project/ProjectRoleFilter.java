package dinhgiang.be.model.dto.request.project;

import lombok.Data;

@Data
public class ProjectRoleFilter {
    private String keyword;
    private Boolean isActive;
}
