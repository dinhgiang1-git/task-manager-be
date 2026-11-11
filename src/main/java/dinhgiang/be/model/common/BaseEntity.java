package dinhgiang.be.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updateAt")
    @UpdateTimestamp
    private Instant updateAt;

    @Column(name = "createdBy", updatable = false)
    private Long createdBy;

    @Column(name = "updateBy")
    private Long updateBy;
}
