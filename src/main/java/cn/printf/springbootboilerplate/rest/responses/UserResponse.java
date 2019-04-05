package cn.printf.springbootboilerplate.rest.responses;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.core.Relation;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(value = "user", collectionRelation = "users")
public class UserResponse extends HalResponse {
   private String username;
}
