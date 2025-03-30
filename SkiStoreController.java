package ski.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ski.store.controller.model.StoreData;
import ski.store.controller.model.StoreData.StoreCustomer;
import ski.store.controller.model.StoreData.StoreSki;
import ski.store.service.SkiStoreService;

@RestController
@RequestMapping ("/ski_store")
@Slf4j
public class SkiStoreController {

	
	@Autowired
	private SkiStoreService skiStoreService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreData insertStore (@RequestBody StoreData storeData) {
		log.info("Creating ski store {}", storeData);
		return skiStoreService.saveStore(storeData);
		
	}
	
	@PutMapping ("/{storeId}")
	public StoreData updateStore (@PathVariable Long storeId,
			@RequestBody StoreData storeData) {
		storeData.setStoreId(storeId);
		log.info("Updating ski store {}", storeData);
		return skiStoreService.saveStore(storeData);
		
	}
	
	@PostMapping("/{storeId}/ski")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreSki addSkiToStore(@PathVariable Long storeId,
			@RequestBody StoreSki storeSki) {
		log.info("Adding ski{} to store with ID = {}", storeSki, storeId);
		
		return skiStoreService.saveSki(storeId, storeSki);
	}

	
	@PostMapping("/{storeId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StoreCustomer addCustomerToStore(@PathVariable Long storeId,
			@RequestBody StoreCustomer storeCustomer) {
		log.info("Adding customer {} to ski store with ID = ", storeCustomer, storeId);
		
		return skiStoreService.saveCustomer(storeId, storeCustomer);
	}
	
	@GetMapping
	public List<StoreData> retrieveAllStore(){
		log.info("Retrieving all ski stores");
		return skiStoreService.retrieveAllStores();
		
	}
	
	@GetMapping("/{storeId}")
	public StoreData retrieveStoreById(@PathVariable Long storeId){
		log.info("Retrieving ski store with ID = ", storeId);
		return skiStoreService.retrieveStoreById(storeId);
		
	}
	
	@DeleteMapping("/{storeId}")
	public Map<String, String> deleteStoreById(@PathVariable Long storeId){
	log.info("Deleting ski store with ID = ", storeId);
	
	skiStoreService.deleteStoreById(storeId);
	
	return Map.of("message", "Ski store with ID = " + storeId + " deleted.");
	
	}
}

