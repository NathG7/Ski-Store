package ski.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ski.store.entity.Ski;

public interface SkiDao extends JpaRepository<Ski, Long> {

}
