package springbootboilerplate.modules.admin.application;

import cn.printf.springbootboilerplate.domain.Department;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springbootboilerplate.exception.NoSuchObjectException;
import springbootboilerplate.repository.DepartmentRepository;
import springbootboilerplate.modules.admin.rest.request.DepartmentAddRequest;
import springbootboilerplate.modules.admin.rest.request.DepartmentCriteria;
import springbootboilerplate.modules.admin.rest.request.DepartmentEditRequest;
import springbootboilerplate.modules.admin.rest.resource.DepartmentResource;
import springbootboilerplate.modules.admin.rest.resource.PageResource;
import springbootboilerplate.utils.CriteriaHelper;

@Service
@AllArgsConstructor
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public PageResource getDepartments(DepartmentCriteria departmentCriteria, Pageable pageable) {
        Page<DepartmentResource> page = departmentRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> CriteriaHelper.getPredicate(root, departmentCriteria, criteriaBuilder), pageable
        ).map(DepartmentResource::of);
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
