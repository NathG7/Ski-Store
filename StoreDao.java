package ski.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ski.store.entity.Store;

public interface StoreDao extends JpaRepository<Store, Long> {

}

