package springbootboilerplate.application.admin.rest.result;

import cn.printf.springbootboilerplate.domain.department.Department;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Getter
@NoArgsConstructor
@Data
public class DepartmentResource {
    private Long id;

    private String name;

    private Boolean enabled;

    private Long pid;

    public static DepartmentResource of(Department department) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(department, DepartmentResource.class);
    }
}
