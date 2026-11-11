package dinhgiang.be.model.entity.project;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permission_schema_grants")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionSchemaGrant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_role_id", nullable = false)
    private Long projectRoleId;

    @Column(name = "project_permission_schema_id", nullable = false)
    private Long projectPermissionSchemaId;

    @Column(name = "project_permission_id", nullable = false)
    private Long projectPermissionId;

    @Column(name = "scope")
    @Builder.Default
    private String scope = "NONE";
}
