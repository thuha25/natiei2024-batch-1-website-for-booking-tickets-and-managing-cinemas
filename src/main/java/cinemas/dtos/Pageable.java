package cinemas.dtos;

public class Pageable {
    Integer page;
    Integer size;
    public Pageable(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getOffset() {
        return (page - 1) * size;
    }

    public Integer getSize() {
        return size;
    }

}
