package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.mapper.mapping.ProjectRoleMapping;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.project.AssignPermissionIntoRoleRequest;
import dinhgiang.be.model.dto.request.project.ProjectRoleCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectRoleFilter;
import dinhgiang.be.model.dto.request.project.ProjectRoleUpdateRequest;
import dinhgiang.be.model.dto.response.project.ProjectRoleListResponse;
import dinhgiang.be.model.entity.project.PermissionSchemaGrant;
import dinhgiang.be.model.entity.project.ProjectRole;
import dinhgiang.be.repository.ProjectPermissionSchemaGrantRepository;
import dinhgiang.be.repository.ProjectRoleRepository;
import dinhgiang.be.service.interfaces.IProjectRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectRoleService implements IProjectRole {
    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectRoleMapping projectRoleMapping;
    private final ProjectPermissionSchemaGrantRepository projectPermissionSchemaGrantRepository;

    public ApiResponse<Void> createProjectRole(ProjectRoleCreateRequest request) {
        if (projectRoleRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Mã vai trò đã tồn tại");
        }

        ProjectRole projectRole = new ProjectRole();
        projectRole.setCode(request.getCode());
        projectRole.setName(request.getName());
        if (request.getIsActive() != null) {
            projectRole.setIsActive(request.getIsActive());
        }

        projectRoleRepository.save(projectRole);

        return ApiResponse.success("Tạo vai trò thành công");
    }

    public ApiResponse<Void> updateProjectRole(ProjectRoleUpdateRequest request) {
        ProjectRole projectRole = projectRoleRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Vai trò không tồn tại"));

        if (projectRoleRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Mã vai trò đã tồn tại");
        }

        projectRoleMapping.updateEntity(request, projectRole);

        projectRoleRepository.save(projectRole);

        return ApiResponse.success("Cập nhật vai trò thành công");
    }

    public ApiResponse<PaginationResponse<ProjectRoleListResponse>> getProjectRoles(PaginationRequest<ProjectRoleFilter> request) {
        ProjectRoleFilter filter = request.getFilter();

        int pageNow = request.getPageNow();
        int pageSize = request.getPageSize();
        int springPage = (pageNow > 0) ? pageNow - 1 : 0;
        Pageable pageable = PageRequest.of(springPage, pageSize);
        String keyword = (filter.getKeyword() != null) ? filter.getKeyword() : null;
        Boolean isActive = (filter.getIsActive() != null) ? filter.getIsActive() : null;

        Page<ProjectRoleListResponse> page = projectRoleRepository.getProjectRoles(keyword, isActive, pageable);

        PaginationResponse<ProjectRoleListResponse> response = new PaginationResponse<>();
        response.setPageNow(pageNow);
        response.setPageSize(pageSize);
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setData(page.getContent());

        return ApiResponse.success(response, "Lấy thành công danh sách");
    }

    public ApiResponse<Void> assignPermissionIntoRole(AssignPermissionIntoRoleRequest request) {
        projectPermissionSchemaGrantRepository.deleteAll(projectPermissionSchemaGrantRepository.findAllBySchemaId(request.getProjectPermissionSchemaId()));

        if (request.getProjectPermissionIds() != null) {
            for (Long projectPermissionId : request.getProjectPermissionIds()) {
                PermissionSchemaGrant psg = new  PermissionSchemaGrant();
                psg.setProjectRoleId(request.getProjectRoleId());
                psg.setProjectPermissionSchemaId(request.getProjectPermissionSchemaId());
                psg.setProjectPermissionId(projectPermissionId);

                projectPermissionSchemaGrantRepository.save(psg);
            }
        }

        return ApiResponse.success("Gán quyền vào role thành công");
    }
}
