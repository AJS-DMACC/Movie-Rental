package movie.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rentalID")
	private long rentalID;
	
	@ManyToOne
	@JoinColumn(name="memberID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="movieID")
	private Movie movie;
	
	@Column(name="checkoutDateTime")
	@CreationTimestamp
	private LocalDateTime checkoutDateTime = LocalDateTime.now();
	
	@Column(name="checkinDateTime")
	@CreationTimestamp
	private LocalDateTime checkinDateTime = LocalDateTime.now();
	private int paymentMethod;
	
	

	public Rental(Member member, Movie movie, int paymentMethod) {
		super();
		this.member = member;
		this.movie = movie;
		this.paymentMethod = paymentMethod;
	}
	
	public Rental(Member member, Movie movie, LocalDateTime checkoutDateTime, LocalDateTime checkinDateTime, int paymentMethod) {
		super();
		this.member = member;
		this.movie = movie;
		this.checkoutDateTime = checkoutDateTime;
		this.checkinDateTime = checkinDateTime;
		this.paymentMethod = paymentMethod;
	}
	

	@Override
	public String toString() {
		return "Rental [rentalID = " + rentalID  +   
				", checkoutDate=" + checkoutDateTime + 
				", checkinDate" + checkinDateTime + 
				",paymentMethod=" + paymentMethod + 
				",paymentMethod=" + paymentMethod + 

				"]";
	}







	
}