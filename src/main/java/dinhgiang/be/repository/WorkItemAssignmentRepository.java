package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.WorkItemAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemAssignmentRepository extends JpaRepository<WorkItemAssignment, Long> {
}
