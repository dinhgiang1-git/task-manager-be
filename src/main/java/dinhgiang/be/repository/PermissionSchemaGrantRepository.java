package dinhgiang.be.repository;

import dinhgiang.be.model.entity.project.PermissionSchemaGrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionSchemaGrantRepository extends JpaRepository<PermissionSchemaGrant, Long> {
}
