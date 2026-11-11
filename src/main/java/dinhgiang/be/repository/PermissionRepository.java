package dinhgiang.be.repository;

import dinhgiang.be.model.entity.account.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    boolean existsByCode(String code);

    @Query(value = """
        SELECT p.id
        FROM permissions p
        WHERE p.code = :code
    """, nativeQuery = true)
    Long findByCodeGetId(String code);

    Permission findByCode(String code);
}
