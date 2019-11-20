package springbootboilerplate.modules.admin.rest.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class DepartmentAddRequest {
    @Size(min = 2, max = 36)
    private String name;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Long pid;
}
