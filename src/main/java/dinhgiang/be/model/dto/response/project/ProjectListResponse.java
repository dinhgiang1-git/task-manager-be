package dinhgiang.be.model.dto.response.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectListResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private LocalDate endAt;

    private Long managerId;
    private String managerName;
    private String managerEmail;
}
