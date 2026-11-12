package dinhgiang.be.repository;

import dinhgiang.be.model.entity.project.PermissionSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionSchemaRepository extends JpaRepository<PermissionSchema, Long> {
    boolean existsByCode(String code);

    PermissionSchema findByCode(String code);

    List<PermissionSchema> findAllByIsActive(Boolean isActive);
}
