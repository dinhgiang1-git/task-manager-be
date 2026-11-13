package dinhgiang.be.model.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PaginationRequest<TFilter> {
    public static final int DEFAULT_PAGE_NOW = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    @Min(value = 1, message = "Page number must be at least 1")
    private int pageNow = DEFAULT_PAGE_NOW;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = MAX_PAGE_SIZE, message = "Page size cannot exceed " + MAX_PAGE_SIZE)
    private int pageSize = DEFAULT_PAGE_SIZE;

    @Valid
    private TFilter filter;
}
