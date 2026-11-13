package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.account.AccountListResponse;
import dinhgiang.be.model.dto.response.project.ProjectDetailResponse;
import dinhgiang.be.model.dto.response.project.ProjectListResponse;
import dinhgiang.be.model.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = """
        SELECT COALESCE(MAX(p.id), 0)
        FROM projects p
    """, nativeQuery = true)
    Long getProjectIdMax();

    @Query(value = """
        SELECT
                p.id as "id",
                p.code as "code",
                p.name as "name",
                p.description as "description",
                p.end_at as "endAt"
        FROM projects p
        WHERE (:keyword IS NULL OR LOWER(p.code) LIKE LOWER('%' || :keyword ||'%'))
    """, countQuery = """
        SELECT
                COUNT(*)
        FROM projects p
        WHERE (:keyword IS NULL OR LOWER(p.code) LIKE LOWER('%' || :keyword ||'%'))
    """, nativeQuery = true)
    Page<ProjectListResponse> getProjects(
            @Param(value = "keyword") String keyword,
            Pageable pageable
    );

    @Query(value = """
        SELECT
                p.id as "id",
                p.code as "code",
                p.name as "name",
                p.description as "description",
                p.end_at as "endAt",
                    
                ps.id as "projectSchemaId",
                ps.code as "projectShemaCode",
                ps.name as "projectSchemaName",
                
                a.id as "managerId",
                a.display_name as "managerName"
                    
        FROM projects p
        LEFT JOIN permission_schemas ps ON ps.id = p.project_schema_id
        LEFT JOIN project_members pm ON pm.project_id = p.id
        LEFT JOIN accounts a ON a.id = pm.account_id
        WHERE p.id = :id 
        AND pm.id = :roleId
    """, nativeQuery = true)
    ProjectDetailResponse getProjectDetailById(
            @Param(value = "id") Long id,
            @Param(value = "roleId") Long roleId);
}
