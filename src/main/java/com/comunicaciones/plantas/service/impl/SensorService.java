package com.comunicaciones.plantas.service.impl;

import com.comunicaciones.plantas.entities.entity.Planta;
import com.comunicaciones.plantas.entities.entity.Sensor;
import com.comunicaciones.plantas.repository.IPlantaRepository;
import com.comunicaciones.plantas.repository.ISensorRepository;
import com.comunicaciones.plantas.service.ISensorService;
import com.comunicaciones.plantas.utils.GenericUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SensorService implements ISensorService {
    private final ISensorRepository sensorRepository;
    private final IPlantaRepository plantaRepository;

    public SensorService(ISensorRepository sensorRepository, IPlantaRepository plantaRepository) {
        this.sensorRepository = sensorRepository;
        this.plantaRepository = plantaRepository;
    }

    @Override
    public ResponseEntity<?> procesarDatos(double temperatura, double humedadTierra, double humedadAmbiente) {
        Optional<Sensor> sensorOp = this.sensorRepository.findById(UUID.fromString("ccfbe661-97d2-11ef-ad9c-0242ac120003"));

        if (sensorOp.isPresent()) {
            log.warn("Se guardo un nuevo registro.");

            Sensor sensor = sensorOp.get();
            sensor.setTemperatura(temperatura);
            sensor.setHumedadAmbiente(humedadAmbiente);
            sensor.setHumedadTierra(humedadTierra);

            sensor.setFechaCreacion(GenericUtils.obtenerFechaYHoraActual());

            log.warn(sensor.toString());

            this.sensorRepository.save(sensor);

            if (this.activarRiego(temperatura, humedadTierra, humedadAmbiente)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }

        }

        return ResponseEntity.badRequest().build();
    }

    private boolean activarRiego(double temperatura, double humedadTierra, double humedadAmbiente) {
        List<Planta> plantas = this.plantaRepository.getPlantasSensor();
        boolean encender = false;

        if (!plantas.isEmpty()) {
            for (Planta planta : plantas) {
                if (temperatura >= planta.getMaxTemperatura() && humedadTierra <= planta.getMinHumedadTierra() && humedadAmbiente <= planta.getMinHumedadAmbiente()) {
                    planta.setEstado("Pendiente de riego.");
                    encender = true;
                    log.warn("La planta " + planta.getNombre() + " esta pendiente de riego!");
                } else {
                    planta.setEstado("Saludable");
                }
                this.plantaRepository.save(planta);
            }
        }

        return encender;
    }
}
