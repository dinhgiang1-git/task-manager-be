package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.WorkItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {

    @Query(value = """
        SELECT COALESCE(MAX(wi.id), 0)
        FROM work_items wi
    """, nativeQuery = true)
    Long getMaxId();
}
