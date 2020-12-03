package movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import movie.beans.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{	
	List<Employee> findByUserName(String userName);
}

