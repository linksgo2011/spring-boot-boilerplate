package cn.printf.springbootboilerplate.application;

import cn.printf.springbootboilerplate.domain.Department;
import cn.printf.springbootboilerplate.exception.NoSuchObjectException;
import cn.printf.springbootboilerplate.repository.DepartmentRepository;
import cn.printf.springbootboilerplate.rest.request.DepartmentAddRequest;
import cn.printf.springbootboilerplate.rest.request.DepartmentCriteria;
import cn.printf.springbootboilerplate.rest.request.DepartmentEditRequest;
import cn.printf.springbootboilerplate.rest.resource.DepartmentResource;
import cn.printf.springbootboilerplate.rest.resource.PageResource;
import cn.printf.springbootboilerplate.utils.CriteriaHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public PageResource getDepartments(DepartmentCriteria departmentCriteria, Pageable pageable) {
        Page<DepartmentResource> page =  departmentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> CriteriaHelper.getPredicate(root, departmentCriteria, criteriaBuilder), pageable).map(DepartmentResource::of);
        return PageResource.toResource(page);
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

    public DepartmentResource updateDepartment(Long departmentId, DepartmentEditRequest departmentAddRequest) {
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
