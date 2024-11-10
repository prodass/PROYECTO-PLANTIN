package com.comunicaciones.plantas.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantaDTO {
    private UUID id;
    private String nombre;
    private String especie;
    private String imagen;
    private String estado;
    private String descripcion;

    private double temperatura;
    private double humedadAmbiente;
    private double humedadTierra;

    private double maxTemperatura;
    private double minHumedadAmbiente;
    private double minHumedadTierra;
}
