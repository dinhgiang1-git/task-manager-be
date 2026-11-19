package dinhgiang.be.model.entity.workitem;

import dinhgiang.be.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "work_item_comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemComment extends BaseEntity {

    @Column(name = "work_item_id", nullable = false)
    private Long workItemId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;
}
