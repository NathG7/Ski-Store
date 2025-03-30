package ski.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeId;
	private String storeName;
	private String storeAddress;
	private String storeCity;
	private String storeState;
	private String storeZip;
	private String storePhone;

	

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "store_customer", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Customer> customers = new HashSet<>();

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Ski> skis = new HashSet<>();

}
