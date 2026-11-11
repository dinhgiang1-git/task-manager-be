package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.project.ProjectRoleListResponse;
import dinhgiang.be.model.entity.project.ProjectRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
    boolean existsByCode(String code);

    @Query(value = """
        SELECT
            pr.id as "id",
            pr.code as "code",
            pr.name as "name",
            pr.is_active as "isActive"
        FROM project_roles pr
        WHERE (:keyword IS NULL OR LOWER(pr.code) LIKE LOWER('%' || :keyword ||'%'))
            AND (:keyword IS NULL OR LOWER(pr.name) LIKE LOWER('%' || :keyword ||'%'))
    """, nativeQuery = true)
    Page<ProjectRoleListResponse> getProjectRoles(
            @Param(value = "keyword") String keyword,
            @Param(value = "isActive") Boolean isActive,
            Pageable pageable);

    Optional<ProjectRole> findByCode(String code);

    List<ProjectRole> getAllByIsDefault(Boolean isDefault);
}
