package dinhgiang.be.model.entity.workitem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "work_item_status")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false, updatable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = false;

    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;
}
