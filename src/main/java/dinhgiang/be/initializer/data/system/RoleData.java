package dinhgiang.be.initializer.data.system;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleData {
    @Builder.Default
    private String code = "TMS_ADMIN";
    @Builder.Default
    private String name = "Quản trị hệ thống";
}
