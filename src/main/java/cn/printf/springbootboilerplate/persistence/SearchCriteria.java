package cn.printf.springbootboilerplate.persistence;

import com.querydsl.jpa.impl.JPAQuery;

public abstract class SearchCriteria {

    public void assembleFilters(JPAQuery<?> jpaQuery) {}

    public void assembleOrders(JPAQuery<?> jpaQuery) {}

    public void assembleFetchJoin(JPAQuery<?> jpaQuery) {}

}
