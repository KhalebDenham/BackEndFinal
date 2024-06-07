package space.marine.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import space.marine.entity.Marine;

public interface MarineDao extends JpaRepository<Marine, Long> {
	Optional<Marine> findByMarineName(String name);
}
