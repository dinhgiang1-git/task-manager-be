package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.mapper.mapping.ProjectMapping;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.project.ProjectCreateRequest;
import dinhgiang.be.model.dto.request.project.ProjectFilterRequest;
import dinhgiang.be.model.dto.request.project.ProjectUpdateRequest;
import dinhgiang.be.model.dto.response.project.ProjectListResponse;
import dinhgiang.be.model.entity.project.Project;
import dinhgiang.be.repository.ProjectRepository;
import dinhgiang.be.service.interfaces.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapping projectMapping;

    @Transactional
    public ApiResponse<Void> createProject(ProjectCreateRequest request) {
        Long maxIdProject = projectRepository.getProjectIdMax();
        String code = "KAN " + maxIdProject + 1;

        Project project = new Project();
        project.setCode(code);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setEndAt(request.getEndAt());

        projectRepository.save(project);

        //Todo: Cần phải khởi tạo bảng quyền theo bảng quyền mặc định

        return ApiResponse.success("Tạo mới dự án thành công");
    }

    public ApiResponse<PaginationResponse<ProjectListResponse>> getProjects(PaginationRequest<ProjectFilterRequest> request) {
        ProjectFilterRequest filter = request.getFilter();

        int pageNow = request.getPageNow();
        int pageSize = request.getPageSize();
        int pageSpring = (pageNow < 0) ? pageNow - 1 : 0;
        Pageable pageable = PageRequest.of(pageSpring, pageSize);

        String keyword = (filter.getKeyword() != null) ? filter.getKeyword() : null;
        Page<ProjectListResponse> page = projectRepository.getProjects(keyword, pageable);

        PaginationResponse<ProjectListResponse> response = new PaginationResponse<>();
        response.setPageNow(pageNow);
        response.setPageSize(pageSize);
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setData(page.getContent());

        return ApiResponse.success(response, "Lấy danh sách dự án thành công");
    }

    public ApiResponse<Void> updateProject(ProjectUpdateRequest request) {
        Project project = projectRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Dự án không tồn tại"));

        projectMapping.updateEntity(request, project);

        projectRepository.save(project);

        return ApiResponse.success("Cập nhật dự án thành công");
    }

}
