package dinhgiang.be.model.dto.request.workitem;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class WorkItemAssignRequest {
    @NotNull
    private Long workItemId;
    @NotNull
    private Set<Long> accountId;
}
