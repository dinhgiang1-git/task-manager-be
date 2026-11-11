package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.mapper.mapping.ProjectPermissionMapping;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.dto.request.project.ProjectPermissionCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectPermissionUpdateRequest;
import dinhgiang.be.model.dto.response.project.ProjectPermissionListResponse;
import dinhgiang.be.model.entity.project.ProjectPermission;
import dinhgiang.be.repository.ProjectPermissionRepository;
import dinhgiang.be.service.interfaces.IProjectPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectPermissionService implements IProjectPermissionService {
    private final ProjectPermissionRepository projectPermissionRepository;
    private final ProjectPermissionMapping projectPermissionMapping;

    public ApiResponse<Void> createProjectPermission(ProjectPermissionCreateRequest request) {
        if (projectPermissionRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Mã quyền đã tồn tại");
        } else {
            ProjectPermission projectPermission = new ProjectPermission();
            projectPermission.setCode(request.getCode());
            projectPermission.setName(request.getName());
            if (request.getIsActive() != null) {
                projectPermission.setIsActive(request.getIsActive());
            }
            projectPermissionRepository.save(projectPermission);
        }

        return ApiResponse.success("Tạo mới quyền thành công");
    }

    public ApiResponse<Void> updateProjectPermission(ProjectPermissionUpdateRequest request) {
        ProjectPermission projectPermission = projectPermissionRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Quyền không tồn tại"));

        if (projectPermissionRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Mã quyền đã tồn tại");
        } else {
            projectPermissionMapping.updateEntity(request, projectPermission);
            projectPermissionRepository.save(projectPermission);
        }
        return ApiResponse.success("Cập nhật quyền thành công");
    }

    public ApiResponse<List<ProjectPermissionListResponse>> getProjectPermissions(Long roleId) {
        List<ProjectPermissionListResponse> response = projectPermissionRepository.getProjectPermissions(roleId);
        return ApiResponse.success(response, "Lấy danh sách quyền thành công");
    }
}
