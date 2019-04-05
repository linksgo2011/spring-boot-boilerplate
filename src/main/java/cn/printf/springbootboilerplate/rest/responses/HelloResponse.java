package cn.printf.springbootboilerplate.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HelloResponse extends HalResponse {
    public String hello;
}
