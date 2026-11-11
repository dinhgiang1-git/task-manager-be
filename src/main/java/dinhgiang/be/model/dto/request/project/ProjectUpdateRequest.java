package dinhgiang.be.model.dto.request.project;

import lombok.Data;

@Data
public class ProjectUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private String endAt;
}
