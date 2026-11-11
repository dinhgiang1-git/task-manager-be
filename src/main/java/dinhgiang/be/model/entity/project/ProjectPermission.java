package dinhgiang.be.model.entity.project;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "project_permissions")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_default", nullable = false)
    @Builder.Default
    private Boolean isDefault = false;
}
