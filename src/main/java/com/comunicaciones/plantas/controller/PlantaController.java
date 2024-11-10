package com.comunicaciones.plantas.controller;

import com.comunicaciones.plantas.entities.dto.PlantaDTO;
import com.comunicaciones.plantas.entities.dto.request.PlantaRequest;
import com.comunicaciones.plantas.service.IPlantaService;
import com.comunicaciones.plantas.utils.GenericConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(GenericConstant.RESOURCE_GENERIC)
public class PlantaController {
    private final IPlantaService plantaService;

    public PlantaController(IPlantaService plantaService) {
        this.plantaService = plantaService;
    }

    @GetMapping(GenericConstant.RESOURCE_PUBLIC + GenericConstant.RESOURCE_PLANTAS)
    private ResponseEntity<?> getPlantas(){
        return this.plantaService.getPlantas();
    }

    @PostMapping(GenericConstant.RESOURCE_PUBLIC + GenericConstant.RESOURCE_PLANTA)
    private ResponseEntity<?> nuevo(@RequestPart(value = "imagen", required = false) MultipartFile imagen){
        return this.plantaService.nuevaPlanta(imagen);
    }

    @PutMapping(GenericConstant.RESOURCE_PUBLIC + GenericConstant.RESOURCE_PLANTA_ID)
    private ResponseEntity<?> actualizar(@PathVariable("idPlanta") UUID id, @RequestBody PlantaRequest plantaRequest){
        return this.plantaService.actualizar(id, plantaRequest);
    }

    @DeleteMapping(GenericConstant.RESOURCE_PUBLIC + GenericConstant.RESOURCE_PLANTA_ID)
    private ResponseEntity<?> eliminar(@PathVariable("idPlanta") UUID idPlanta){
        return this.plantaService.eliminar(idPlanta);
    }
}
