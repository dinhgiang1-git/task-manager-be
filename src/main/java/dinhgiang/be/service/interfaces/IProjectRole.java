package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.project.AssignPermissionIntoRoleRequest;
import dinhgiang.be.model.dto.request.project.ProjectRoleCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectRoleFilter;
import dinhgiang.be.model.dto.request.project.ProjectRoleUpdateRequest;
import dinhgiang.be.model.dto.response.project.ProjectRoleListResponse;

public interface IProjectRole {
    ApiResponse<Void> createProjectRole(ProjectRoleCreateRequest request);
    ApiResponse<Void> updateProjectRole(ProjectRoleUpdateRequest request);
    ApiResponse<PaginationResponse<ProjectRoleListResponse>> getProjectRoles(PaginationRequest<ProjectRoleFilter> request);
    ApiResponse<Void> assignPermissionIntoRole(AssignPermissionIntoRoleRequest request);
}
