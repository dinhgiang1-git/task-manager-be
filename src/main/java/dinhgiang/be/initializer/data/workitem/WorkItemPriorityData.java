package dinhgiang.be.initializer.data.workitem;

import java.util.List;

public class WorkItemPriorityData {
    public static final List<WorkItemPrioritySeed> DATA = List.of(
            new WorkItemPrioritySeed("HIGHEST", "Highest", 1L,  true, true),
            new WorkItemPrioritySeed("HIGH", "High", 2L, true, true),
            new WorkItemPrioritySeed("MEDIUM", "Medium",2L, true, true),
            new WorkItemPrioritySeed("LOW", "Low",2L, true, true)
    );
}
