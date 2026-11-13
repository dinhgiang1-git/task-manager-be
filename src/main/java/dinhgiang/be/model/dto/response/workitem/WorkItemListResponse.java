package dinhgiang.be.model.dto.response.workitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemListResponse {
    private Long id;
    private Long projectId;
    private String workItemCode;
    private String title;
    private Long parentId;
    private LocalDate dueDate;
    private Long ownerId;

    private Long workItemTypeId;
    private String workItemTypeCode;

    private Long priorityId;
    private String priorityCode;
    private String priorityName;

    private Long workItemStatusId;
    private String workItemStatusCode;

    private List<WorkItemListResponse> children = new ArrayList<>();

}
