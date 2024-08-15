package cinemas.dtos;

import java.util.List;

public class PaginationResult<T> {
    private Integer totalElements;
    private Integer totalPages;
    private final List<T> data;

    public PaginationResult(Integer totalElements, Integer size, List<T> data) {
        this.totalElements = totalElements;
        this.totalPages = totalElements % size == 0 ? totalElements / size : totalElements / size + 1;
        this.data = data;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<T> getData() {
        return data;
    }
}
