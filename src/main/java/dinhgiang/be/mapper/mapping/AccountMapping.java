package dinhgiang.be.mapper.mapping;

import dinhgiang.be.mapper.common.IgnoreRecordMapper;
import dinhgiang.be.mapper.common.ToEntityMapper;
import dinhgiang.be.mapper.common.UpdateMapper;
import dinhgiang.be.model.dto.request.account.AccountCreateRequest;
import dinhgiang.be.model.dto.request.account.AccountUpdateRequest;
import dinhgiang.be.model.entity.account.Account;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AccountMapping extends
        ToEntityMapper<Account, AccountCreateRequest>,
        UpdateMapper<Account, AccountUpdateRequest>
{
    @Override
    @IgnoreRecordMapper
    Account toEntity(AccountCreateRequest request);

    @Override
    @IgnoreRecordMapper
    void updateEntity(AccountUpdateRequest request, @MappingTarget Account entity);
}
