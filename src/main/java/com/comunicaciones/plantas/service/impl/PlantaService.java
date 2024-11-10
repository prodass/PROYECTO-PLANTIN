package com.comunicaciones.plantas.service.impl;

import com.comunicaciones.plantas.entities.dto.PlantaDTO;
import com.comunicaciones.plantas.entities.dto.PlantaResponseDTO;
import com.comunicaciones.plantas.entities.dto.request.PlantaRequest;
import com.comunicaciones.plantas.entities.entity.Planta;
import com.comunicaciones.plantas.entities.entity.Sensor;
import com.comunicaciones.plantas.repository.IPlantaRepository;
import com.comunicaciones.plantas.repository.ISensorRepository;
import com.comunicaciones.plantas.service.IPlantaService;
import com.comunicaciones.plantas.utils.GenericUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class PlantaService implements IPlantaService {
    private final IPlantaRepository plantaRepository;
    private final GenericUtils genericUtils;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ISensorRepository sensorRepository;

    public PlantaService(IPlantaRepository plantaRepository, GenericUtils genericUtils, RestTemplate restTemplate, ObjectMapper objectMapper, ISensorRepository sensorRepository) {
        this.plantaRepository = plantaRepository;
        this.genericUtils = genericUtils;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.sensorRepository = sensorRepository;
    }

    @Value("${path.file-server.uri}")
    private String PATH_URI;

    @Override
    public ResponseEntity<?> getPlantas() {
        List<Planta> planta = this.plantaRepository.findAll();

        return ResponseEntity.ok(
                this.getPlantaResponse(planta)
        );
    }

    private List<PlantaDTO> getPlantaResponse(List<Planta> plantas) {
        List<PlantaDTO> plantasDTO = new ArrayList<>();

        for (Planta planta : plantas) {
            PlantaDTO plantaDTO = PlantaDTO.builder()
                    .id(planta.getId())
                    .nombre(planta.getNombre())
                    .especie(planta.getEspecie())
                    .imagen(planta.getImagen())
                    .estado(planta.getEstado())
                    .maxTemperatura(planta.getMaxTemperatura())
                    .minHumedadTierra(planta.getMinHumedadTierra())
                    .minHumedadAmbiente(planta.getMinHumedadAmbiente())
                    .descripcion(planta.getDescripcion())
                    .build();

            if (planta.getSensor() != null) {
                plantaDTO.setTemperatura(planta.getSensor().getTemperatura());
                plantaDTO.setHumedadAmbiente(planta.getSensor().getHumedadAmbiente());
                plantaDTO.setHumedadTierra(planta.getSensor().getHumedadTierra());
            }

            plantasDTO.add(plantaDTO);
        }

        return plantasDTO;
    }

    @Override
    public ResponseEntity<?> nuevaPlanta(MultipartFile imagen) {
        if (imagen == null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("error", "La imagen es nula.")
            );
        }

        String url = this.guardarImagen(imagen);


        if (url.equals("null")) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("error", "La imagen no se guardo.")
            );
        }

        PlantaResponseDTO plantaDTO = this.obtenerDatos(imagen);

        if (plantaDTO == null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("error", "No se pudo obtener los datos de la planta.")
            );
        }

        Planta planta = Planta.builder()
                .nombre(plantaDTO.getNombre())
                .imagen(url)
                .sensor(this.getSensor())
                .descripcion(plantaDTO.getDescripcion())
                .especie(plantaDTO.getFamilia())
                .estado("Saludable")
                .build();

        this.plantaRepository.save(planta);

        return ResponseEntity.ok().build();
    }

    private Sensor getSensor() {
        Optional<Sensor> sensorOp = this.sensorRepository.findById(UUID.fromString("ccfbe661-97d2-11ef-ad9c-0242ac120003"));

        if (sensorOp.isPresent()) {
            return sensorOp.get();
        } else {
            return null;
        }
    }

    private PlantaResponseDTO obtenerDatos(MultipartFile imagen) {
        // Construimos el cuerpo de la solicitud
        Map<String, Object> requestBody = new HashMap<>();

        try {
            requestBody.put("images",
                    GenericUtils.convertToBase64(imagen));
        } catch (Exception ex) {
            log.warn("Error:" + ex.getMessage());
        }

        // Construimos los headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", "zA2lQ4NmAVSMk5fUmPJ8qj8KPSG4iShJxhKtcvObbQdKBmvbr9");

        // Creamos la entidad de la solicitud con headers y cuerpo
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Realizamos la llamada POST
        String url = "https://plant.id/api/v3/identification?details=common_names,url,description,taxonomy,rank,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering,propagation_methods&language=es";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        try {
            PlantaResponseDTO plantaDTO = this.getPlantFromApiResponse(response.getBody());

            return plantaDTO;
        } catch (Exception ex) {
            log.warn("Error" + ex.getMessage());
            return null;
        }
    }

    public PlantaResponseDTO getPlantFromApiResponse(String jsonResponse) throws IOException {
        log.warn(jsonResponse);
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        // Navegar por el JSON para obtener el campo "name"
        JsonNode nameNode = rootNode
                .path("result")
                .path("classification")
                .path("suggestions")
                .get(0)
                .path("name");

        String nombre = nameNode.asText();

        JsonNode descripcionNode = rootNode
                .path("result")
                .path("classification")
                .path("suggestions")
                .get(0)
                .path("details")
                .path("description")
                .path("value");

        String descripcion = descripcionNode.asText();

        JsonNode familiaNode = rootNode
                .path("result")
                .path("classification")
                .path("suggestions")
                .get(0)
                .path("details")
                .path("taxonomy")
                .path("family");

        String familia = familiaNode.asText();

        return PlantaResponseDTO.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .familia(familia)
                .build();
    }

    private String guardarImagen(MultipartFile imagen) {
        try {
            Path directory = Paths.get(PATH_URI);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String nombreImagen = this.genericUtils.obtenerNombreImagen(imagen);

            Path filePath = directory.resolve(nombreImagen);

            Files.copy(imagen.getInputStream(), filePath);

            return nombreImagen;
        } catch (Exception e) {
            log.warn("Error al cargar un archivo, {}", imagen.getOriginalFilename());
            return "null";
        }
    }

    @Override
    public ResponseEntity<?> eliminar(UUID idPlanta) {
        Optional<Planta> plantaOp = this.plantaRepository.findById(idPlanta);

        if (plantaOp.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("error", "El id no existe!")
            );
        }

        Planta planta = plantaOp.get();

        this.plantaRepository.delete(planta);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> actualizar(UUID id, PlantaRequest plantaRequest) {
        Optional<Planta> plantaOp = this.plantaRepository.findById(id);

        if (plantaOp.isPresent()) {
            if (plantaRequest != null) {
                Planta planta = plantaOp.get();

                planta.setMaxTemperatura(plantaRequest.getTemperatura());
                planta.setMinHumedadTierra(plantaRequest.getHumedadTierra());
                planta.setMinHumedadAmbiente(plantaRequest.getHumedadAmbiente());

                this.plantaRepository.save(planta);
                log.warn("Se actualizo un registro.");
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
