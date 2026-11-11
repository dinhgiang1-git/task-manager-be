package dinhgiang.be.model.entity.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account_roles")
@Getter
@Setter
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accountId", nullable = false)
    private Long accountId;

    @Column(name = "roleId", nullable = false)
    private Long roleId;
}
