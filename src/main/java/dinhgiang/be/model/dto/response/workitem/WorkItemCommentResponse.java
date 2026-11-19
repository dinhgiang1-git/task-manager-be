package dinhgiang.be.model.dto.response.workitem;

import lombok.Data;

@Data
public class WorkItemCommentResponse {
    private Long id;
    private Long workItemId;
    private String body;

    private Long authorId;
    private String authorName;
    private String authorEmail;
}
