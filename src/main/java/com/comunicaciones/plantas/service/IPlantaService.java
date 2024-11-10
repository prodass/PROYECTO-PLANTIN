package com.comunicaciones.plantas.service;

import com.comunicaciones.plantas.entities.dto.PlantaDTO;
import com.comunicaciones.plantas.entities.dto.request.PlantaRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IPlantaService {
    ResponseEntity<?> getPlantas();

    ResponseEntity<?> nuevaPlanta(MultipartFile imagen);

    ResponseEntity<?> eliminar(UUID idPlanta);

    ResponseEntity<?> actualizar(UUID id, PlantaRequest plantaRequest);
}
