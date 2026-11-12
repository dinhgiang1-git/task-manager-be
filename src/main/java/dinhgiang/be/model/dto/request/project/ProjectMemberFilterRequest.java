package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberFilterRequest {
    @NotNull
    private Long projectId;
    private String keyword;
}
