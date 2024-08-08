package cinemas.repositories;


import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    <S extends T> S save(S entity);
    <S extends T> List<S> saveAll(List<S> entities);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    void delete(T entity);
    void deleteAll(List<? extends T> entities);
    void deleteAll();
}
