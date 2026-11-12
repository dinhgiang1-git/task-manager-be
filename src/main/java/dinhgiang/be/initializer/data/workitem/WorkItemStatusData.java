package dinhgiang.be.initializer.data.workitem;

import lombok.Data;

import java.util.List;

@Data
public class WorkItemStatusData {
    public static final List<WorkItemStatusSeed> DATA = List.of(
            new WorkItemStatusSeed("NOT_STARTED", "Chưa bắt đầu", true, true),
            new WorkItemStatusSeed("IN_PROGRESS", "Đang làm", true, true),
            new WorkItemStatusSeed("PAUSED", "Tạm dừng", true, true),
            new WorkItemStatusSeed("DONE", "Hoàn thành", true, true),
            new WorkItemStatusSeed("CLOSED", "Đóng", true, true)

    );
}
