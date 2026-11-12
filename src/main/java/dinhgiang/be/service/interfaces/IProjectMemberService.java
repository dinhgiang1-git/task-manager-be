package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.project.ProjectMemberCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectMemberFilterRequest;
import dinhgiang.be.model.dto.response.project.ProjectMemberListResponse;

import java.util.List;

public interface IProjectMemberService {
    ApiResponse<Void> assignMemberIntoProject(ProjectMemberCreateRequest request);
    ApiResponse<PaginationResponse<ProjectMemberListResponse>> getProjectMembers(PaginationRequest<ProjectMemberFilterRequest> request);
    ApiResponse<Void> removeMemberFromProject(List<Long> accountIds);
}
