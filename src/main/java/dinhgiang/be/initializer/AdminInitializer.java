package dinhgiang.be.initializer;

import dinhgiang.be.initializer.data.system.PermissionData;
import dinhgiang.be.initializer.data.system.PermissionSeed;
import dinhgiang.be.model.entity.account.*;
import dinhgiang.be.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements CommandLineRunner {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository  rolePermissionRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder  passwordEncoder;
    private final AccountRoleRepository  accountRoleRepository;

    @Override
    public void run(String @NonNull ... args) {
        List<Permission> permissions = new ArrayList<>();
        for (PermissionSeed seed : PermissionData.DATA) {
            if (permissionRepository.existsByCode(seed.getCode())) {
                permissions.add(permissionRepository.findByCode(seed.getCode()));
                continue;
            }

            Permission permission = new Permission();
            permission.setCode(seed.getCode());
            permission.setActionName(seed.getActionName());
            permission.setResourceName(seed.getResourceName());
            permission.setDescription(seed.getDescription());
            permission.setCreatedAt(Instant.now());
            if (seed.getParentCode() != null && !seed.getParentCode().isBlank()) {
                permission.setParentId(permissionRepository.findByCodeGetId(seed.getParentCode()));
            }
            permission.setIsActive(seed.getIsActive());
            permission = permissionRepository.saveAndFlush(permission);
            permissions.add(permission);
        }

        Role role = roleRepository.findByCode("TMS_ADMIN").orElse(null);
        if (role == null) {
            role = new Role();
            role.setCode("TMS_ADMIN");
            role.setName("ADMIN");
            role.setDescription("Quản trị hệ thống");
            role.setIsActive(true);
            role.setCreatedAt(Instant.now());
            role = roleRepository.saveAndFlush(role);
            log.info("Created role {}", role);
        } else {
            log.info("Role already exists");
        }

        for (Permission permission : permissions) {
            RolePermission rolePermission = rolePermissionRepository
                    .findByRoleIdAndPermissionId(role.getId(), permission.getId()).orElse(null);
            if (rolePermission == null) {
                rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permission.getId());
                rolePermissionRepository.saveAndFlush(rolePermission);
            }
        }


        Account account = accountRepository.findByUsername("admin").orElse(null);
        if (account == null) {
            account = new Account();
            account.setUsername("admin");
            account.setDisplayName("Nguyễn Văn Admin");
            account.setPassword(passwordEncoder.encode("1211"));
            account.setEmail("nguyenvanadmin@gmail.com");
            account.setIsActive(true);
            account = accountRepository.saveAndFlush(account);
            log.info("Created account admin {}", account);
        } else log.info("Account admin already exists");

        AccountRole accountRole = accountRoleRepository.findByRoleIdAndAccountId(role.getId(), account.getId()).orElse(null);
        if (accountRole == null) {
            accountRole = new AccountRole();
            accountRole.setAccountId(account.getId());
            accountRole.setRoleId(role.getId());
            accountRole = accountRoleRepository.saveAndFlush(accountRole);
            log.info("Created account role {}", accountRole);
        } else log.info("Account role already exists");

    }

}
