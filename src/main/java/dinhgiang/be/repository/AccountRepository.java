package dinhgiang.be.repository;

import dinhgiang.be.model.dto.response.account.AccountDetailResponse;
import dinhgiang.be.model.dto.response.account.AccountListResponse;
import dinhgiang.be.model.entity.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    @Query(value = """
        SELECT 
                a.id as "id",
                a.username as "username",
                a.display_name as "displayName",
                a.email as "email",
                a.is_active as "isActive",
                a.last_login_at as "lastLoginAt",
                a.create_at as "createAt",
                a.update_at as "updateAt",
                a.created_by as "createBy",
                a.update_by as "updateBy",
                    
                r.id as "roleId",
                r.name as "roleName"        
        FROM accounts a
        LEFT JOIN account_roles ar ON ar.account_id = a.id
        LEFT JOIN roles r ON r.id = ar.role_id            
        WHERE (:isActive IS NULL OR a.is_active = :isActive)
        AND (:keyword IS NULL OR LOWER(a.username) LIKE LOWER('%' || :keyword ||'%'))
        AND (:keyword IS NULL OR LOWER(a.display_name) LIKE LOWER('%' || :keyword ||'%'))
        AND (:keyword IS NULL OR LOWER(a.email) LIKE LOWER('%' || :keyword ||'%'))
    """,countQuery = """
        SELECT COUNT(*)
        FROM accounts a
        WHERE (:isActive IS NULL OR a.is_active = :isActive)
        AND (:keyword IS NULL OR LOWER(a.username) LIKE LOWER('%' || :keyword ||'%'))
        AND (:keyword IS NULL OR LOWER(a.display_name) LIKE LOWER('%' || :keyword ||'%'))
        AND (:keyword IS NULL OR LOWER(a.email) LIKE LOWER('%' || :keyword ||'%'))
    """, nativeQuery = true)
    Page<AccountListResponse> page(@Param("keyword") String keyword,
                                   @Param("isActive") Boolean isActive,
                                   Pageable pageable);

    @Query(value = """
        SELECT 
                a.id as "id",
                a.username as "username",
                a.display_name as "displayName",
                a.email as "email",
                a.is_active as "isActive",
                a.last_login_at as "lastLoginAt",
                a.create_at as "createAt",
                a.update_at as "updateAt",
                a.created_by as "createBy",
                a.update_by as "updateBy",
                    
                r.id as "roleId",
                r.name as "roleName"
        FROM accounts a
        LEFT JOIN account_roles ar ON a.id = ar.account_id
        LEFT JOIN roles r ON r.id = ar.role_id
        WHERE a.id = :id
    """, nativeQuery = true)
    AccountDetailResponse getAccount(@Param("id") Long id);
}
