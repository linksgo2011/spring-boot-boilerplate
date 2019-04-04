package cn.printf.springbootboilerplate.persistence;

import cn.printf.springbootboilerplate.exception.NoSuchObjectException;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.CacheMode;
import org.hibernate.annotations.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Function;

import static org.hibernate.CacheMode.NORMAL;

@Transactional(readOnly = true)
public abstract class AbstractDao<T, I> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected JPAQuery<T> cacheable(JPAQuery<T> query) {
        return cacheable(query, NORMAL);
    }

    protected JPAQuery<T> cacheable(JPAQuery<T> query, CacheMode cacheMode) {
        return query
            .setHint(QueryHints.CACHEABLE, true)
            .setHint(QueryHints.CACHE_MODE, cacheMode);
    }

    @Transactional
    public void save(T model) {
        entityManager.persist(model);
    }

    protected abstract Function<I, T> findById();

    public Optional<T> findBy(I identity) {
        return Optional.ofNullable(findById().apply(identity));
    }

    public T mustFindBy(I identity) {
        return findBy(identity).orElseThrow(() ->
            new NoSuchObjectException(getEntityClass(), identity));
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        //TODO, recursive if nested subclass
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type type = parameterizedType.getActualTypeArguments()[0];
        return (Class<T>) type;
    }


}
