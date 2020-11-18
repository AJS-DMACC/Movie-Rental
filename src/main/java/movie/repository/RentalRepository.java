package movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import movie.beans.Rental;
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
