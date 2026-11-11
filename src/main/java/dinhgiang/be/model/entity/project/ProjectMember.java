package dinhgiang.be.model.entity.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "project_members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "project_role_id", nullable = false)
    private Long projectRoleId;

    @Column(name = "join_at", nullable = false)
    private Instant joinAt;

    @Column(name = "left_at")
    private Instant leftAt;

    @Column(name = "added_by", nullable = false)
    private Long addedBy;
}
