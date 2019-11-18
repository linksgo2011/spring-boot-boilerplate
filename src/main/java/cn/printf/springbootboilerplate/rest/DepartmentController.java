package cn.printf.springbootboilerplate.rest;

import cn.printf.springbootboilerplate.application.DepartmentService;
import cn.printf.springbootboilerplate.rest.request.DepartmentAddRequest;
import cn.printf.springbootboilerplate.rest.resource.DepartmentResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @GetMapping
    public Collection<DepartmentResource> getDepartments() {
        return departmentService.getDepartments();
    }

    @PostMapping
    public DepartmentResource addDepartment(@RequestBody @Valid DepartmentAddRequest departmentAddRequest) {
        return departmentService.addDepartment(departmentAddRequest);
    }

    @PutMapping("{departmentId}")
    public DepartmentResource updateDepartment(@PathVariable long departmentId, @RequestBody @Valid DepartmentAddRequest departmentAddRequest) {
        return departmentService.updateDepartment(departmentId, departmentAddRequest);
    }

    @DeleteMapping("{departmentId}")
    public void deleteDepartment(@PathVariable long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}
