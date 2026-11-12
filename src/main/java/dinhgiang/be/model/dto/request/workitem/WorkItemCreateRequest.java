package dinhgiang.be.model.dto.request.workitem;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkItemCreateRequest {

    @NotBlank(message = "Mã dự án không được để trống")
    private Long projectId;

    @NotBlank(message = "Loại công việc không được để trống")
    private Long wokItemTypeId;

    private Long parentId;

    @NotBlank(message = "Tiêu đề công việc không được để trống")
    private String title;

    private String description;

    @NotBlank(message = "Mã độ ưu tiên không được để trống")
    private Long priorityId;

    @Column(name = "due_date")
    private LocalDate dueDate;

}
