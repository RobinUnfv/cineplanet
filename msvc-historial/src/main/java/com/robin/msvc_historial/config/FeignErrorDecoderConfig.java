package com.robin.msvc_historial.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.robin.msvc_historial.exception.RemoteServiceException;
import com.robin.msvc_historial.exception.SocioNotFoundException;
import com.robin.msvc_historial.exception.ValidationException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class FeignErrorDecoderConfig {

    @Bean
    public ErrorDecoder feignErrorDecoder(ObjectMapper objectMapper) {
        return new CustomFeignErrorDecoder(objectMapper);
    }

    public static class CustomFeignErrorDecoder implements ErrorDecoder {

        private final ObjectMapper objectMapper;
        private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

        public CustomFeignErrorDecoder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                String body = extractBody(response);
                log.warn("[FeignErrorDecoder] methodKey={} status={} body={}",
                        methodKey, response.status(), body);

                if (body != null && !body.isEmpty()) {
                    JsonNode json = objectMapper.readTree(body);
                    String codigo = json.path("codigo").asText(null);
                    String mensaje = json.path("mensaje").asText("Error en servicio remoto");

                    // Mapeo según el código de error del servicio remoto
                    if (codigo != null) {
                        switch (codigo) {
                            case "SOCIO_NOT_FOUND":
                                return new SocioNotFoundException(mensaje);
                            case "VALIDATION_ERROR":
                                Map<String, String> detalles = extractDetalles(json);
                                return new ValidationException(mensaje, detalles);
                            default:
                                return new RemoteServiceException(mensaje, response.status(), codigo);
                        }
                    }
                }

                // Si no se pudo parsear, usar el decoder por defecto
                return defaultDecoder.decode(methodKey, response);

            } catch (IOException e) {
                log.error("[FeignErrorDecoder] Error parseando respuesta de Feign", e);
                return new RemoteServiceException(
                        "Error de comunicación con servicio remoto",
                        response.status(),
                        "FEIGN_ERROR");
            }
        }

        private String extractBody(Response response) throws IOException {
            if (response.body() == null) {
                return null;
            }
            try (InputStream is = response.body().asInputStream()) {
                return new String(is.readAllBytes());
            }
        }

        private Map<String, String> extractDetalles(JsonNode json) {
            Map<String, String> detalles = new HashMap<>();
            JsonNode detallesNode = json.path("detalles");
            if (detallesNode.isObject()) {
                detallesNode.fields().forEachRemaining(entry ->
                        detalles.put(entry.getKey(), entry.getValue().asText()));
            }
            return detalles;
        }
    }

}
