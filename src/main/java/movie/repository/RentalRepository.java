package movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import movie.beans.Rental;
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

	
	@Query("SELECT r FROM Rental r WHERE memberID = ?1 AND checkinDateTime IS NULL")
    List<Rental> findMemberRentals(long memberID);
 
	
}
