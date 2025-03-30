package ski.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import ski.store.entity.Customer;
import ski.store.entity.Ski;
import ski.store.entity.Store;


@Data
@NoArgsConstructor
public class StoreData {

	
	private Long storeId;
	private String storeName;
	private String storeAddress;
	private String storeCity;
	private String storeState;
	private String storeZip;
	private String storePhone;
	private Set<StoreCustomer> customers = new HashSet<>();
	private Set<StoreSki> skis = new HashSet<>();
	
	public StoreData(Store store) {
		storeId = store.getStoreId();
		storeName = store.getStoreName();
		storeAddress = store.getStoreAddress();
		storeCity = store.getStoreCity();
		storeState = store.getStoreState();
		storeZip = store.getStoreZip();
		storePhone = store.getStorePhone();
		
		for(Customer customer : store.getCustomers()) {
			customers.add(new StoreCustomer(customer));
		}
		
		for (Ski ski : store.getSkis()) {
			skis.add(new StoreSki(ski));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class StoreCustomer{
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public StoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class StoreSki {
		private Long skiId;
		private String skiManufacturer;
		private String skiModel;
		private String skiLength;
		private String skiWidth;
		
		public StoreSki (Ski ski) {
			skiId = ski.getSkiId();
			skiManufacturer = ski.getSkiManufacturer();
			skiModel = ski.getSkiModel();
			skiLength = ski.getSkiLength();
			skiWidth = ski.getSkiWidth();
		}
		
	}
}
