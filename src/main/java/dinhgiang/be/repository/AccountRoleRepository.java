package dinhgiang.be.repository;

import dinhgiang.be.model.entity.account.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    List<AccountRole> findAllByAccountId(Long accountId);

    Optional<AccountRole> findByRoleIdAndAccountId(Long roleId, Long accountId);
}
