package dinhgiang.be.repository;

import dinhgiang.be.model.entity.project.PermissionSchemaGrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionSchemaGrantRepository extends JpaRepository<PermissionSchemaGrant, Long> {
    List<PermissionSchemaGrant> findAllByProjectPermissionSchemaId(Long projectPermissionSchemaId);
}
