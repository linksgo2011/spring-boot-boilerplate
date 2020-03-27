package springbootboilerplate.application.admin.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootboilerplate.application.admin.DepartmentAdminAppService;
import springbootboilerplate.application.admin.rest.command.DepartmentAddCommand;
import springbootboilerplate.application.admin.rest.query.DepartmentCriteria;
import springbootboilerplate.application.admin.rest.command.DepartmentEditCommand;
import springbootboilerplate.application.admin.rest.result.DepartmentResource;
import springbootboilerplate.application.admin.rest.result.PageResource;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/departments")
public class DepartmentController {

    private DepartmentAdminAppService departmentAdminAppService;

    @GetMapping
    public PageResource getDepartments(DepartmentCriteria departmentCriteria, Pageable pageable) {
        return departmentAdminAppService.getDepartments(departmentCriteria, pageable);
    }

    @PostMapping
    public DepartmentResource addDepartment(@RequestBody @Valid DepartmentAddCommand departmentAddCommand) {
        return departmentAdminAppService.addDepartment(departmentAddCommand);
    }

    @PutMapping("{departmentId}")
    public DepartmentResource updateDepartment(
            @PathVariable long departmentId,
            @RequestBody @Valid DepartmentEditCommand departmentAddRequest
    ) {
        return departmentAdminAppService.updateDepartment(departmentId, departmentAddRequest);
    }

    @DeleteMapping("{departmentId}")
    public void deleteDepartment(@PathVariable long departmentId) {
        departmentAdminAppService.deleteDepartment(departmentId);
    }
}
