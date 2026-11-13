package dinhgiang.be.initializer.data.workitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemTypeSeed {
    private String code;
    private String description;
    private Boolean isActive;
    private Boolean isDefault;
}
