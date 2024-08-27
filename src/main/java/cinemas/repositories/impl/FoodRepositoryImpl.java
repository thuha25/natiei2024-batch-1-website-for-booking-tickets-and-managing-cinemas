package cinemas.repositories.impl;

import cinemas.dtos.Pageable;
import cinemas.models.Food;
import cinemas.repositories.FoodsRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("foodsRepository")
public class FoodRepositoryImpl extends BaseRepositoryImpl<Food, Integer> implements FoodsRepository {
    public FoodRepositoryImpl() {
        super(Food.class);
    }
    @Override
    public List<Food> getFoodsByName(String name, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Food> criteriaQuery = criteriaBuilder.createQuery(Food.class);
        Root<Food> root = criteriaQuery.from(Food.class);

        Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(namePredicate);

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(finalPredicate);

        TypedQuery<Food> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getSize());

        return query.getResultList();
    }
    @Override
    public Integer countFoodsByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Food> root = criteriaQuery.from(Food.class);
        criteriaQuery.select(criteriaBuilder.count(root));

        Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(namePredicate);
        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        criteriaQuery.where(finalPredicate);

        Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
        return count.intValue();
    }
}
