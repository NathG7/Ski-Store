package ski.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ski.store.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
