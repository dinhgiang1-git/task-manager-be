package dinhgiang.be.initializer.data.workitem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkItemStatusSeed {
    private String code;
    private String description;
    private Boolean isActive;
    private Boolean isDefault;
}
