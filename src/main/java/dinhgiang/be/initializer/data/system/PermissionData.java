package dinhgiang.be.initializer.data.system;

import java.util.List;

public class PermissionData {
    public static final List<PermissionSeed> DATA = List.of(
            // TÀI KHOẢN
            new PermissionSeed("TMS_ACCOUNT_MENU", "MENU", "TMS_ACCOUNT", "MENU TÀI KHOẢN", null, true),
            new PermissionSeed("TMS_ACCOUNT_LIST", "READ", "TMS_ACCOUNT", "DANH SÁCH TÀI KHOẢN", "TMS_ACCOUNT_MENU", true),
            new PermissionSeed("TMS_ACCOUNT_DETAIL", "READ", "TMS_ACCOUNT", "CHI TIẾT TÀI KHOẢN", "TMS_ACCOUNT_MENU", true),
            new PermissionSeed("TMS_ACCOUNT_CREATE", "CREATE", "TMS_ACCOUNT", "TẠO TÀI KHOẢN", "TMS_ACCOUNT_MENU", true),
            new PermissionSeed("TMS_ACCOUNT_UPDATE", "UPDATE", "TMS_ACCOUNT", "CẬP NHẬT TÀI KHOẢN", "TMS_ACCOUNT_MENU", true),
            new PermissionSeed("TMS_ACCOUNT_CHANGE_STATUS", "UPDATE", "TMS_ACCOUNT", "KHÓA / MỞ KHÓA TÀI KHOẢN", "TMS_ACCOUNT_MENU", true),
            new PermissionSeed("TMS_ACCOUNT_ASSIGN_ROLE", "ASSIGN", "TMS_ACCOUNT", "GÁN / THU HỒI VAI TRÒ HỆ THỐNG", "TMS_ACCOUNT_MENU", true),

            // VAI TRÒ VÀ PHÂN QUYỀN
            new PermissionSeed("TMS_ROLE_MENU", "MENU", "TMS_ROLE", "MENU VAI TRÒ VÀ PHÂN QUYỀN", null, true),
            new PermissionSeed("TMS_ROLE_LIST", "READ", "TMS_ROLE", "DANH SÁCH VAI TRÒ", "TMS_ROLE_MENU", true),
            new PermissionSeed("TMS_ROLE_DETAIL", "READ", "TMS_ROLE", "CHI TIẾT VAI TRÒ", "TMS_ROLE_MENU", true),
            new PermissionSeed("TMS_ROLE_ASSIGN_PERMISSION", "ASSIGN", "TMS_ROLE", "GÁN / THU HỒI QUYỀN CHO VAI TRÒ", "TMS_ROLE_MENU", true),

            // DANH MỤC QUYỀN
            new PermissionSeed("TMS_PERMISSION_MENU", "MENU", "TMS_PERMISSION", "MENU DANH MỤC QUYỀN", "TMS_PERMISSION_MENU", true),
            new PermissionSeed("TMS_PERMISSION_LIST", "READ", "TMS_PERMISSION", "DANH SÁCH QUYỀN", "TMS_PERMISSION_MENU", true),
            new PermissionSeed("TMS_PERMISSION_DETAIL", "READ", "TMS_PERMISSION", "CHI TIẾT QUYỀN", "TMS_PERMISSION_MENU", true),

            // DỰ ÁN
            new PermissionSeed("TMS_PROJECT_MENU", "MENU", "TMS_PROJECT", "MENU DỰ ÁN", null, true),
            new PermissionSeed("TMS_PROJECT_LIST", "READ", "TMS_PROJECT", "DANH SÁCH DỰ ÁN", "TMS_PROJECT_MENU", true),
            new PermissionSeed("TMS_PROJECT_DETAIL", "READ", "TMS_PROJECT", "CHI TIẾT DỰ ÁN", "TMS_PROJECT_MENU", true),
            new PermissionSeed("TMS_PROJECT_CREATE", "CREATE", "TMS_PROJECT", "TẠO DỰ ÁN", "TMS_PROJECT_MENU", true),
            new PermissionSeed("TMS_PROJECT_UPDATE", "UPDATE", "TMS_PROJECT", "CẬP NHẬT THÔNG TIN DỰ ÁN", "TMS_PROJECT_MENU", true),

            // CÔNG VIỆC
            new PermissionSeed("TMS_WORK_ITEM_MENU", "MENU", "TMS_WORK_ITEM", "MENU CÔNG VIỆC", null, true),
            new PermissionSeed("TMS_WORK_ITEM_LIST", "READ", "TMS_WORK_ITEM", "DANH SÁCH CÔNG VIỆC", "TMS_WORK_ITEM_MENU", true),
            new PermissionSeed("TMS_WORK_ITEM_DETAIL", "READ", "TMS_WORK_ITEM", "CHI TIẾT CÔNG VIỆC", "TMS_WORK_ITEM_MENU", true),
            new PermissionSeed("TMS_WORK_ITEM_CREATE", "CREATE", "TMS_WORK_ITEM", "TẠO CÔNG VIỆC", "TMS_WORK_ITEM_MENU", true),
            new PermissionSeed("TMS_WORK_ITEM_ASSIGN", "ASSIGN", "TMS_WORK_ITEM", "PHÂN CÔNG CÔNG VIỆC", "TMS_WORK_ITEM_MENU", true),
            new PermissionSeed("TMS_WORK_ITEM_UPDATE", "UPDATE", "TMS_WORK_ITEM", "SỬA NỘI DUNG CÔNG VIỆC", "TMS_WORK_ITEM_MENU", true),
            new PermissionSeed("TMS_WORK_ITEM_REASSIGN", "ASSIGN", "TMS_WORK_ITEM", "ĐIỀU CHỈNH PHÂN CÔNG", "TMS_WORK_ITEM_MENU", true),
            new PermissionSeed("TMS_WORK_ITEM_CHANGE_STATUS", "TRANSITION", "TMS_WORK_ITEM", "CẬP NHẬT TRẠNG THÁI THỰC HIỆN", "TMS_WORK_ITEM_MENU", true),

            // TỆP BÁO CÁO
            new PermissionSeed("TMS_ATTACHMENT_MENU", "MENU", "TMS_ATTACHMENT", "MỤC TỆP TRONG CÔNG VIỆC", null, true),
            new PermissionSeed("TMS_ATTACHMENT_LIST", "READ", "TMS_ATTACHMENT", "DANH SÁCH TỆP BÁO CÁO", "TMS_ATTACHMENT_MENU", true),
            new PermissionSeed("TMS_ATTACHMENT_DOWNLOAD", "DOWNLOAD", "TMS_ATTACHMENT", "TẢI TỆP BÁO CÁO", "TMS_ATTACHMENT_MENU", true),
            new PermissionSeed("TMS_ATTACHMENT_UPLOAD", "UPLOAD", "TMS_ATTACHMENT", "ĐÍNH KÈM BÁO CÁO KẾT QUẢ", "TMS_ATTACHMENT_MENU", true),

            // DASHBOARD
            new PermissionSeed("TMS_DASHBOARD_MENU", "MENU", "TMS_DASHBOARD", "MENU DASHBOARD QUẢN LÝ", null, true),
            new PermissionSeed("TMS_DASHBOARD_PROJECT_PROGRESS", "READ", "TMS_DASHBOARD", "TIẾN ĐỘ TỔNG QUAN DỰ ÁN", "TMS_DASHBOARD_MENU", true),
            new PermissionSeed("TMS_DASHBOARD_MEMBER_PROGRESS", "READ", "TMS_DASHBOARD", "TIẾN ĐỘ THEO THÀNH VIÊN", "TMS_DASHBOARD_MENU", true),
            new PermissionSeed("TMS_DASHBOARD_ON_TIME_RATE", "READ", "TMS_DASHBOARD", "TỶ LỆ HOÀN THÀNH ĐÚNG HẠN", "TMS_DASHBOARD_MENU", true),
            new PermissionSeed("TMS_DASHBOARD_OVERDUE", "READ", "TMS_DASHBOARD", "THỐNG KÊ CÔNG VIỆC TRỄ HẠN", "TMS_DASHBOARD_MENU", true),

            // LỊCH SỬ CÔNG VIỆC
            new PermissionSeed("TMS_TASK_HISTORY_MENU", "MENU", "TMS_TASK_HISTORY", "MỤC LỊCH SỬ CÔNG VIỆC", null, true),
            new PermissionSeed("TMS_TASK_HISTORY_LIST", "READ", "TMS_TASK_HISTORY", "XEM LỊCH SỬ THAY ĐỔI CÔNG VIỆC", "TMS_TASK_HISTORY_MENU", true),

            // KHO LƯU TRỮ
            new PermissionSeed("TMS_ARCHIVE_MENU", "MENU", "TMS_ARCHIVE", "MENU KHO LƯU TRỮ", null, true),
            new PermissionSeed("TMS_ARCHIVE_LIST", "READ", "TMS_ARCHIVE", "DANH SÁCH CÔNG VIỆC LƯU TRỮ", "TMS_ARCHIVE_MENU", true),
            new PermissionSeed("TMS_ARCHIVE_DETAIL", "READ", "TMS_ARCHIVE", "CHI TIẾT CÔNG VIỆC LƯU TRỮ", "TMS_ARCHIVE_MENU", true)
    );
}
