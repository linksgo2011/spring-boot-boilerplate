package cn.printf.springbootboilerplate.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
/**
 * 通过注解构建基本的查询机制
 */
public class QueryProcessor {
    private static final String DELIMITER = ",";
    private static final String LIKE = "%";
    private static final String JOIN_DELIMITER = ">";

    @SuppressWarnings("unchecked")
    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (query == null) {
            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        }
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                // 开启字段
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                handleField(root, query, criteriaBuilder, predicates, field);
                // 关闭字段
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        int size = predicates.size();
        return criteriaBuilder.and(predicates.toArray(new Predicate[size]));
    }

    private static <R, Q> void handleField(
            Root<R> root,
            Q query,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates,
            Field field
    ) throws IllegalAccessException {
        Query queryAnnotation = field.getAnnotation(Query.class);
        // 没有注解就不处理
        if (null == queryAnnotation) {
            return;
        }
        // 查询名称
        String propName = queryAnnotation.propName();
        // 连表查询的字段名称
        String joinName = queryAnnotation.joinName();
        // 模糊搜索
        String blurry = queryAnnotation.blurry();
        // 默认使用字段名称
        String attributeName = StringUtils.isBlank(propName) ? field.getName() : propName;

        // 获取字段类型和值
        Class<?> fieldType = field.getType();
        Object fieldValue = field.get(query);
        if (null == fieldValue) {
            return;
        }
        Join join = null;

        // 多字段模糊搜索
        if (StringUtils.isNotEmpty(blurry)) {
            Predicate subOrPredicate = buildBlurrySubPredicate(root, criteriaBuilder, blurry, fieldValue);
            predicates.add(subOrPredicate);
        }

        if (StringUtils.isNotEmpty(joinName)) {
            join = buildJoin(root, queryAnnotation, joinName);
        }

        buildBasePredicate(
                root,
                criteriaBuilder,
                predicates,
                queryAnnotation,
                attributeName,
                fieldType,
                fieldValue,
                join
        );
    }

    private static <R> void buildBasePredicate(Root<R> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates, Query queryAnnotation, String attributeName, Object fieldType, Object fieldValue, Join join) {
        switch (queryAnnotation.type()) {
            case EQUAL:
                Predicate atomicPredicate = criteriaBuilder.equal(getExpression(attributeName, join, root)
                        .as((Class<? extends Comparable>) fieldType), fieldValue);
                predicates.add(atomicPredicate);
                break;
            case GREATER_THAN:
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(getExpression(attributeName, join, root)
                        .as((Class<? extends Comparable>) fieldType), (Comparable) fieldValue));
                break;
            case LESS_THAN:
                predicates.add(criteriaBuilder.lessThanOrEqualTo(getExpression(attributeName, join, root)
                        .as((Class<? extends Comparable>) fieldType), (Comparable) fieldValue));
                break;
            case LESS_THAN_NQ:
                predicates.add(criteriaBuilder.lessThan(getExpression(attributeName, join, root)
                        .as((Class<? extends Comparable>) fieldType), (Comparable) fieldValue));
                break;
            case INNER_LIKE:
                predicates.add(criteriaBuilder.like(getExpression(attributeName, join, root)
                        .as(String.class), LIKE + fieldValue.toString() + LIKE));
                break;
            case LEFT_LIKE:
                predicates.add(criteriaBuilder.like(getExpression(attributeName, join, root)
                        .as(String.class), LIKE + fieldValue.toString()));
                break;
            case RIGHT_LIKE:
                predicates.add(criteriaBuilder.like(getExpression(attributeName, join, root)
                        .as(String.class), fieldValue.toString() + LIKE));
            case IN:
                if (!CollectionUtils.isEmpty((Collection<Long>) fieldValue)) {
                    predicates.add(getExpression(attributeName, join, root).in((Collection<Long>) fieldValue));
                }
                break;
            default:
                break;
        }
    }

    private static <R> Join buildJoin(Root<R> root, Query q, String joinName) {
        String[] joinNames = joinName.split(JOIN_DELIMITER);
        Join joinChain = null;
        for (String name : joinNames) {
            if (joinChain == null) {
                joinChain = root.join(name, q.join());
            } else {
                joinChain = joinChain.join(name, q.join());
            }
        }
        return joinChain;
    }

    private static <R> Predicate buildBlurrySubPredicate(Root<R> root, CriteriaBuilder criteriaBuilder, String blurry, Object fieldValue) {
        String[] blurrys = blurry.split(DELIMITER);
        List<Predicate> orPredicate = new ArrayList<>();
        for (String s : blurrys) {
            orPredicate.add(criteriaBuilder.like(root.get(s).as(String.class), LIKE + fieldValue.toString() + LIKE));
        }
        Predicate[] p = new Predicate[orPredicate.size()];
        return criteriaBuilder.or(orPredicate.toArray(p));
    }

    @SuppressWarnings("unchecked")
    private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
        if (!Objects.isNull(join)) {
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
    }

    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
