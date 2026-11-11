package dinhgiang.be.repository;

import dinhgiang.be.model.entity.project.PermissionSchemaGrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPermissionSchemaGrantRepository extends JpaRepository<PermissionSchemaGrant, Long> {

    @Query(value = """
        SELECT *
        FROM permission_schema_grants psg
        WHERE psg.project_permission_schema_id = :projectPermissionSchemaId
    """, nativeQuery = true)
    List<PermissionSchemaGrant> findAllBySchemaId(@Param("projectPermissionSchemaId") Long projectPermissionSchemaId);
}
