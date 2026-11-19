package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.workitem.WorkItemStatusResponse;
import dinhgiang.be.model.entity.workitem.WorkItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkItemStatusRepository extends JpaRepository<WorkItemStatus, Long> {
    boolean existsByCode(String code);

    @Query(value = """
        SELECT wis.id
        FROM work_item_status wis
        WHERE wis.code = :code
    """, nativeQuery = true)
    Optional<Long> findByCode(@Param("code") String code);

    @NativeQuery(value = """
        SELECT
            wis.id as "id",
            wis.code as "code",
            wis.description as "description",
            wis.is_active as "isActive",
            wis.is_default as "isDefault"
        FROM work_item_status wis
        ORDER BY wis.id ASC
    """)
    List<WorkItemStatusResponse> getAllWorkItemStatues();
}
