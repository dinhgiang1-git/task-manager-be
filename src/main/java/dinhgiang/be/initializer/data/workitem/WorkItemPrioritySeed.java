package dinhgiang.be.initializer.data.workitem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkItemPrioritySeed {
    private String code;
    private String name;
    private Long shortOder;
    private Boolean isActive;
    private Boolean isDefault;
}
