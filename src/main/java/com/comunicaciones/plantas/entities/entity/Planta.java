package com.comunicaciones.plantas.entities.entity;

import com.comunicaciones.plantas.utils.GenericConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = GenericConstant.TAB_NAME_PLANTA, schema = GenericConstant.SCHEMA_NAME)
public class Planta {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;

    private String nombre;

    private String imagen;

    private String especie;

    private String estado;

    private double maxTemperatura;
    private double minHumedadTierra;
    private double minHumedadAmbiente;

    private String descripcion;
}
