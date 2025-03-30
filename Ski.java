package ski.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Ski {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long skiId;
	
	private String skiManufacturer;
	private String skiModel;
	private String skiLength;
	private String skiWidth;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;
}
