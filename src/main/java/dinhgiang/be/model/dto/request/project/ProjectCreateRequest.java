package dinhgiang.be.model.dto.request.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectCreateRequest {

    @NotBlank(message = "Tên dự án không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Bảng quyền của dự án không được để trống")
    private Long permissionSchemaId;

    private LocalDate endAt;
}
