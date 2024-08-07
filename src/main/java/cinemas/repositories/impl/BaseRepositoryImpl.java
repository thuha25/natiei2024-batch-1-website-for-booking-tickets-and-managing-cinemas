package cinemas.repositories.impl;

import cinemas.repositories.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl<T, ID> implements BaseRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> domainClass;

    protected BaseRepositoryImpl(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("The given entity must not be null!");
        }
        return entityManager.merge(entity);
    }

    @Override
    public <S extends T> List<S> saveAll(List<S> entities) {
        if (entities == null) {
            throw new IllegalArgumentException("The given entities must not be null!");
        }
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            if (entity == null) {
                throw new IllegalArgumentException("The given entity must not be null!");
            }
            result.add(entityManager.merge(entity));
        }
        return result;
    }

    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null!");
        }
        return Optional.ofNullable(entityManager.find(domainClass, id));
    }

    @Override
    public List<T> findAll() {
        String hql = "FROM " + domainClass.getName();
        return entityManager.createQuery(hql, domainClass).getResultList();
    }


    @Override
    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null!");
        }
        T entity = entityManager.find(domainClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    @Override
    public void delete(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("The given entity must not be null!");
        }
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }



    @Override
    public void deleteAll(List<? extends T> entities) {
        if (entities == null) {
            throw new IllegalArgumentException("The given entities must not be null!");
        }
        for (T entity : entities) {
            if (entity == null) {
                throw new IllegalArgumentException("The given entities must not contain null values!");
            }
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        }
    }

    @Override
    public void deleteAll() {
        String hql = "DELETE FROM " + domainClass.getName();
        entityManager.createQuery(hql).executeUpdate();
    }
}
