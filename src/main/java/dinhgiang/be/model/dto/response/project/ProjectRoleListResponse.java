package dinhgiang.be.model.dto.response.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRoleListResponse {
    private Long id;
    private String code;
    private String name;
    private Boolean isActive;
}
