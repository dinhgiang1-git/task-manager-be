package dinhgiang.be.initializer.data.project;

import java.util.List;

public class RoleData {
    public static final List<RoleSeed> DATA = List.of(
            new RoleSeed("MANAGER", "Quản lý dự án", true, true),
            new RoleSeed("TESTER", "Kiểm thử viên", true, true),
            new RoleSeed("MEMBER", "Thành viên", true, true)
    );
}
