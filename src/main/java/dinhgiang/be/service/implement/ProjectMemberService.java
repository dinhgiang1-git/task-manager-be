package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.project.ProjectMemberCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectMemberFilterRequest;
import dinhgiang.be.model.dto.request.project.ProjectMemberUpdateRequest;
import dinhgiang.be.model.dto.response.project.ProjectMemberListResponse;
import dinhgiang.be.model.entity.account.Account;
import dinhgiang.be.model.entity.project.Project;
import dinhgiang.be.model.entity.project.ProjectMember;
import dinhgiang.be.model.entity.project.ProjectRole;
import dinhgiang.be.repository.*;
import dinhgiang.be.security.util.SecurityUtil;
import dinhgiang.be.service.interfaces.IProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService implements IProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final AccountRepository accountRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final WorkItemRepository workItemRepository;
    private final WorkItemAssignmentRepository workItemAssignmentRepository;
    private final StatusCalculatorService statusCalculatorService;

    public ApiResponse<Void> assignMemberIntoProject(ProjectMemberCreateRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new BusinessException("Dự án không tồn tại"));

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new BusinessException("Tài khoản không tồn tại"));

        ProjectRole projectRole = projectRoleRepository.findById(request.getProjectRoleId())
                .orElseThrow(() -> new BusinessException("Vai trò không tồn tại"));

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(project.getId());
        projectMember.setAccountId(account.getId());
        projectMember.setProjectRoleId(projectRole.getId());
        projectMember.setJoinAt(Instant.now());
        projectMember.setAddedBy(SecurityUtil.getRequireAccountId());

        projectMemberRepository.save(projectMember);

        return ApiResponse.success("Thêm thành viên vào dự án thành công");
    }

    public ApiResponse<PaginationResponse<ProjectMemberListResponse>> getProjectMembers(PaginationRequest<ProjectMemberFilterRequest> request) {
        ProjectMemberFilterRequest filter = request.getFilter();

        Project project = projectRepository.findById(filter.getProjectId())
                .orElseThrow(() -> new BusinessException("Dự án không tồn tại"));

        int pageNow = request.getPageNow();
        int pageSize = request.getPageSize();
        int springPage = (pageNow > 0) ? pageNow - 1 : 0;
        Pageable pageable = PageRequest.of(springPage, pageSize);
        String keyword = (filter.getKeyword() != null) ? filter.getKeyword() : null;
        Long projectId = project.getId();

        Page<ProjectMemberListResponse> page = projectMemberRepository.page(keyword, projectId, pageable);

        PaginationResponse<ProjectMemberListResponse> response = new PaginationResponse<>();
        response.setPageNow(pageNow);
        response.setPageSize(pageSize);
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setData(page.getContent());

        return ApiResponse.success(response, "Lấy danh sách thành viên thành công");
    }

    @Transactional
    public ApiResponse<Void> removeMemberFromProject(Long accountId, Long projectId) {
        ProjectMember projectMembers = projectMemberRepository.findByAccountId(accountId)
                .orElseThrow(() -> new BusinessException("Thành viên không tồn tại trong dự án"));

        if (projectMembers.getAccountId().equals(SecurityUtil.getRequireAccountId()))
            throw new BusinessException("Không thể tự xoá chính mình ra khỏi dự án");

        //Todo: Cần remove thành viên đó ra khỏi task đang gán
        List<Long> assignedWorkItem = workItemRepository.getAssignedWorkItemIds(accountId, projectId);

        if (!assignedWorkItem.isEmpty()) {
            workItemAssignmentRepository.deleteByWorkItemIdInAndAccountId(assignedWorkItem, accountId);
            workItemAssignmentRepository.flush();

            for (Long workItemId : assignedWorkItem) {
                statusCalculatorService.recalculateStatus(workItemId);
            }
        }

        projectMemberRepository.delete(projectMembers);

        return ApiResponse.success("Xoá thành viên khỏi dự án thành công");
    }

    public ApiResponse<Long> updateProjectMember(ProjectMemberUpdateRequest request) {
        ProjectMember member = projectMemberRepository.findByAccountIdAndProjectId(request.getAccountId(), request.getProjectId())
                .orElseThrow(() -> new BusinessException("Thành viên không tồn tại trong dự án"));

        ProjectRole projectRole = projectRoleRepository.findById(request.getProjectRoleId())
                .orElseThrow(() -> new BusinessException("Vai trò dự án không tồn tại"));

        if (member.getAccountId().equals(SecurityUtil.getRequireAccountId()))
            throw new BusinessException("Không thể tự cập nhật role của bản thân");

        if (member.getProjectRoleId().equals(projectRole.getId()))
            return ApiResponse.success(member.getId(), "Cập nhật thành công");

        member.setProjectRoleId(projectRole.getId());
        projectMemberRepository.save(member);

        return ApiResponse.success(member.getId(), "Cập nhật thành công");
    }

}
