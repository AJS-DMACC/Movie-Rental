package movie.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString@Table(name="movietype")
public class MovieType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="categoryId")
	private long categoryId;
	private String category;
	
	
	public MovieType(String category) {
		super();
		this.category = category;
	}
	
	
}
