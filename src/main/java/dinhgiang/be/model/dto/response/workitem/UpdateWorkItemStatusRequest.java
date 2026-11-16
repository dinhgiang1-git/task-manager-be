package dinhgiang.be.model.dto.response.workitem;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateWorkItemStatusRequest {
    @NotNull(message = "Công việc không được để trống")
    private Long workItemId;

    @NotNull(message = "Trạng thái công việc không được để trống")
    private Long workItemStatusId;
}
