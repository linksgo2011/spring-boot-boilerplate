package cn.printf.springbootboilerplate.rest.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DepartmentAddRequest {
    private String name;

    private Boolean enabled;

    private Long pid;
}
