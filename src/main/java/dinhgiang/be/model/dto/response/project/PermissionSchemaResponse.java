package dinhgiang.be.model.dto.response.project;

import lombok.Data;

@Data
public class PermissionSchemaResponse {
    private Long id;
    private String code;
    private String name;
    private Boolean isActive;
    private Boolean isDefault;
}
