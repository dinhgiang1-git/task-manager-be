package dinhgiang.be.model.dto.request.workitem;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class WorkItemCreateRequest {

    @NotNull(message = "Mã dự án không được để trống")
    private Long projectId;

    @NotNull(message = "Loại công việc không được để trống")
    private Long wokItemTypeId;

    private Long parentId;

    @NotBlank(message = "Tiêu đề công việc không được để trống")
    private String title;

    private String description;

    @NotNull(message = "Mã độ ưu tiên không được để trống")
    private Long priorityId;

    @Column(name = "due_date")
    private LocalDate dueDate;

    private Set<Long> accountIds;

    private Long ownerId;

}
