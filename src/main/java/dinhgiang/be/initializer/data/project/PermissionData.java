package dinhgiang.be.initializer.data.project;


import java.util.List;

public class PermissionData {
    public static final List<PermissionSeed> DATA = List.of(
            new PermissionSeed(
                    "XEM_TAT_CA_VIEC",
                    "Xem mọi việc trong dự án",
                    true,
                    true
            ),
            new PermissionSeed(
                    "XEM_VIEC",
                    "Xem việc liên quan đến mình",
                    true,
                    true
            ),
            new PermissionSeed(
                    "TAO_EPIC",
                    "Tạo Epic",
                    true,
                    true
            ),
            new PermissionSeed(
                    "TAO_STORY_TASK",
                    "Tạo Story / Task",
                    true,
                    true
            ),
            new PermissionSeed(
                    "TAO_SUBTASK",
                    "Tạo Sub-task",
                    true,
                    true
            ),
            new PermissionSeed(
                    "TAO_BUG",
                    "Tạo Bug",
                    true,
                    true
            ),
            new PermissionSeed(
                    "GIAO_NGUOI",
                    "Thêm / bớt người được giao",
                    true,
                    true
            ),
            new PermissionSeed(
                    "SUA_VIEC",
                    "Sửa nội dung, ưu tiên, gia hạn deadline, đổi cha",
                    true,
                    true
            ),
            new PermissionSeed(
                    "DONG_VIEC",
                    "Đóng việc",
                    true,
                    true
            ),
            new PermissionSeed(
                    "MO_LAI",
                    "Mở lại việc / phần việc",
                    true,
                    true
            ),
            new PermissionSeed(
                    "CAP_NHAT_PHAN_VIEC",
                    "Cập nhật trạng thái phần việc của mình",
                    true,
                    true
            ),
            new PermissionSeed(
                    "DINH_KEM_FILE",
                    "Đính kèm tệp",
                    true,
                    true
            ),
            new PermissionSeed(
                    "BINH_LUAN",
                    "Bình luận",
                    true,
                    true
            ),
            new PermissionSeed(
                    "KHOI_PHUC_KHO",
                    "Khôi phục việc từ kho lưu trữ",
                    true,
                    true
            ),
            new PermissionSeed(
                    "QUAN_LY_THANH_VIEN",
                    "Thêm / bớt thành viên, đặt vai trò trong dự án",
                    true,
                    true
            ),
            new PermissionSeed(
                    "QUAN_LY_DU_AN",
                    "Sửa thông tin dự án, chọn workflow, kết thúc dự án",
                    true,
                    true
            ),
            new PermissionSeed(
                    "XEM_DASHBOARD_DU_AN",
                    "Xem dashboard dự án",
                    true,
                    true
            )
    );

}
