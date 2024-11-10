package com.comunicaciones.plantas.repository;

import com.comunicaciones.plantas.entities.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISensorRepository extends JpaRepository<Sensor, UUID> {
}
