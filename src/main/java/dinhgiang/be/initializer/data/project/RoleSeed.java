package dinhgiang.be.initializer.data.project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleSeed {
    private String code;
    private String name;
    private Boolean isActive;
    private Boolean isDefault;
}
