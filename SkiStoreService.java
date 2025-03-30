package ski.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ski.store.controller.model.StoreData;
import ski.store.controller.model.StoreData.StoreCustomer;
import ski.store.controller.model.StoreData.StoreSki;
import ski.store.dao.CustomerDao;
import ski.store.dao.SkiDao;
import ski.store.dao.StoreDao;
import ski.store.entity.Customer;
import ski.store.entity.Ski;
import ski.store.entity.Store;

@Service
public class SkiStoreService {

	
	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private SkiDao skiDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public StoreData saveStore(StoreData storeData) {
		Long storeId = storeData.getStoreId();
		Store store = findOrCreateStore(storeId);
		
		copyStoreFields(store, storeData);
		return new StoreData(storeDao.save(store));
		
	}
	
	private void copyStoreFields(Store store, StoreData storeData) {
		store.setStoreAddress(storeData.getStoreAddress());
		store.setStoreCity(storeData.getStoreCity());
		store.setStoreId(storeData.getStoreId());
		store.setStoreName(storeData.getStoreName());
		store.setStorePhone(storeData.getStorePhone());
		store.setStoreZip(storeData.getStoreZip());
		store.setStoreState(storeData.getStoreState());
	}


	private Store findOrCreateStore(Long storeId) {
		if(Objects.isNull(storeId)) {
			return new Store();

		}
		else {
			return findStoreById(storeId);
		}
	}
	
	private Store findStoreById(Long storeId) {
		return storeDao.findById(storeId)
				.orElseThrow(() -> new NoSuchElementException(
						"Ski store with ID = " + storeId + " was not found."));
	}
	
	private void copySkiFields(Ski ski, StoreSki storeSki) {
		ski.setSkiManufacturer(storeSki.getSkiManufacturer());
		ski.setSkiModel(storeSki.getSkiModel());
		ski.setSkiLength(storeSki.getSkiLength());
		ski.setSkiWidth(storeSki.getSkiWidth());
		ski.setSkiId(storeSki.getSkiId());
		
	}
	
	private void copyCustomerFields (Customer customer, StoreCustomer storeCustomer) {
		customer.setCustomerEmail(storeCustomer.getCustomerEmail());
		customer.setCustomerFirstName(storeCustomer.getCustomerFirstName());
		customer.setCustomerId(storeCustomer.getCustomerId());
		customer.setCustomerLastName(storeCustomer.getCustomerLastName());
		
	}
	
	
	private Ski findOrCreateSki(Long storeId, Long skiId) {
		if(Objects.isNull(skiId)) {
			return new Ski();
		}
		
		return findSkiById(storeId, skiId);
		
	}
	
	private Customer findOrCreateCustomer(Long storeId, Long customerId) {
		if(Objects.isNull(customerId)) {
			return new Customer();
			
		}
		
		return findCustomerById(storeId, customerId);
	}
	
	private Ski findSkiById(Long storeId, Long skiId) {
		Ski ski = skiDao.findById(skiId)
				.orElseThrow(() -> new NoSuchElementException(
						"Ski with Id = " + skiId + " was not found."));
		if(ski.getStore().getStoreId() != storeId) {
			throw new IllegalArgumentException("The ski with ID = " + skiId
					+ " is not found at the store with ID = " + storeId + ".");
			
		}
		
		return ski;
	}

	private Customer findCustomerById(Long storeId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Customer with ID = " + customerId + " was not found."));
		
		
		boolean found = false;
		
		for(Store petStore : customer.getStores()) {
			if(petStore.getStoreId() == storeId) {
				found = true;
				break;
			}
		}
		
		if(!found) {
			throw new IllegalArgumentException("The customer with ID = " + customerId + " is not a memeber of the ski store with ID = " + storeId);
		}
		
		return customer;
	}
	
	@Transactional(readOnly = false)
	public StoreSki saveSki(Long storeId, StoreSki storeSki) {
		Store store = findStoreById(storeId);
		Long skiId = storeSki.getSkiId();
		Ski ski = findOrCreateSki(storeId, skiId);
		
		copySkiFields(ski, storeSki);
		
		ski.setStore(store);
		store.getSkis().add(ski);
		
		Ski dbSki = skiDao.save(ski);
		
		return new StoreSki(dbSki);
	}
	
	@Transactional
	public StoreCustomer saveCustomer(Long storeId, StoreCustomer storeCustomer) {
		Store store = findStoreById(storeId);
		Long customerId = storeCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(storeId, customerId);
		
		copyCustomerFields(customer, storeCustomer);
		
		customer.getStores().add(store);
		store.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new StoreCustomer(dbCustomer);
	}
	
		@Transactional(readOnly = true)
		public List<StoreData> retrieveAllStores() {
			List<Store> stores = storeDao.findAll();
			
			List<StoreData> result = new LinkedList<>();
			
			for(Store store : stores) {
				StoreData sd = new StoreData(store);
				
//				sd.getCustomers().clear();
//				sd.getSkis().clear();
				
				result.add(sd);
			}
		
		
			return result;
		}
		
		
		
	

	@Transactional(readOnly = true)
	public StoreData retrieveStoreById(Long storeId) {
		return new StoreData(findStoreById(storeId));
	}

	@Transactional(readOnly = false)
	public void deleteStoreById(Long storeId) {
		Store store = findStoreById(storeId);
		storeDao.delete(store);
	}
}
