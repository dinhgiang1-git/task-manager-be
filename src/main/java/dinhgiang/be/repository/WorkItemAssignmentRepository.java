package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.WorkItemAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkItemAssignmentRepository extends JpaRepository<WorkItemAssignment, Long> {
    WorkItemAssignment findByWorkItemId(Long workItemId);

    Set<Long> findAllByAccountId(Long accountId);

    @Query("SELECT wia.accountId FROM WorkItemAssignment wia WHERE wia.workItemId = :workItemId")
    Set<Long> findAllByWorkItemId(@Param("workItemId") Long workItemId);

    List<WorkItemAssignment> findByWorkItemIdOrderByIdAsc(Long workItemId);

    void deleteByWorkItemIdAndAccountId(Long workItemId, Long accountId);

    void deleteByWorkItemIdAndAccountIdIn(Long workItemId, Collection<Long> accountIds);

    Optional<WorkItemAssignment> findByWorkItemIdAndAccountId(Long workItemId, Long accountId);

    void deleteByWorkItemIdInAndAccountId(Collection<Long> workItemIds, Long accountId);
}
