package springbootboilerplate.modules.admin.rest;

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
import springbootboilerplate.modules.admin.application.DepartmentService;
import springbootboilerplate.modules.admin.rest.request.DepartmentAddRequest;
import springbootboilerplate.modules.admin.rest.request.DepartmentCriteria;
import springbootboilerplate.modules.admin.rest.request.DepartmentEditRequest;
import springbootboilerplate.modules.admin.rest.resource.DepartmentResource;
import springbootboilerplate.modules.admin.rest.resource.PageResource;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping
    public PageResource getDepartments(DepartmentCriteria departmentCriteria, Pageable pageable) {
        return departmentService.getDepartments(departmentCriteria, pageable);
    }

    @PostMapping
    public DepartmentResource addDepartment(@RequestBody @Valid DepartmentAddRequest departmentAddRequest) {
        return departmentService.addDepartment(departmentAddRequest);
    }

    @PutMapping("{departmentId}")
    public DepartmentResource updateDepartment(
            @PathVariable long departmentId,
            @RequestBody @Valid DepartmentEditRequest departmentAddRequest
    ) {
        return departmentService.updateDepartment(departmentId, departmentAddRequest);
    }

    @DeleteMapping("{departmentId}")
    public void deleteDepartment(@PathVariable long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}
