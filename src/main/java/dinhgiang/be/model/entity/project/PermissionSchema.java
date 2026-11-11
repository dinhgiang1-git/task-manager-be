package dinhgiang.be.model.entity.project;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permission_schemas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;
}
