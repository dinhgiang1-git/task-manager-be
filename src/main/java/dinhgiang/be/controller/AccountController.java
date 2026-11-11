package dinhgiang.be.controller;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.BaseController;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.account.AccountCreateRequest;
import dinhgiang.be.model.dto.request.account.AccountFilterRequest;
import dinhgiang.be.model.dto.request.account.AccountUpdateRequest;
import dinhgiang.be.model.dto.response.account.AccountDetailResponse;
import dinhgiang.be.model.dto.response.account.AccountListResponse;
import dinhgiang.be.service.interfaces.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts")
@Tag(name = "Quản lý tài khoản")
@RequiredArgsConstructor
public class AccountController extends BaseController {
    private final IAccountService accountService;

    @PostMapping(value = "/create")
    @Operation(summary = "Tạo mới tài khoản")
    public ResponseEntity<ApiResponse<Void>> createAccount(@Valid @RequestBody AccountCreateRequest request) {
        return executeApiResult(() -> accountService.createAccount(request));
    }

    @PostMapping(value = "/gets")
    @Operation(summary = "Lấy danh sách tài khoản")
    public ResponseEntity<ApiResponse<PaginationResponse<AccountListResponse>>> getAccounts(
            @RequestBody PaginationRequest<AccountFilterRequest> request) {
        return executeApiResult(() -> accountService.getAccounts(request));
    }

    @GetMapping(value = "/get")
    @Operation(summary = "Lấy chi tiết tài khoản thành công")
    public ResponseEntity<ApiResponse<AccountDetailResponse>> getAccount(@RequestParam(value = "id") Long id) {
        return executeApiResult(() -> accountService.getAccount(id));
    }

    @PutMapping(value = "/update")
    @Operation(summary = "Cập nhật tài khoản")
    public ResponseEntity<ApiResponse<Long>> updateAccount(@RequestBody AccountUpdateRequest request) {
        return executeApiResult(() -> accountService.updateAccount(request));
    }

    @PutMapping(value = "/lock")
    @Operation(summary = "Khoá tài khoản")
    public ResponseEntity<ApiResponse<Void>> lockAccount(@RequestParam(value = "id") Long id) {
        return executeApiResult(() -> accountService.lockAccount(id));
    }
}
