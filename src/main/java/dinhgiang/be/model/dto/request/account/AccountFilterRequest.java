package dinhgiang.be.model.dto.request.account;

import lombok.Data;

@Data
public class AccountFilterRequest {
    private String keyword;
    private Boolean isActive;
}
