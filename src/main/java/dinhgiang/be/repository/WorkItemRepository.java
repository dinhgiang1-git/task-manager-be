package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.workitem.WorkItemListResponse;
import dinhgiang.be.model.entity.workitem.WorkItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {

    @Query(value = """
        SELECT COALESCE(MAX(wi.id), 1)
        FROM work_items wi
    """, nativeQuery = true)
    Long getMaxId();

    @Query(value = """
        SELECT
                
            wi.id as "id",
            wi.project_id as "projectId",
            wi.work_item_code as "workItemCode",
            wi.title as "title",
            wi.parent_id as "parentId",
            wi.due_date as "dueDate",
            wi.owner_id as "ownerId",
            
            wit.id as "workItemTypeId",
            wit.code as "workItemTypeCode",
                
            p.id as "priorityId",
            p.code as "priorityCode",
            p.name as "priorityName",
                
            wis.id as "workItemStatusId",
            wis.code as "workItemStatusCode"
                
        FROM work_items wi
        LEFT JOIN work_item_types wit ON wit.id = wi.work_item_type_id
        LEFT JOIN priorities p ON p.id = wi.priority_id
        LEFT JOIN work_item_status wis ON wis.id = wi.work_item_status_id
        
        WHERE wi.project_id = :projectId
        AND (:keyword IS NULL
                      OR LOWER(wi.work_item_code) LIKE LOWER('%' || :keyword || '%')
                      OR LOWER(wi.title) LIKE LOWER('%' || :keyword || '%'))
    """, countQuery = """
    SELECT
        COUNT(*)
        FROM work_items wi
        LEFT JOIN work_item_types wit ON wit.id = wi.work_item_type_id
        LEFT JOIN priorities p ON p.id = wi.priority_id
        LEFT JOIN work_item_status wis ON wis.id = wi.work_item_status_id
        WHERE wi.project_id = :projectId
        AND (:keyword IS NULL
                      OR LOWER(wi.work_item_code) LIKE LOWER('%' || :keyword || '%')
                      OR LOWER(wi.title) LIKE LOWER('%' || :keyword || '%'))
    """, nativeQuery = true)
    Page<WorkItemListResponse> page(
            @Param(value = "projectId") Long projectId,
            @Param(value = "keyword") String keyword,
            Pageable pageable);
}
