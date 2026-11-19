package dinhgiang.be.service.interfaces;

import dinhgiang.be.initializer.data.workitem.WorkItemCommentRequest;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.workitem.MyWorkItemFilterRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemAssignRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemFilterRequest;
import dinhgiang.be.model.dto.response.workitem.*;

import java.util.List;

public interface IWorkItemService {
    ApiResponse<Void> createWorkItem(WorkItemCreateRequest request);
    ApiResponse<PaginationResponse<WorkItemListResponse>> getWorkItems(PaginationRequest<WorkItemFilterRequest> request);
    ApiResponse<PaginationResponse<MyWorkItemListResponse>> getMyWorkItem(PaginationRequest<MyWorkItemFilterRequest> request);
    ApiResponse<Void> assignWorkItem(WorkItemAssignRequest request);
    ApiResponse<Void> updateWorkItemStatus(UpdateWorkItemStatusRequest request);
    ApiResponse<List<WorkItemTypeResponse>> getAllWorkItemTypes();
    ApiResponse<Long> createWorkItemComment(WorkItemCommentRequest request);
    ApiResponse<List<WorkItemCommentResponse>> getWorkItemComment(Long workItemId);
    ApiResponse<List<WorkItemStatusResponse>> getAllWorkItemStatuses();
}
