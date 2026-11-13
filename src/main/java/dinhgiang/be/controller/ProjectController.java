package dinhgiang.be.controller;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.BaseController;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.project.*;
import dinhgiang.be.model.dto.response.project.*;
import dinhgiang.be.service.interfaces.IProjectMemberService;
import dinhgiang.be.service.interfaces.IProjectPermissionService;
import dinhgiang.be.service.interfaces.IProjectRole;
import dinhgiang.be.service.interfaces.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/projects")
@RequiredArgsConstructor
@Tag(name = "Quản lý dự án")
public class ProjectController extends BaseController {
    private final IProjectService projectService;
    private final IProjectPermissionService projectPermissionService;
    private final IProjectRole projectRole;
    private final IProjectMemberService projectMemberService;

    //--------Project:
    @PostMapping(value = "/create")
    @Operation(summary = "Tạo mới dự án")
    public ResponseEntity<ApiResponse<Void>> createProject(@Valid @RequestBody ProjectCreateRequest request){
        return executeApiResult(() ->  projectService.createProject(request));
    }

    @PostMapping(value = "/gets")
    @Operation(summary = "Lấy danh sách dự án")
    public ResponseEntity<ApiResponse<PaginationResponse<ProjectListResponse>>> getProjects(@RequestBody PaginationRequest<ProjectFilterRequest> request){
        return executeApiResult(() ->  projectService.getProjects(request));
    }

    @PutMapping(value = "/update")
    @Operation(summary = "Cập nhật dự án")
    public ResponseEntity<ApiResponse<Void>> updateProject(@RequestBody ProjectUpdateRequest request){
        return executeApiResult(() ->  projectService.updateProject(request));
    }

    @GetMapping("/get")
    @Operation(summary = "Lấy chi tiết dự án")
    public ResponseEntity<ApiResponse<ProjectDetailResponse>> getProjectDetail(@RequestParam(value = "id") Long id) {
        return executeApiResult(() -> projectService.getDetailProject(id));
    }

    //---------Permission:
    @PostMapping(value = "/permissions/create")
    @Operation(summary = "Tạo mới quyền")
    public ResponseEntity<ApiResponse<Void>> createProjectPermission(@Valid @RequestBody ProjectPermissionCreateRequest request){
        return executeApiResult(() ->  projectPermissionService.createProjectPermission(request));
    }

    @PutMapping(value = "/permissions/update")
    @Operation(summary = "Cập nhật quyền")
    public ResponseEntity<ApiResponse<Void>> updateProjectPermission(@Valid @RequestBody ProjectPermissionUpdateRequest request){
        return executeApiResult(() ->  projectPermissionService.updateProjectPermission(request));
    }

    @PostMapping(value = "/permissions/gets")
    @Operation(summary = "Lấy danh sách quyền")
    public ResponseEntity<ApiResponse<List<ProjectPermissionListResponse>>> getProjectPermissions(@RequestParam(value = "roleId", required = false) Long roleId) {
        return executeApiResult(() ->  projectPermissionService.getProjectPermissions(roleId));
    }

    //--------Role:
    @PostMapping(value = "/roles/create")
    @Operation(summary = "Tạo mới vai trò")
    public ResponseEntity<ApiResponse<Void>> createProjectRole(@Valid @RequestBody ProjectRoleCreateRequest request){
        return executeApiResult(() -> projectRole.createProjectRole(request));
    }

    @PutMapping(value = "/roles/update")
    @Operation(summary = "Cập nhật vai trò")
    public ResponseEntity<ApiResponse<Void>> updateProjectRole(@Valid @RequestBody ProjectRoleUpdateRequest request){
        return executeApiResult(() -> projectRole.updateProjectRole(request));
    }

    @PostMapping(value = "/roles/gets")
    @Operation(summary = "Lấy danh sách vai trò")
    public ResponseEntity<ApiResponse<PaginationResponse<ProjectRoleListResponse>>> getProjectRoles(@RequestBody PaginationRequest<ProjectRoleFilter> request){
        return executeApiResult(() -> projectRole.getProjectRoles(request));
    }

    @PostMapping(value = "/roles/assign-permssion")
    @Operation(summary = "Gán quyền vào vai trò")
    public ResponseEntity<ApiResponse<Void>> assignPermissionIntoRole(@Valid @RequestBody AssignPermissionIntoRoleRequest request){
        return executeApiResult(() -> projectRole.assignPermissionIntoRole(request));
    }

    //----------Member:
    @PostMapping(value = "/project-member/assign")
    @Operation(summary = "Gán người vào dự án")
    public ResponseEntity<ApiResponse<Void>> assignMemberIntoProject(@Valid @RequestBody ProjectMemberCreateRequest request){
        return executeApiResult(() -> projectMemberService.assignMemberIntoProject(request));
    }

    public ResponseEntity<ApiResponse<PaginationResponse<ProjectMemberListResponse>>> getProjectMembers(@RequestBody PaginationRequest<ProjectMemberFilterRequest> request) {
        return executeApiResult(() ->  projectMemberService.getProjectMembers(request));
    }

    //----------PermissionSchema:
    @GetMapping(value = "permission-schema/get-all")
    @Operation(summary = "Danh sách bảng quyền")
    public ResponseEntity<ApiResponse<List<PermissionSchemaResponse>>> getAllPermissionSchemas() {
        return executeApiResult(projectPermissionService::getAllPermissionSchemas);
    }


}
