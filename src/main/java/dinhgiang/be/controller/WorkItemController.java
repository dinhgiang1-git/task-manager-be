package dinhgiang.be.controller;

import dinhgiang.be.initializer.data.workitem.WorkItemCommentRequest;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.BaseController;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.workitem.MyWorkItemFilterRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemAssignRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemFilterRequest;
import dinhgiang.be.model.dto.response.workitem.*;
import dinhgiang.be.service.interfaces.IWorkItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/work-items")
@RequiredArgsConstructor
@Tag(name = "Quản lí công việc")
public class WorkItemController extends BaseController {
    private final IWorkItemService workItemService;

    @PostMapping(value = "/create")
    @Operation(summary = "Tạo mới công việc")
    public ResponseEntity<ApiResponse<Void>> createWorkItem(
            @Valid @RequestBody WorkItemCreateRequest request){
        return executeApiResult(() -> workItemService.createWorkItem(request));
    }

    @PostMapping(value = "/gets")
    @Operation(summary = "Lấy danh sách công việc theo dự án")
    public ResponseEntity<ApiResponse<PaginationResponse<WorkItemListResponse>>> getWorkItems(
            @Valid @RequestBody PaginationRequest<WorkItemFilterRequest> request) {
        return executeApiResult(() -> workItemService.getWorkItems(request));
    }

    @PostMapping(value = "/my-tasks")
    @Operation(summary = "Lấy danh sách công việc của tôi")
    public ResponseEntity<ApiResponse<PaginationResponse<MyWorkItemListResponse>>> getMyWorkItems(
            @Valid @RequestBody PaginationRequest<MyWorkItemFilterRequest> request) {
        return executeApiResult(() -> workItemService.getMyWorkItem(request));
    }

    @PostMapping(value = "/assgin")
    @Operation(summary = "Gán thành viên vào công việc")
    public ResponseEntity<ApiResponse<Void>> assignWorkItem(
            @Valid @RequestBody WorkItemAssignRequest request) {
        return executeApiResult(() -> workItemService.assignWorkItem(request));
    }

    @PostMapping(value = "/status-update")
    @Operation(summary = "Cập nhật trạng thái công việc")
    public ResponseEntity<ApiResponse<Void>> updateWorkItemStatus(
            @Valid @RequestBody UpdateWorkItemStatusRequest request) {
        return executeApiResult(() -> workItemService.updateWorkItemStatus(request));
    }

    //WorkItem Other:.......
    @GetMapping("/types/gets")
    @Operation(summary = "Lấy tất cả loại công việc")
    public ResponseEntity<ApiResponse<List<WorkItemTypeResponse>>> getAllWorkItemTypes() {
        return executeApiResult(workItemService::getAllWorkItemTypes);
    }

    @GetMapping(value = "/statues/gets")
    @Operation(summary = "Lấy tất cả trạng thái công việc")
    public ResponseEntity<ApiResponse<List<WorkItemStatusResponse>>> getAllWorkItemStatuses() {
        return executeApiResult(workItemService::getAllWorkItemStatuses);
    }

    //Comment........:
    @PostMapping("/comments/create")
    @Operation(summary = "Tạo mới comment")
    public ResponseEntity<ApiResponse<Long>> createComment(
            @Valid @RequestBody WorkItemCommentRequest request){
        return executeApiResult(() -> workItemService.createWorkItemComment(request));
    }

    @GetMapping("/comment/gets")
    @Operation(summary = "Lấy danh sách comment theo công việc")
    public ResponseEntity<ApiResponse<List<WorkItemCommentResponse>>> getWorkItemComments(
            @RequestParam(value = "workItemId") Long workItemId) {
        return executeApiResult(() -> workItemService.getWorkItemComment(workItemId));
    }


}
