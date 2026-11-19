package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.initializer.data.workitem.WorkItemCommentRequest;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.workitem.MyWorkItemFilterRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemAssignRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemFilterRequest;
import dinhgiang.be.model.dto.response.workitem.*;
import dinhgiang.be.model.entity.account.Account;
import dinhgiang.be.model.entity.project.Project;
import dinhgiang.be.model.entity.workitem.*;
import dinhgiang.be.model.enums.WorkItemStatusEnum;
import dinhgiang.be.model.enums.WorkItemTypeEnum;
import dinhgiang.be.repository.*;
import dinhgiang.be.security.util.SecurityUtil;
import dinhgiang.be.service.interfaces.IWorkItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
    private final StatusCalculatorService statusCalculatorService;
    private final WorkItemCommentRepository workItemCommentRepository;

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
            if (request.getOwnerId() == null) {
                throw new BusinessException("EPIC bắt buộc phải có người phụ trách");
            }
            Account account = accountRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new BusinessException("Tài khoản Owner không tồn tại"));
            if (!projectMemberRepository.existsByAccountId(account.getId())) throw new BusinessException("Tài khoản không thuộc dự án");
            workItem.setOwnerId(account.getId());
        } else if (request.getOwnerId() != null) throw new BusinessException("Chỉ có EPIC mới có người phụ trách");


        workItem = workItemRepository.saveAndFlush(workItem);

        // Gán người vào công việc
        Set<Long> accountIds = request.getAccountIds() == null
                ? Set.of()
                : request.getAccountIds().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
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

    //Lấy công việc theo dự án
    public ApiResponse<PaginationResponse<WorkItemListResponse>> getWorkItems(PaginationRequest<WorkItemFilterRequest> request) {
        WorkItemFilterRequest filter = request.getFilter();

        int pageSize = request.getPageSize();
        int pageNow = request.getPageNow();
        int springPage = (pageNow > 0) ? pageNow -1 : 0;

        Pageable pageable = PageRequest.of(springPage, pageSize);
        Long projectId = filter.getProjectId();
        String keyword = filter.getKeyword();
        Boolean isArchive = filter.getIsArchive() ? true : null;
        Page<WorkItemListResponse> page = workItemRepository.page(projectId, isArchive, keyword, pageable);

        List<WorkItemListResponse> treeData = buildTreeWorkItems(page.getContent());

        PaginationResponse<WorkItemListResponse> response = new PaginationResponse<>();
        response.setPageNow(pageNow);
        response.setPageSize(pageSize);
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setData(treeData);

        return ApiResponse.success(response, "Lấy danh sách công việc thành công");
    }

    //--------Helper build tree
    private List<WorkItemListResponse> buildTreeWorkItems(List<WorkItemListResponse> workItems) {
        Map<Long, WorkItemListResponse> nodes = new LinkedHashMap<>();

        //Tạo node cho tất cả công việc
        for (WorkItemListResponse workItem : workItems) {
            WorkItemListResponse node = new WorkItemListResponse();
            node.setId(workItem.getId());
            node.setProjectId(workItem.getProjectId());
            node.setWorkItemCode(workItem.getWorkItemCode());
            node.setTitle(workItem.getTitle());
            node.setParentId(workItem.getParentId());
            node.setDueDate(workItem.getDueDate());
            node.setOwnerId(workItem.getOwnerId());

            node.setWorkItemTypeId(workItem.getWorkItemTypeId());
            node.setWorkItemTypeCode(workItem.getWorkItemTypeCode());

            node.setPriorityId(workItem.getPriorityId());
            node.setPriorityCode(workItem.getPriorityCode());
            node.setPriorityName(workItem.getPriorityName());

            node.setWorkItemStatusId(workItem.getWorkItemStatusId());
            node.setWorkItemStatusCode(workItem.getWorkItemStatusCode());

            nodes.put(workItem.getId(), node);
        }

        List<WorkItemListResponse> roots = new ArrayList<>();
        for (WorkItemListResponse node : nodes.values()) {
            WorkItemListResponse parent = nodes.get(node.getParentId());

            if (parent != null) {
                parent.getChildren().add(node);
            } else {
                roots.add(node);
            }
        }
        return roots;
    }

    //Lấy công việc của tôi
    public ApiResponse<PaginationResponse<MyWorkItemListResponse>> getMyWorkItem(PaginationRequest<MyWorkItemFilterRequest> request) {
        MyWorkItemFilterRequest filter = request.getFilter();

        int pageSize = request.getPageSize();
        int pageNow = request.getPageNow();
        int springPage = (pageNow > 0) ? pageNow - 1 : 0;
        Pageable pageable = PageRequest.of(springPage, pageSize);
        Long accountId = SecurityUtil.getRequireAccountId();

        Page<MyWorkItemListResponse> page = workItemRepository.myWorkItemPage(accountId, filter.getKeyword(), pageable);

        PaginationResponse<MyWorkItemListResponse> response = new PaginationResponse<>();
        response.setPageNow(pageNow);
        response.setPageSize(pageSize);
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setData(page.getContent());
        return ApiResponse.success(response, "Lấy danh sách công việc của tôi thành công");
    }

    //Gàn người vào công việc
    @Transactional
    public ApiResponse<Void> assignWorkItem(WorkItemAssignRequest request) {
        WorkItem workItem = workItemRepository.findById(request.getWorkItemId())
                .orElseThrow(() -> new BusinessException("Công việc không tồn tại"));

        Set<Long> targetAccountIds = request.getAccountId().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (!targetAccountIds.isEmpty()) {
            List<Account> accounts = accountRepository.findAllById(targetAccountIds);
            if (accounts.size() != targetAccountIds.size()) {
                throw new BusinessException("Có tài khoản không tồn tại");
            }
            for (Account account : accounts) {
                if (!projectMemberRepository.existsByAccountIdAndProjectId(account.getId(), workItem.getProjectId())) {
                    throw new BusinessException("Tài khoản không thuộc dự án");
                }
            }
        }

        Set<Long> currentAccountIds = workItemAssignmentRepository.findAllByWorkItemId(workItem.getId());

        Set<Long> toRemove = currentAccountIds.stream()
                .filter(id -> !targetAccountIds.contains(id))
                .collect(Collectors.toSet());
        if (!toRemove.isEmpty()) {
            workItemAssignmentRepository.deleteByWorkItemIdAndAccountIdIn(workItem.getId(), toRemove);
        }

        Set<Long> toAdd = targetAccountIds.stream()
                .filter(id -> !currentAccountIds.contains(id))
                .collect(Collectors.toSet());
        if (!toAdd.isEmpty()) {
            Long statusDefaultId = workItemStatusRepository.findByCode("NOT_STARTED")
                    .orElseThrow(() -> new BusinessException("Trạng thái khởi tạo dự án không tồn tại"));

            List<WorkItemAssignment> toSave = new ArrayList<>();
            for (Long accountId : toAdd) {
                WorkItemAssignment assignment = new WorkItemAssignment();
                assignment.setWorkItemId(workItem.getId());
                assignment.setAccountId(accountId);
                assignment.setWorkItemStatusId(statusDefaultId);
                assignment.setStartedAt(Instant.now());
                toSave.add(assignment);
            }

            workItemAssignmentRepository.saveAll(toSave);
        }
        return ApiResponse.success("Gán người vào công việc thành công");
    }

    public ApiResponse<Void> updateWorkItemStatus(UpdateWorkItemStatusRequest request) {
        WorkItem workItem = workItemRepository.findById(request.getWorkItemId())
                .orElseThrow(() -> new BusinessException("Công việc không tồn tại"));

        if (workItem.isClosed() || workItem.getArchivedAt() != null) {
            throw new BusinessException("Không thể cập nhật trạng thái cho công việc đã đóng hoặc lưu trữ");
        }

        WorkItemStatus newWorkItemStatus = workItemStatusRepository.findById(request.getWorkItemStatusId())
                .orElseThrow(() -> new BusinessException("Trạng thái công việc không tồn tại"));

        if ("CLOSED".equals(newWorkItemStatus.getCode())) {
            throw new BusinessException("Vui lòng chọn chức năng đóng công việc");
        }

        WorkItemAssignment workItemAssignment = workItemAssignmentRepository
                .findByWorkItemIdAndAccountId(workItem.getId(), SecurityUtil.getRequireAccountId())
                .orElseThrow(() -> new BusinessException("Bạn không được phân công vào công việc này"));

        workItemAssignment.setWorkItemStatusId(newWorkItemStatus.getId());
        if (WorkItemStatusEnum.IN_PROGRESS.toString().equals(newWorkItemStatus.getCode()) && workItemAssignment.getStartedAt() == null) {
            workItemAssignment.setStartedAt(Instant.now());
        }
        if (WorkItemStatusEnum.DONE.toString().equals(newWorkItemStatus.getCode())) {
            workItemAssignment.setCompletedAt(Instant.now());
        }

        workItemAssignmentRepository.saveAndFlush(workItemAssignment);

        statusCalculatorService.recalculateStatus(workItem.getId());

        return ApiResponse.success("Cập nhật traạng thái công việc thành công");
    }

    //Lấy tất cả loại công việc
    public ApiResponse<List<WorkItemTypeResponse>> getAllWorkItemTypes() {
        List<WorkItemType> workItemTypes = workItemTypeRepository.findAllByIsDefaultAndIsActive(true, true);
        List<WorkItemTypeResponse> responses = new ArrayList<>();
        for (WorkItemType workItemType : workItemTypes) {
            WorkItemTypeResponse workItemTypeRes = new WorkItemTypeResponse();
            workItemTypeRes.setId(workItemType.getId());
            workItemTypeRes.setCode(workItemType.getCode());
            workItemTypeRes.setDescription(workItemType.getDescription());
            workItemTypeRes.setIsActive(workItemType.getIsActive());
            workItemTypeRes.setIsDefault(workItemType.getIsDefault());
            responses.add(workItemTypeRes);
        }

        return ApiResponse.success(responses, "Lấy tất cả loại công việc thành công");
    }

    //Lấy tất cả trạng thái công việc
    public ApiResponse<List<WorkItemStatusResponse>> getAllWorkItemStatuses() {
        List<WorkItemStatusResponse> response = workItemStatusRepository.getAllWorkItemStatues();
        return ApiResponse.success(response, "Lất tất cả trạng thái công việc thành công");
    }

    //Tạo comment WorkItem
    public ApiResponse<Long> createWorkItemComment(WorkItemCommentRequest request) {
        WorkItem workItem = workItemRepository.findById(request.getWorkItemId())
                .orElseThrow(() -> new BusinessException("Công việc không tồn tại"));

        WorkItemComment comment = new  WorkItemComment();
        comment.setWorkItemId(request.getWorkItemId());
        comment.setAuthorId(SecurityUtil.getRequireAccountId());
        comment.setBody(request.getBody());
        Long response = workItemCommentRepository.save(comment).getId();

        return ApiResponse.success(response, "Tạo mới comment thành công");
    }

    public ApiResponse<List<WorkItemCommentResponse>> getWorkItemComment(Long workItemId) {
        WorkItem workItem = workItemRepository.findById(workItemId)
                .orElseThrow(() -> new BusinessException("Công việc không tồn tại"));

        List<WorkItemCommentResponse> response = workItemCommentRepository.getWorkItemComments(workItemId);

        return ApiResponse.success(response, "Lấy danh sách comment thành công");
    }
}
