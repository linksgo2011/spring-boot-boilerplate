package springbootboilerplate.modules.admin.rest.request;

import lombok.Data;
import springbootboilerplate.annotation.Query;

import java.sql.Timestamp;

@Data
public class DepartmentCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query
    private Long pid;

    @Query(type = Query.Type.GREATER_THAN, propName = "createAt")
    private Timestamp startTime;

    @Query(type = Query.Type.LESS_THAN, propName = "createAt")
    private Timestamp endTime;
}
