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
import dinhgiang.be.model.entity.project.*;
import dinhgiang.be.repository.*;
import dinhgiang.be.security.util.SecurityUtil;
import dinhgiang.be.service.interfaces.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapping projectMapping;
    private final PermissionSchemaRepository permissionSchemaRepository;
    private final PermissionSchemaGrantRepository permissionSchemaGrantRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRoleRepository  projectRoleRepository;

    @Transactional
    public ApiResponse<Void> createProject(ProjectCreateRequest request) {
        //Tạo dự án
        Long maxIdProject = projectRepository.getProjectIdMax();
        String projectCode = "KAN " + maxIdProject + 1;

        Project project = new Project();
        project.setCode(projectCode);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setEndAt(request.getEndAt());

        project = projectRepository.saveAndFlush(project);

        // Gán bảng quyền dự án
        PermissionSchema permissionSchema = permissionSchemaRepository.findById(request.getPermissionSchemaId())
                .orElseThrow(() -> new BusinessException("Bảng quyền dự án không tồn tại"));
        Long oldPermissionSchemaId = permissionSchema.getId();

        if (permissionSchema.getCode().equals("DEFAULT_SCHEMA")) {
            permissionSchema = new PermissionSchema();
            permissionSchema.setCode("P_SCHEMA_" + projectCode);
            permissionSchema.setName("Bảng quyền dự án " + project.getName());

            permissionSchema = permissionSchemaRepository.saveAndFlush(permissionSchema);

            helperSavedPermissionShemaGrant(permissionSchema, oldPermissionSchemaId);
        } else {
            helperSavedPermissionShemaGrant(permissionSchema, oldPermissionSchemaId);
        }

        //Gán người quản lí dự án
        ProjectRole projectRole = projectRoleRepository.findByCode("MANAGER")
                .orElseThrow(() -> new BusinessException("Không tìm thấy vai trò MANAGER"));

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProjectId(project.getId());
        projectMember.setAccountId(SecurityUtil.getRequireAccountId());
        projectMember.setProjectRoleId(projectRole.getId());
        projectMember.setJoinAt(Instant.now());
        projectMember.setAddedBy(SecurityUtil.getRequireAccountId());

        projectMemberRepository.saveAndFlush(projectMember);

        return ApiResponse.success("Tạo mới dự án thành công");
    }

    private void helperSavedPermissionShemaGrant(PermissionSchema permissionSchema, Long oldPermissionSchemaId) {
        List<PermissionSchemaGrant> permissionSchemaGrants = permissionSchemaGrantRepository.findAllByProjectPermissionSchemaId(oldPermissionSchemaId);
        PermissionSchemaGrant permissionSchemaGrant;
        for (PermissionSchemaGrant psg : permissionSchemaGrants) {
            permissionSchemaGrant = new PermissionSchemaGrant();
            permissionSchemaGrant.setProjectRoleId(psg.getProjectRoleId());
            permissionSchemaGrant.setProjectPermissionSchemaId(permissionSchema.getId());
            permissionSchemaGrant.setProjectPermissionId(psg.getProjectPermissionId());

            permissionSchemaGrantRepository.save(permissionSchemaGrant);
        }
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
