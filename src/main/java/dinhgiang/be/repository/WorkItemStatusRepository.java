package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.WorkItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkItemStatusRepository extends JpaRepository<WorkItemStatus, Long> {
    boolean existsByCode(String code);

    Optional<Long> findByCode(String code);
}
