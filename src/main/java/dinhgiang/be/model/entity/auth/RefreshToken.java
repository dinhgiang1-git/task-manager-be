package dinhgiang.be.model.entity.auth;

import dinhgiang.be.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken extends BaseEntity {

    @Column(name = "accountId", nullable = false)
    private Long accountId;

    @Column(name = "tokenHash", nullable = false, unique = true)
    private String tokenHash;

    @Column(name = "expiresAt", nullable = false)
    private Instant expiresAt;

    @Column(name = "revokedAt")
    private Instant revokedAt;

}
