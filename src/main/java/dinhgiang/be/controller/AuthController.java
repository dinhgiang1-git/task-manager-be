package dinhgiang.be.controller;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.common.BaseController;
import dinhgiang.be.model.dto.request.auth.LoginRequest;
import dinhgiang.be.model.dto.request.auth.LogoutRequest;
import dinhgiang.be.model.dto.response.auth.LoginResponse;
import dinhgiang.be.service.interfaces.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Xác thực người dùng")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final IAuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return executeApiResult(() -> authService.login(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất")
    public ResponseEntity<ApiResponse<Void>> logout(@Valid @RequestBody LogoutRequest request) {
        return executeApiResult(() -> authService.logout(request));
    }


}
