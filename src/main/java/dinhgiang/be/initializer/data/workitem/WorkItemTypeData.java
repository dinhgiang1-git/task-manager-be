package dinhgiang.be.initializer.data.workitem;

import java.util.List;

public class WorkItemTypeData {
    public static final List<WorkItemTypeSeed> DATA = List.of(
            new WorkItemTypeSeed("EPIC", "Epic", true, true),
            new WorkItemTypeSeed("STORY", "Story", true, true),
            new WorkItemTypeSeed("TASK", "Task", true, true),
            new WorkItemTypeSeed("BUG", "Bug", true, true),
            new WorkItemTypeSeed("SUBTASK", "SubTask", true, true)
    );
}
