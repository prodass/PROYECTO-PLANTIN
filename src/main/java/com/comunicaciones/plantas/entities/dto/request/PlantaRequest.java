package com.comunicaciones.plantas.entities.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantaRequest {
    private double temperatura;
    private double humedadTierra;
    private double humedadAmbiente;
}
