package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.WorkItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
