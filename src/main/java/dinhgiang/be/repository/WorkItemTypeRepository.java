package dinhgiang.be.repository;

import dinhgiang.be.model.entity.workitem.WorkItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkItemTypeRepository extends JpaRepository<WorkItemType, Long> {
    boolean existsByCode(String code);

    List<WorkItemType> findAllByIsDefault(Boolean isDefault);

    List<WorkItemType> findAllByIsDefaultAndIsActive(Boolean isDefault, Boolean isActive);
}
