package dinhgiang.be.model.common;

import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class BaseController {
    protected <T> ResponseEntity<ApiResponse<T>> executeApiResult(Supplier<ApiResponse<T>> supplier) {
        ApiResponse<T> result = supplier.get();
        return ResponseEntity.ok(result);
    }
}
