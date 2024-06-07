package space.marine.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import space.marine.entity.Ship;

public interface ShipDao extends JpaRepository<Ship, Long> {

}
