package dinhgiang.be.controller;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.BaseController;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.workitem.MyWorkItemFilterRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemAssignRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemFilterRequest;
import dinhgiang.be.model.dto.response.workitem.MyWorkItemListResponse;
import dinhgiang.be.model.dto.response.workitem.UpdateWorkItemStatusRequest;
import dinhgiang.be.model.dto.response.workitem.WorkItemListResponse;
import dinhgiang.be.service.interfaces.IWorkItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/work-items")
@RequiredArgsConstructor
@Tag(name = "Quản lí công việc")
public class WorkItemController extends BaseController {
    private final IWorkItemService workItemService;

    @PostMapping(value = "/create")
    @Operation(summary = "Tạo mới công việc")
    public ResponseEntity<ApiResponse<Void>> createWorkItem(@Valid @RequestBody WorkItemCreateRequest request){
        return executeApiResult(() -> workItemService.createWorkItem(request));
    }

    @PostMapping(value = "/gets")
    @Operation(summary = "Lấy danh sách công việc theo dự án")
    public ResponseEntity<ApiResponse<PaginationResponse<WorkItemListResponse>>> getWorkItems(@Valid @RequestBody PaginationRequest<WorkItemFilterRequest> request) {
        return executeApiResult(() -> workItemService.getWorkItems(request));
    }

    @PostMapping(value = "/my-tasks")
    @Operation(summary = "Lấy danh sách công việc của tôi")
    public ResponseEntity<ApiResponse<PaginationResponse<MyWorkItemListResponse>>> getMyWorkItems(@Valid @RequestBody PaginationRequest<MyWorkItemFilterRequest> request) {
        return executeApiResult(() -> workItemService.getMyWorkItem(request));
    }

    @PostMapping(value = "/assgin")
    @Operation(summary = "Gán thành viên vào công việc")
    public ResponseEntity<ApiResponse<Void>> assignWorkItem(@Valid @RequestBody WorkItemAssignRequest request) {
        return executeApiResult(() -> workItemService.assignWorkItem(request));
    }

    @PostMapping(value = "/status-update")
    @Operation(summary = "Cập nhật trạng thái công việc")
    public ResponseEntity<ApiResponse<Void>> updateWorkItemStatus(@Valid @RequestBody UpdateWorkItemStatusRequest request) {
        return executeApiResult(() -> workItemService.updateWorkItemStatus(request));
    }
}
