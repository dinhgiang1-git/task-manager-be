package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.project.ProjectMemberListResponse;
import dinhgiang.be.model.entity.project.ProjectMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Long> {

    @Query(value = """
        SELECT
                pm.id as "id",
                pm.join_at as "joinAt",
                    
                pm.project_id as "projectId",
                    
                a.id as "accountId",
                a.username as "username",
                a.display_name as "displayName",
                a.email as "email"
            
        FROM project_members pm
        LEFT JOIN accounts a ON a.id = pm.account_id
        WHERE (:keyword IS NULL 
                        OR LOWER(a.display_name) LIKE LOWER('%' || :keyword || '%')
                        OR LOWER(a.email) LIKE LOWER('%' || :keyword || '%'))
        AND :projectId = pm.project_id
    """, nativeQuery = true)
    Page<ProjectMemberListResponse> page(
            @Param(value = "keyword") String keyword,
            @Param(value = "projectId") Long projectId,
            Pageable pageable);

    boolean existsByAccountId(Long accountId);

    List<ProjectMember> findByAccountId(Long accountId);
}
