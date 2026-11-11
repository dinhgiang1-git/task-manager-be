package dinhgiang.be.model.dto.response.account;

import lombok.Data;

import java.time.Instant;

@Data
public class AccountDetailResponse {
    private Long id;
    private String username;
    private String displayName;
    private String email;
    private Boolean isActive;
    private Instant lastLoginAt;
    private Instant createdAt;
    private Instant updateAt;
    private Long createdBy;
    private Long updateBy;

    //Role
    private Long roleId;
    private String roleName;
}
