package springbootboilerplate.application.user;

import cn.printf.springbootboilerplate.domain.department.Department;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springbootboilerplate.application.exception.NoSuchObjectException;
import cn.printf.springbootboilerplate.domain.department.DepartmentRepository;
import springbootboilerplate.application.user.rest.request.DepartmentAddRequest;
import springbootboilerplate.application.user.rest.request.DepartmentCriteria;
import springbootboilerplate.application.user.rest.request.DepartmentEditRequest;
import springbootboilerplate.application.user.rest.resource.DepartmentResource;
import springbootboilerplate.application.user.rest.resource.PageResource;
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