package cn.printf.springbootboilerplate.application;

import cn.printf.springbootboilerplate.domain.Department;
import cn.printf.springbootboilerplate.exception.NoSuchObjectException;
import cn.printf.springbootboilerplate.repository.DepartmentRepository;
import cn.printf.springbootboilerplate.rest.request.DepartmentAddRequest;
import cn.printf.springbootboilerplate.rest.resource.DepartmentResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Collection<DepartmentResource> getDepartments() {
        return departmentRepository.findAll().stream().map(DepartmentResource::of).collect(Collectors.toList());
    }

    public DepartmentResource addDepartment(DepartmentAddRequest departmentAddRequest) {
        Department department = Department
                .builder()
                .name(departmentAddRequest.getName())
                .pid(departmentAddRequest.getPid())
                .enabled(departmentAddRequest.getEnabled())
                .build();
        Department savedDepartment = departmentRepository.saveAndFlush(department);
        return DepartmentResource.of(savedDepartment);
    }

    public DepartmentResource updateDepartment(Long departmentId, DepartmentAddRequest departmentAddRequest) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchObjectException("department not found"));

        department.setName(departmentAddRequest.getName());
        department.setPid(departmentAddRequest.getPid());
        department.setEnabled(departmentAddRequest.getEnabled());

        Department savedDepartment = departmentRepository.saveAndFlush(department);
        return DepartmentResource.of(savedDepartment);
    }

    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}
