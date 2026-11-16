package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.workitem.MyWorkItemListResponse;
import dinhgiang.be.model.dto.response.workitem.WorkItemListResponse;
import dinhgiang.be.model.entity.workitem.WorkItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {

    List<WorkItem> findAllByParentIdAndClosedFalseAndArchivedAtIsNull(Long parentId);

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

    @Query(value = """
        SELECT
                wi.id as "id",
                wi.project_id as "projectId",
                wi.work_item_code as "workItemCode",
                wi.title as "title",
                wi.parent_id as "parentId",
                wi.due_date as "dueDate",
                
                a.id as "accountId",
                a.display_name as "accountDisplayName",
                a.email as "accountEmail",
                
                wit.id as "workItemTypeId",
                wit.code as "workItemTypeCode",
                
                p.id as "priorityId",
                p.code as "priorityCode",
                p.name as "priorityName",
                    
                wis.id as "workItemStatusId",
                wis.code as "workItemStatusCode"
            
        FROM work_items wi
        INNER JOIN work_item_assignment wia ON wia.work_item_id = wi.id
        LEFT JOIN accounts a ON a.id = wia.account_id
        LEFT JOIN work_item_types wit ON wit.id = wi.work_item_type_id
        LEFT JOIN priorities p ON p.id = wi.priority_id
        LEFT JOIN work_item_status wis ON wis.id = wia.work_item_status_id
        WHERE a.id = :accountId
        AND (:keyword IS NULL 
                     OR LOWER(wi.title) LIKE LOWER('%' || :keyword || '%')
                     OR LOWER(wi.work_item_code) LIKE ('%' || :keyword || '%'))
        ORDER BY wi.due_date ASC NULLS LAST, wi.id DESC
    """, countQuery = """
        SELECT COUNT(*)
        FROM work_items wi
        INNER JOIN work_item_assignment wia ON wia.work_item_id = wi.id
        WHERE wia.account_id = :accountId
          AND (:keyword IS NULL
               OR LOWER(wi.title) LIKE LOWER('%' || :keyword || '%')
               OR LOWER(wi.work_item_code) LIKE LOWER('%' || :keyword || '%'))
    """, nativeQuery = true)
    Page<MyWorkItemListResponse> myWorkItemPage(
            @Param(value = "accountId") Long accountId,
            @Param(value = "keyword") String keyword,
            Pageable pageable);
}
