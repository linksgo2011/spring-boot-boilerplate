package cn.printf.springbootboilerplate.rest.resource;

import cn.printf.springbootboilerplate.domain.Department;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Data
public class DepartmentResource {
    private Long id;

    private String name;

    private Boolean enabled;

    private Long pid;

    private Timestamp createTime;

    public static DepartmentResource of(Department department) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(department, DepartmentResource.class);
    }
}
