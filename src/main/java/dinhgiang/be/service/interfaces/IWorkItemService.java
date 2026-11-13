package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;
import dinhgiang.be.model.dto.request.workitem.WorkItemFilterRequest;
import dinhgiang.be.model.dto.response.workitem.WorkItemListResponse;

public interface IWorkItemService {
    ApiResponse<Void> createWorkItem(WorkItemCreateRequest request);
    ApiResponse<PaginationResponse<WorkItemListResponse>> getWorkItems(PaginationRequest<WorkItemFilterRequest> request);
}
