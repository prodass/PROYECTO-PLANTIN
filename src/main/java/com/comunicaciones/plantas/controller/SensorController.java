package com.comunicaciones.plantas.controller;

import com.comunicaciones.plantas.service.ISensorService;
import com.comunicaciones.plantas.utils.GenericConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(GenericConstant.RESOURCE_GENERIC)
@Slf4j
public class SensorController {
    private final ISensorService sensorService;

    public SensorController(ISensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping(GenericConstant.RESOURCE_PUBLIC + GenericConstant.RESOURCE_SENSORES)
    private ResponseEntity<?> nuevo(@RequestParam("temperatura") double temperatura,
                                    @RequestParam("humedadTierra") double humedadTierra,
                                    @RequestParam("humedadAmbiente") double humedadAmbiente){

        log.warn("Temperatura: " + temperatura + " humedad tierra: " + humedadTierra + " humedad ambiente: " + humedadAmbiente);

        return this.sensorService.procesarDatos(temperatura, humedadTierra, humedadAmbiente);
    }
}
