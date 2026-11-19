package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.workitem.WorkItemCommentResponse;
import dinhgiang.be.model.entity.workitem.WorkItemComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkItemCommentRepository extends JpaRepository<WorkItemComment, Long> {

    @NativeQuery(value = """
        SELECT
                wic.id as "id",
                wic.work_item_id as "workItemId",
                wic.body as "body",
                
                a.id as "authorId",
                a.display_name as "authorName",
                a.email as "authorEmail"
            
        FROM work_item_comments wic
        LEFT JOIN accounts a ON a.id = wic.author_id
        ORDER BY wic.id DESC 
    """)
    List<WorkItemCommentResponse> getWorkItemComments(@Param(value = "workItemId") Long workItemId);
}
