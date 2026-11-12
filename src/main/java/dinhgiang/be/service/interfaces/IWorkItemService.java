package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.dto.request.workitem.WorkItemCreateRequest;

public interface IWorkItemService {
    ApiResponse<Void> createWorkItem(WorkItemCreateRequest request);
}
