package dinhgiang.be.model.dto.request.account;

import lombok.Data;

@Data
public class AccountUpdateRequest {
    private Long id;
    private String displayName;
    private String password;
    private String passwordNew;
}
