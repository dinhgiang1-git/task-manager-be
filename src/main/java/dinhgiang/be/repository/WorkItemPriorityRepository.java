package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemPriorityRepository extends JpaRepository<Priority, Long> {
    boolean existsByCode(String code);
}
