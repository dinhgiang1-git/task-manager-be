package dinhgiang.be.model.entity.account;

import dinhgiang.be.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "action_name", nullable = false)
    private String actionName;

    @Column(name = "resource_name", nullable = false)
    private String resourceName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "parentId")
    private Long parentId;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
