package dinhgiang.be.model.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationResponse<T> {
    private int pageNow;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private List<T> data = new ArrayList<>();
}
