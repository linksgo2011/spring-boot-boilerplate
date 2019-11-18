package cn.printf.springbootboilerplate.rest.resource;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResource {
    private String username;
}
