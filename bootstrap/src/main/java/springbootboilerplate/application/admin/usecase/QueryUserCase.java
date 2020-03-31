package springbootboilerplate.application.admin.usecase;

import cn.printf.springbootboilerplate.common.Query;
import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;

public class QueryUserCase {

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Request {
        @Query(type = Query.Type.INNER_LIKE)
        private String username;

        @Query
        private Boolean enabled;

        @Query
        private Long pid;

        @Query(type = Query.Type.GREATER_THAN, propName = "createAt")
        private Timestamp startTime;

        @Query(type = Query.Type.LESS_THAN, propName = "createAt")
        private Timestamp endTime;
    }

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Response {
        private Long id;

        private String username;

        private String email;

        private String phone;

        private Boolean enabled;
    }

    public static Response toResponseFrom(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, Response.class);
    }
}
