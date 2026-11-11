package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.account.AccountListResponse;
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
}
