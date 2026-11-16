package dinhgiang.be.model.dto.response.workitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MyWorkItemListResponse {
    private Long id;
    private Long projectId;
    private String workItemCode;
    private String title;
    private Long parentId;
    private LocalDate dueDate;

    private Long accountId;
    private String accountDisplayName;
    private String accountEmail;

    private Long workItemTypeId;
    private String workItemTypeCode;

    private Long priorityId;
    private String priorityCode;
    private String priorityName;

    private Long workItemStatusId;
    private String workItemStatusCode;
}
