package dinhgiang.be.repository;

import dinhgiang.be.model.entity.account.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    List<RolePermission> findAllByRoleId(Long roleId);

    Optional<RolePermission> findByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
