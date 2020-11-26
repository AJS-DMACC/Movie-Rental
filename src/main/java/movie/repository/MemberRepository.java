package movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import movie.beans.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	List<Member> findByFirstNameAndLastName(String firstName, String LastName);
}
