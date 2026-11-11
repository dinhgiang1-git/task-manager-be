package dinhgiang.be.initializer.data.project;

import java.util.List;

public class PermissionSchemaData {
    public static final List<PermissionSchemaSeed> DATA = List.of(
            new PermissionSchemaSeed("DEFAULT_SCHEMA", "Bảng quyền mặc định", true, true)
    );
}
