package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;
import dinhgiang.be.model.entity.account.Account;
import dinhgiang.be.model.entity.project.Project;
import dinhgiang.be.model.entity.workitem.Priority;
import dinhgiang.be.model.entity.workitem.WorkItem;
import dinhgiang.be.model.entity.workitem.WorkItemAssignment;
import dinhgiang.be.model.entity.workitem.WorkItemType;
import dinhgiang.be.repository.*;
import dinhgiang.be.security.util.SecurityUtil;
import dinhgiang.be.service.interfaces.IWorkItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkItemService implements IWorkItemService {
    private final ProjectRepository projectRepository;
    private final WorkItemTypeRepository  workItemTypeRepository;
    private final WorkItemRepository workItemRepository;
    private final PriorityRepository priorityRepository;
    private final WorkItemStatusRepository workItemStatusRepository;
    private final AccountRepository accountRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final WorkItemAssignmentRepository workItemAssignmentRepository;

    public ApiResponse<Void> createWorkItem(WorkItemCreateRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new BusinessException("Dự án không tồn tại"));

        WorkItemType workItemType = workItemTypeRepository.findById(request.getWokItemTypeId())
                .orElseThrow(() -> new BusinessException("Loại công việc không tồn tại"));

        Priority priority = priorityRepository.findById(request.getPriorityId())
                .orElseThrow(() -> new BusinessException("Độ ưu tiên không tồn tại"));

        Long workItemParentId = null;
        if (request.getParentId() != null) {
            WorkItem workItemParent = workItemRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BusinessException("Công việc cha không tồn tại"));
            if (!project.getId().equals(workItemParent.getProjectId())) throw new BusinessException("Công việc cha không thuộc dự án");

            workItemParentId = workItemParent.getId();
        }

        WorkItem workItem = new WorkItem();
        String workItemCode = project.getCode() + " " + workItemRepository.getMaxId();
        Long workItemStatusDefault = workItemStatusRepository.findByCode("NOT_STARTED")
                        .orElseThrow(() -> new BusinessException("Trạng thái khởi tạo của công việc không tồn tại"));

        workItem.setProjectId(project.getId());
        workItem.setWorkItemTypeId(workItemType.getId());
        workItem.setWorkItemCode(workItemCode);
        workItem.setParentId(workItemParentId);
        workItem.setTitle(request.getTitle());
        workItem.setDescription(request.getDescription());
        workItem.setPriorityId(priority.getId());
        workItem.setDueDate(request.getDueDate());
        workItem.setCreatedBy(SecurityUtil.getRequireAccountId());
        workItem.setWorkItemStatusId(workItemStatusDefault);

        //Nếu loại công việc là EPIC thì có người phụ trách
        if (workItemType.getCode().equals("EPIC")) {
            Account account = accountRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new BusinessException("Tài khoản Owner không tồn tại"));
            if (!projectMemberRepository.existsByAccountId(account.getId())) throw new BusinessException("Tài khoản không thuộc dự án");
            workItem.setOwnerId(account.getId());
        } else if (request.getOwnerId() != null) throw new BusinessException("Chỉ có EPIC mới có người phụ trách");


        workItem = workItemRepository.saveAndFlush(workItem);

        // Gán người vào công việc
        Set<Long> accountIds = request.getAccountIds();
        List<Account> accounts = accountRepository.findAllById(accountIds);
        WorkItemAssignment workItemAssignment;
        for (Account account : accounts) {
            if (!projectMemberRepository.existsByAccountId(account.getId())) {
                throw new BusinessException("Tài khoản không thuộc thành viên dự án");
            }

            workItemAssignment = new WorkItemAssignment();
            workItemAssignment.setWorkItemId(workItem.getId());
            workItemAssignment.setAccountId(account.getId());
            workItemAssignment.setWorkItemStatusId(workItemStatusDefault);
            workItemAssignment.setStartedAt(Instant.now());

            workItemAssignmentRepository.saveAndFlush(workItemAssignment);
        }

        return ApiResponse.success("Tạo mới thành công công việc");
    }


}
