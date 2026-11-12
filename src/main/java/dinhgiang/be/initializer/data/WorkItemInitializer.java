package dinhgiang.be.initializer.data;

import dinhgiang.be.initializer.data.workitem.WorkItemStatusData;
import dinhgiang.be.initializer.data.workitem.WorkItemStatusSeed;
import dinhgiang.be.model.entity.workitem.WorkItemStatus;
import dinhgiang.be.repository.WorkItemStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkItemInitializer implements CommandLineRunner {
    private final WorkItemStatusRepository workItemStatusRepository;

    @Override
    public void run(String... args) throws Exception {

        WorkItemStatus workItemStatus;
        for (WorkItemStatusSeed seed : WorkItemStatusData.DATA) {
            if (workItemStatusRepository.existsByCode(seed.getCode())) continue;

            workItemStatus = new WorkItemStatus();
            workItemStatus.setCode(seed.getCode());
            workItemStatus.setDescription(seed.getDescription());
            workItemStatus.setIsActive(seed.getIsActive());
            workItemStatus.setIsDefault(seed.getIsDefault());

            workItemStatusRepository.save(workItemStatus);
            log.info("Khởi tạo trạng thái công việc mặc định: {}", workItemStatus.getCode());
        }
    }
}
