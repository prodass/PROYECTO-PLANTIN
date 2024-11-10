package com.comunicaciones.plantas.repository;

import com.comunicaciones.plantas.entities.entity.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IPlantaRepository extends JpaRepository<Planta, UUID> {

    @Query(value = "SELECT * FROM planta WHERE id_sensor = UUID_TO_BIN('ccfbe661-97d2-11ef-ad9c-0242ac120003')", nativeQuery = true)
    List<Planta> getPlantasSensor();
}
