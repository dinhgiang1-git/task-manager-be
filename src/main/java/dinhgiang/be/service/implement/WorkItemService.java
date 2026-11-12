package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;
import dinhgiang.be.model.entity.project.Project;
import dinhgiang.be.model.entity.workitem.Priority;
import dinhgiang.be.model.entity.workitem.WorkItem;
import dinhgiang.be.model.entity.workitem.WorkItemType;
import dinhgiang.be.repository.*;
import dinhgiang.be.security.util.SecurityUtil;
import dinhgiang.be.service.interfaces.IWorkItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkItemService implements IWorkItemService {
    private final ProjectRepository projectRepository;
    private final WorkItemTypeRepository  workItemTypeRepository;
    private final WorkItemRepository workItemRepository;
    private final PriorityRepository priorityRepository;
    private final WorkItemStatusRepository workItemStatusRepository;

    public ApiResponse<Void> createWorkItem(WorkItemCreateRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new BusinessException("Dự án không tồn tại"));

        WorkItemType workItemType = workItemTypeRepository.findById(request.getWokItemTypeId())
                .orElseThrow(() -> new BusinessException("Loại công việc không tồn tại"));

        Priority priority = priorityRepository.findById(request.getPriorityId())
                .orElseThrow(() -> new BusinessException("Độ ưu tiên không tồn tại"));

        if (request.getParentId() != null) {
            WorkItem workItemParent = workItemRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BusinessException("Công việc cha không tồn tại"));

            WorkItem workItem =  new WorkItem();
            String workItemCode = project.getCode() + " " + workItemRepository.getMaxId();
            Long workItemStatusDefault = workItemStatusRepository.findByCode("NOT_STARTED")
                    .orElseThrow(() -> new BusinessException("Trạng thái khởi tạo của công việc không tồn tại"));

            workItem.setProjectId(project.getId());
            workItem.setWorkItemTypeId(workItemType.getId());
            workItem.setWorkItemCode(workItemCode);
            workItem.setParentId(workItemParent.getId());
            workItem.setTitle(request.getTitle());
            workItem.setDescription(request.getDescription());
            workItem.setPriorityId(priority.getId());
            workItem.setDueDate(request.getDueDate());
            workItem.setCreatedBy(SecurityUtil.getRequireAccountId());
            workItem.setWorkItemStatusId(workItemStatusDefault);

            workItemRepository.saveAndFlush(workItem);

        }

        WorkItem workItem = new WorkItem();
        String workItemCode = project.getCode() + " " + workItemRepository.getMaxId();
        Long workItemStatusDefault = workItemStatusRepository.findByCode("NOT_STARTED")
                        .orElseThrow(() -> new BusinessException("Trạng thái khởi tạo của công việc không tồn tại"));

        workItem.setProjectId(project.getId());
        workItem.setWorkItemTypeId(workItemType.getId());
        workItem.setWorkItemCode(workItemCode);
        workItem.setParentId(null);
        workItem.setTitle(request.getTitle());
        workItem.setDescription(request.getDescription());
        workItem.setPriorityId(priority.getId());
        workItem.setDueDate(request.getDueDate());
        workItem.setCreatedBy(SecurityUtil.getRequireAccountId());
        workItem.setWorkItemStatusId(workItemStatusDefault);

        workItemRepository.saveAndFlush(workItem);

        return ApiResponse.success("Tạo mới thành công công việc");

    }
}
