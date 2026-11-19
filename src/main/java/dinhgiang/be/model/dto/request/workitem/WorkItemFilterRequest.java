package dinhgiang.be.model.dto.request.workitem;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemFilterRequest {
    @NotNull(message = "Mã dự án không được để trống")
    private Long projectId;

    private String keyword;

    @Builder.Default
    private Boolean isArchive = false;
}
