package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.project.ProjectCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectFilterRequest;
import dinhgiang.be.model.dto.request.project.ProjectUpdateRequest;
import dinhgiang.be.model.dto.response.project.PermissionSchemaResponse;
import dinhgiang.be.model.dto.response.project.ProjectListResponse;

import java.util.List;

public interface IProjectService {
    ApiResponse<Void> createProject(ProjectCreateRequest request);
    ApiResponse<PaginationResponse<ProjectListResponse>> getProjects(PaginationRequest<ProjectFilterRequest> request);
    ApiResponse<Void> updateProject(ProjectUpdateRequest request);
    ApiResponse<List<PermissionSchemaResponse>> getAllPermissionSchemas();
}
