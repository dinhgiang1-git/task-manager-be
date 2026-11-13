package dinhgiang.be.model.entity.workitem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "priorities")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sort_order", nullable = false)
    private Long sortOrder;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

}
