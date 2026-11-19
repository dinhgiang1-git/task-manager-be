package dinhgiang.be.model.dto.response.workitem;

import lombok.Data;

@Data
public class WorkItemTypeResponse {
    private Long id;
    private String code;
    private String description;
    private Boolean isActive;
    private Boolean isDefault;
}
