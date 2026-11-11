package dinhgiang.be.initializer.data.system;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionSeed {
    private String code;
    private String actionName;
    private String resourceName;
    private String description;
    private String parentCode;
    private Boolean isActive;
}
