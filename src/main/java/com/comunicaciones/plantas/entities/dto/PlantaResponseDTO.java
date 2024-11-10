package com.comunicaciones.plantas.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantaResponseDTO {
    private String nombre;
    private String descripcion;
    private String familia;
}
