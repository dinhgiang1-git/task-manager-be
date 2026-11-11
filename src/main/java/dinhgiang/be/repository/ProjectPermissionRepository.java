package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.project.ProjectPermissionListResponse;
import dinhgiang.be.model.entity.project.ProjectPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectPermissionRepository extends JpaRepository<ProjectPermission,Long> {
    Optional<ProjectPermission> findByCode(String code);

    boolean existsByCode(String code);

    @Query(value = """
        SELECT\s
                pp.id as "id",
                pp.code as "code",
                pp.name as "name",
                pp.is_active as "isActive"
        FROM project_permissions pp
        LEFT JOIN permission_schema_grants psg ON psg.project_permission_id = pp.id
        LEFT JOIN project_roles pr ON pr.id = psg.project_role_id
        WHERE (:roleId IS NULL OR pr.id = :roleId)
   \s""", nativeQuery = true)
    List<ProjectPermissionListResponse> getProjectPermissions(@Param(value = "roleId") Long roleId);

    List<ProjectPermission> getAllByIsDefault(Boolean isDefault);
}
