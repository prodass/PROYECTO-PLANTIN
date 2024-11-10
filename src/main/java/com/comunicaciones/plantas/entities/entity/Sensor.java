package com.comunicaciones.plantas.entities.entity;

import com.comunicaciones.plantas.utils.GenericConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = GenericConstant.TAB_NAME_SENSOR, schema = GenericConstant.SCHEMA_NAME)
public class Sensor {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    private UUID id;

    private double temperatura;
    private double humedadAmbiente;
    private double humedadTierra;

    private LocalDateTime fechaCreacion;
}
