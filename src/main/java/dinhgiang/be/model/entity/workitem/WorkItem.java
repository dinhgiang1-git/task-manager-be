package dinhgiang.be.model.entity.workitem;

import dinhgiang.be.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "work_items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkItem extends BaseEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "work_item_type_id", nullable = false)
    private Long workItemTypeId;

    @Column(name = "work_item_code", nullable = false, unique = true, updatable = false)
    private String workItemCode;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "priority_id", length = 20, nullable = false)
    private Long priorityId;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "work_item_status_id", nullable = false)
    private Long workItemStatusId;

    // Đóng việc
    @Column(name = "is_closed", nullable = false)
    @Builder.Default
    private boolean closed = false;

    @Column(name = "closed_reason", columnDefinition = "TEXT")
    private String closedReason;

    @Column(name = "closed_by")
    private Long closedBy;

    @Column(name = "closed_at")
    private Instant closedAt;


    // Lưu trữ
    @Column(name = "ended_at")
    private Instant endedAt;

    @Column(name = "archived_at")
    private Instant archivedAt;

    @Version
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Integer version = 0;
}
