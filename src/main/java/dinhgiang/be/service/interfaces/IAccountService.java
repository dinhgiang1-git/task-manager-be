package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.account.AccountCreateRequest;
import dinhgiang.be.model.dto.request.account.AccountFilterRequest;
import dinhgiang.be.model.dto.request.account.AccountUpdateRequest;
import dinhgiang.be.model.dto.response.account.AccountDetailResponse;
import dinhgiang.be.model.dto.response.account.AccountListResponse;

public interface IAccountService {

    ApiResponse<Void> createAccount(AccountCreateRequest request);

    ApiResponse<PaginationResponse<AccountListResponse>> getAccounts(PaginationRequest<AccountFilterRequest> request);

    ApiResponse<AccountDetailResponse> getAccount(Long id);

    ApiResponse<Long> updateAccount(AccountUpdateRequest request);

    ApiResponse<Void> lockAccount(Long id);
}
