package dinhgiang.be.service.interfaces;

import dinhgiang.be.model.common.ApiResponse;
import dinhgiang.be.model.dto.request.auth.LoginRequest;
import dinhgiang.be.model.dto.request.auth.LogoutRequest;
import dinhgiang.be.model.dto.response.auth.LoginResponse;

public interface IAuthService {
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<Void> logout(LogoutRequest request);
}
