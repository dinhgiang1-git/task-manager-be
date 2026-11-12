package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.WorkItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemTypeRepository extends JpaRepository<WorkItemType, Long> {
}
