package dinhgiang.be.model.entity.workitem;

import dinhgiang.be.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkItemAssignment extends BaseEntity {

    @Column(name = "work_item_id", nullable = false)
    private Long workItemId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "work_item_status_id", nullable = false)
    private Long workItemStatusId;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

}
