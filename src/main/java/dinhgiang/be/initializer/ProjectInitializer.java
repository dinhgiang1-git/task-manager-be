package dinhgiang.be.initializer;

import dinhgiang.be.initializer.data.project.*;
import dinhgiang.be.initializer.data.workitem.WorkItemPriorityData;
import dinhgiang.be.initializer.data.workitem.WorkItemPrioritySeed;
import dinhgiang.be.initializer.data.workitem.WorkItemTypeData;
import dinhgiang.be.initializer.data.workitem.WorkItemTypeSeed;
import dinhgiang.be.model.entity.project.PermissionSchema;
import dinhgiang.be.model.entity.project.PermissionSchemaGrant;
import dinhgiang.be.model.entity.project.ProjectPermission;
import dinhgiang.be.model.entity.project.ProjectRole;
import dinhgiang.be.model.entity.workitem.Priority;
import dinhgiang.be.model.entity.workitem.WorkItemType;
import dinhgiang.be.model.enums.WorkItemPriority;
import dinhgiang.be.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectInitializer implements CommandLineRunner {
    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectPermissionRepository projectPermissionRepository;
    private final PermissionSchemaRepository  permissionSchemaRepository;
    private final PermissionSchemaGrantRepository permissionSchemaGrantRepository;
    private final WorkItemTypeRepository workItemTypeRepository;
    private final WorkItemPriorityRepository workItemPriorityRepository;

    @Override
    public void run(String... args) throws Exception {

        //Khởi tạo role mặc định của dự án
        ProjectRole projectRole;
        for (RoleSeed seed : RoleData.DATA) {
            if (projectRoleRepository.existsByCode(seed.getCode())) continue;

            projectRole = new ProjectRole();
            projectRole.setCode(seed.getCode());
            projectRole.setName(seed.getName());
            projectRole.setIsActive(seed.getIsActive());
            projectRole.setIsDefault(seed.getIsDefault());

            projectRoleRepository.save(projectRole);
        }

        //Khởi tạo permission mặc định của dự án
        ProjectPermission projectPermission;
        for (PermissionSeed seed : PermissionData.DATA) {
            if (projectPermissionRepository.existsByCode(seed.getCode())) continue;

            projectPermission = new ProjectPermission();
            projectPermission.setCode(seed.getCode());
            projectPermission.setName(seed.getName());
            projectPermission.setIsActive(seed.getIsActive());
            projectPermission.setIsDefault(seed.getIsDefault());

            projectPermissionRepository.save(projectPermission);
        }

        //Khởi tạo bảng quyền mặc định
        PermissionSchema permissionSchema;
        for (PermissionSchemaSeed seed : PermissionSchemaData.DATA) {
            if (permissionSchemaRepository.existsByCode(seed.getCode())) continue;

            permissionSchema = new PermissionSchema();
            permissionSchema.setCode(seed.getCode());
            permissionSchema.setName(seed.getName());
            permissionSchema.setIsActive(seed.getIsActive());
            permissionSchema.setIsDefault(seed.getIsDefault());

            permissionSchemaRepository.save(permissionSchema);
        }

        //Gán quyền vào vai trò mặc định
        PermissionSchemaGrant permissionSchemaGrant;
        permissionSchema = permissionSchemaRepository.findByCode("DEFAULT_SCHEMA");
        List<ProjectPermission> projectPermissions = projectPermissionRepository.getAllByIsDefault(true);
        List<ProjectRole> projectRoles = projectRoleRepository.getAllByIsDefault(true);
        for (ProjectRole pr : projectRoles) {
            switch (pr.getCode()) {
                case "MANAGER": {
                    for (ProjectPermission pp : projectPermissions) {
                        permissionSchemaGrant = new PermissionSchemaGrant();
                        permissionSchemaGrant.setProjectRoleId(pr.getId());
                        permissionSchemaGrant.setProjectPermissionId(pp.getId());
                        permissionSchemaGrant.setProjectPermissionSchemaId(permissionSchema.getId());

                        permissionSchemaGrantRepository.saveAndFlush(permissionSchemaGrant);
                    }
                }
                case "TESTER": {
                    Set<String> permissionsTester = Set.of(
                            "XEM_TAT_CA_VIEC",
                            "XEM_VIEC",
                            "TAO_BUG",
                            "GIAO_NGUOI",
                            "SUA_VIEC",
                            "DONG_VIEC",
                            "MO_LAI",
                            "CAP_NHAT_PHAN_VIEC",
                            "DINH_KEM_FILE",
                            "BINH_LUAN");
                    for (ProjectPermission pp : projectPermissions) {
                        if (permissionsTester.contains(pp.getCode())) {
                            permissionSchemaGrant = new PermissionSchemaGrant();
                            permissionSchemaGrant.setProjectRoleId(pr.getId());
                            permissionSchemaGrant.setProjectPermissionSchemaId(permissionSchema.getId());
                            permissionSchemaGrant.setProjectPermissionId(pp.getId());

                            permissionSchemaGrantRepository.saveAndFlush(permissionSchemaGrant);
                        }
                    }
                }
                case "MEMBER": {
                    Set<String> permissionsMember = Set.of(
                            "XEM_TAT_CA_VIEC",
                            "XEM_VIEC",
                            "CAP_NHAT_PHAN_VIEC",
                            "DINH_KEM_FILE",
                            "BINH_LUAN");
                    for (ProjectPermission pp : projectPermissions) {
                        if (permissionsMember.contains(pp.getCode())) {
                            permissionSchemaGrant = new PermissionSchemaGrant();
                            permissionSchemaGrant.setProjectRoleId(pr.getId());
                            permissionSchemaGrant.setProjectPermissionSchemaId(permissionSchema.getId());
                            permissionSchemaGrant.setProjectPermissionId(pp.getId());

                            permissionSchemaGrantRepository.saveAndFlush(permissionSchemaGrant);
                        }
                    }
                }
            }
        }

        //Khởi tạo loại công việc mặc định
        for (WorkItemTypeSeed seed : WorkItemTypeData.DATA) {
            if (workItemTypeRepository.existsByCode(seed.getCode())) {
                log.info("Loại công việc {} đã tồn tại", seed.getCode());
                continue;
            }
            WorkItemType workItemType = new WorkItemType();
            workItemType.setCode(seed.getCode());
            workItemType.setDescription(seed.getDescription());
            workItemType.setIsActive(seed.getIsActive());
            workItemType.setIsDefault(seed.getIsDefault());
            workItemTypeRepository.save(workItemType);
            log.info("Loại công việc mặc định {} được khởi tạo", seed.getCode());
        }

        //Khởi tạo độ ưu tiên công việc
        for (WorkItemPrioritySeed seed : WorkItemPriorityData.DATA) {
            if (workItemPriorityRepository.existsByCode(seed.getCode())) {
                log.info("Độ ưu tiên {} đã tồn tại",  seed.getCode());
                continue;
            }
            Priority workItemPriority = new Priority();
            workItemPriority.setCode(seed.getCode());
            workItemPriority.setName(seed.getName());
            workItemPriority.setSortOrder(seed.getShortOder());
            workItemPriority.setIsActive(seed.getIsActive());
            workItemPriority.setIsDefault(seed.getIsDefault());
            workItemPriorityRepository.save(workItemPriority);
            log.info("Độ ưu tiên {} được khởi tạo", seed.getCode());
        }
    }
}
