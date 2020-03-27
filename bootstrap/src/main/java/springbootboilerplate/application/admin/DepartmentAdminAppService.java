package springbootboilerplate.application.admin;

import cn.printf.springbootboilerplate.domain.department.Department;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cn.printf.springbootboilerplate.domain.NoSuchObjectException;
import cn.printf.springbootboilerplate.domain.department.DepartmentRepository;
import springbootboilerplate.application.admin.rest.command.DepartmentAddCommand;
import springbootboilerplate.application.admin.rest.query.DepartmentCriteria;
import springbootboilerplate.application.admin.rest.command.DepartmentEditCommand;
import springbootboilerplate.application.admin.rest.result.DepartmentResource;
import springbootboilerplate.application.admin.rest.result.PageResource;
import springbootboilerplate.utils.CriteriaHelper;

@Service
@AllArgsConstructor
public class DepartmentAdminAppService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public PageResource getDepartments(DepartmentCriteria departmentCriteria, Pageable pageable) {
        Page<DepartmentResource> page = departmentRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> CriteriaHelper.getPredicate(root, departmentCriteria, criteriaBuilder), pageable
        ).map(DepartmentResource::of);
        return PageResource.toResource(page);
    }

    public DepartmentResource addDepartment(DepartmentAddCommand departmentAddCommand) {
        Department department = Department
                .builder()
                .name(departmentAddCommand.getName())
                .pid(departmentAddCommand.getPid())
                .enabled(departmentAddCommand.getEnabled())
                .build();
        Department savedDepartment = departmentRepository.saveAndFlush(department);
        return DepartmentResource.of(savedDepartment);
    }

    public DepartmentResource updateDepartment(Long departmentId, DepartmentEditCommand departmentAddRequest) {
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
