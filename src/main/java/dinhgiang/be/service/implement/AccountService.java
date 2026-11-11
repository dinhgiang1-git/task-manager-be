package dinhgiang.be.service.implement;

import dinhgiang.be.exception.BusinessException;
import dinhgiang.be.mapper.mapping.AccountMapping;
import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.PaginationRequest;
import dinhgiang.be.model.common.PaginationResponse;
import dinhgiang.be.model.dto.request.account.AccountCreateRequest;
import dinhgiang.be.model.dto.request.account.AccountFilterRequest;
import dinhgiang.be.model.dto.request.account.AccountUpdateRequest;
import dinhgiang.be.model.dto.response.account.AccountDetailResponse;
import dinhgiang.be.model.dto.response.account.AccountListResponse;
import dinhgiang.be.model.entity.account.Account;
import dinhgiang.be.repository.AccountRepository;
import dinhgiang.be.security.util.SecurityUtil;
import dinhgiang.be.service.interfaces.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final AccountMapping accountMapping;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<Void> createAccount(AccountCreateRequest request) {
        Account account = accountMapping.toEntity(request);
        account.setCreatedAt(Instant.now());
//        account.setCreatedBy(SecurityUtil.getRequireAccountId());
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountRepository.save(account);

        return ApiResponse.success("Tạo mới tài khoản thành công");
    }

    public ApiResponse<PaginationResponse<AccountListResponse>> getAccounts(PaginationRequest<AccountFilterRequest> request) {
        AccountFilterRequest filter = request.getFilter();

        int pageSize = request.getPageSize();
        int pageNow = request.getPageNow();
        int springPage = pageNow > 0 ? pageNow - 1 : 0;
        Pageable pageable = PageRequest.of(springPage, pageSize);

        String keyword = (filter.getKeyword() != null) ? filter.getKeyword() : null;
        Boolean isActive = (filter.getIsActive() != null) ? filter.getIsActive() : null;
        Page<AccountListResponse> page = accountRepository.page(keyword, isActive, pageable);

        PaginationResponse<AccountListResponse> response = new PaginationResponse<>();
        response.setPageNow(pageNow);
        response.setPageSize(pageSize);
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setData(page.getContent());

        return ApiResponse.success(response, "Lấy danh sách account thành công");
    }

    public ApiResponse<Long> updateAccount(AccountUpdateRequest request) {
        Account account = accountRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Tài khoản không tồn tại"));

        accountMapping.updateEntity(request, account);
        account.setUpdateAt(Instant.now());
        account.setUpdateBy(SecurityUtil.getRequireAccountId());

        account = accountRepository.save(account);

        return ApiResponse.success(account.getId(), "Cập nhật tài khoản thành công");
    }

    public ApiResponse<AccountDetailResponse> getAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new BusinessException("Tài khoản không tồn tại");
        }

        AccountDetailResponse response = accountRepository.getAccount(id);

        return ApiResponse.success(response, "Lấy chi tiết tài khoản thành công");
    }

    public ApiResponse<Void> lockAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tài khoản không tồn tại"));

        account.setIsActive(false);
        account.setUpdateAt(Instant.now());
        account.setUpdateBy(SecurityUtil.getRequireAccountId());

        accountRepository.save(account);

        return ApiResponse.success("Khoá tài khoản thành công");
    }
}
