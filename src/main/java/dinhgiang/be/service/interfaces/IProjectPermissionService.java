package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.dto.request.project.ProjectPermissionCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectPermissionUpdateRequest;
import dinhgiang.be.model.dto.response.project.PermissionSchemaResponse;
import dinhgiang.be.model.dto.response.project.ProjectPermissionListResponse;

import java.util.List;

public interface IProjectPermissionService {
    ApiResponse<Void> createProjectPermission(ProjectPermissionCreateRequest request);
    ApiResponse<Void> updateProjectPermission(ProjectPermissionUpdateRequest request);
    ApiResponse<List<ProjectPermissionListResponse>> getProjectPermissions(Long roleId);
    ApiResponse<List<PermissionSchemaResponse>> getAllPermissionSchemas();
}
