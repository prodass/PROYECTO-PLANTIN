package com.comunicaciones.plantas.service;

import org.springframework.http.ResponseEntity;

public interface ISensorService {
    ResponseEntity<?> procesarDatos(double temperatura, double humedadTierra, double humedadAmbiente);
}
