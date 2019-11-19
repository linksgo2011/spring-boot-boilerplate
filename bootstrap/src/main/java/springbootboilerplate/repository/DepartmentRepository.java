package springbootboilerplate.repository;

import cn.printf.springbootboilerplate.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DepartmentRepository extends JpaRepository<Department, Long>,
        JpaSpecificationExecutor<Department> {
}
