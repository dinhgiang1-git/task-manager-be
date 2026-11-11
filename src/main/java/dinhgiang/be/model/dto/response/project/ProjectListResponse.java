package dinhgiang.be.model.dto.response.project;

import lombok.Data;

@Data
public class ProjectListResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String endAt;
}
