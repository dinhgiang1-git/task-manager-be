package dinhgiang.be.model.dto.request.workitem;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkItemFilterRequest {
    @NotNull
    private Long projectId;
    private String keyword;
}
