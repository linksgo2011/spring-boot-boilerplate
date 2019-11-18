package cn.printf.springbootboilerplate.rest.request;

import cn.printf.springbootboilerplate.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DepartmentCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query
    private Long pid;

    @Query(type = Query.Type.GREATER_THAN,propName = "createTime")
    private Timestamp startTime;

    @Query(type = Query.Type.LESS_THAN,propName = "createTime")
    private Timestamp endTime;
}
