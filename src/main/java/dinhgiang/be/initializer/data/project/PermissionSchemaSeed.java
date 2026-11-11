package dinhgiang.be.initializer.data.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionSchemaSeed {
    private String code;
    private String name;
    private Boolean isActive;
    private Boolean isDefault;
}
