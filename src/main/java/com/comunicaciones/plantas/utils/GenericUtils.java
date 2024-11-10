package com.comunicaciones.plantas.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

@Slf4j
@Configuration
public class GenericUtils {
    public String obtenerExtension(String nombreArchivo) {
        int lastIndex = nombreArchivo.lastIndexOf(".");
        if (lastIndex != -1) {
            return nombreArchivo.substring(lastIndex).toLowerCase(); // Convertir a minúsculas
        }
        return null; // O puedes devolver una cadena vacía
    }

    public String obtenerNombreSinExtension(String nombreArchivo) {
        int lastIndex = nombreArchivo.lastIndexOf(".");
        if (lastIndex != -1) {
            return nombreArchivo.substring(0, lastIndex);
        }
        return nombreArchivo;
    }

    public static LocalDateTime obtenerFechaYHoraActual() {
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        return currentDateTime.toLocalDateTime();
    }

    public String obtenerNombreImagen(MultipartFile imagen) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestampNumerico = ahora.format(formatter);

        return this.obtenerNombreSinExtension(imagen.getOriginalFilename().toLowerCase(Locale.ROOT).replace(' ', '_'))
                + "_" + timestampNumerico
                + this.obtenerExtension(imagen.getOriginalFilename());
    }

    public static String convertToBase64(MultipartFile file) throws IOException {
        // Obtener los bytes del archivo
        byte[] fileBytes = file.getBytes();

        // Codificar los bytes en Base64
        String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);

        return base64Encoded;
    }
}
