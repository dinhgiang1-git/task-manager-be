package dinhgiang.be.initializer.data.workitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkItemCommentRequest {
    @NotNull(message = "Mã công việc không được để trống")
    private Long workItemId;

    @NotBlank(message = "Nội dung bình luận không được để trống")
    private String body;
}
