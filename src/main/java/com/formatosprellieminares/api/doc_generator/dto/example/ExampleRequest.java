package com.formatosprellieminares.api.doc_generator.dto.example;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ejemplos de solicitudes para diferentes tipos de documentos")
public class ExampleRequest {

    @Schema(description = "Ejemplo de solicitud de códigos SPARD", example = "{\"fromAddress\":\"Calle 123 #45-67\",\"date\":\"2024-03-29\",\"fromName\":\"Juan Pérez\",\"fromIdentification\":\"123456789\",\"fromPhone\":\"3001234567\"}")
    public static final String SOLICITUD_CODIGOS_SPARD = """
            {
                "datos": {
                    "fromAddress": "Calle 123 #45-67",
                    "date": "2024-03-29",
                    "fromName": "Juan Pérez",
                    "fromIdentification": "123456789",
                    "projectLocation": "Barrio El Centro",
                    "projectAddress": "Neiva",
                    "userName": "María González",
                    "userIdentification": "987654321",
                    "userLocation": "Neiva, Huila",
                    "fromName2": "Juan Pérez",
                    "fromIdentification2": "123456789",
                    "fromPhone": "3001234567",
                    "fromEmail": "juan.perez@email.com"
                }
            }
            """;

    @Schema(description = "Ejemplo de solicitud de autorización de trámites", example = "{\"fromAddress\":\"Calle 123 #45-67\",\"date\":\"2024-03-29\",\"fromName\":\"Juan Pérez\",\"fromIdentification\":\"123456789\",\"fromPhone\":\"3001234567\"}")
    public static final String AUTORIZACION_TRAMITES = """
            {
                "datos": {
                    "fromAddress": "Calle 123 #45-67",
                    "date": "2024-03-29",
                    "fromName": "Juan Pérez",
                    "fromIdentification": "123456789",
                    "projectLocation": "Barrio El Centro",
                    "projectAddress": "Neiva",
                    "fromName2": "Juan Pérez",
                    "fromIdentification2": "123456789",
                    "fromPhone": "3001234567",
                    "fromEmail": "juan.perez@email.com"
                }
            }
            """;

    @Schema(description = "Ejemplo de solicitud de autorización de conexión", example = "{\"fromAddress\":\"Calle 123 #45-67\",\"date\":\"2024-03-29\",\"fromName\":\"Juan Pérez\",\"fromIdentification\":\"123456789\",\"fromPhone\":\"3001234567\"}")
    public static final String AUTORIZACION_CONEXION = """
            {
                "datos": {
                    "fromAddress": "Calle 123 #45-67",
                    "date": "2024-03-29",
                    "fromName": "Juan Pérez",
                    "fromIdentification": "123456789",
                    "projectLocation": "Barrio El Centro",
                    "projectAddress": "Neiva",
                    "fromName2": "Juan Pérez",
                    "fromIdentification2": "123456789",
                    "fromPhone": "3001234567",
                    "fromEmail": "juan.perez@email.com"
                }
            }
            """;

    @Schema(description = "Ejemplo de solicitud de no hay permiso líneas carretera", example = "{\"fromAddress\":\"Calle 123 #45-67\",\"date\":\"2024-03-29\",\"fromName\":\"Juan Pérez\",\"fromIdentification\":\"123456789\",\"fromPhone\":\"3001234567\"}")
    public static final String NO_HAY_PERMISO_LINEAS_CARRETERA = """
            {
                "datos": {
                    "fromAddress": "Calle 123 #45-67",
                    "date": "2024-03-29",
                    "fromName": "Juan Pérez",
                    "fromIdentification": "123456789",
                    "fromPhone": "3001234567"
                }
            }
            """;

    @Schema(description = "Ejemplo de solicitud múltiple de documentos", example = "{\"documentos\":[{\"tipoDocumento\":\"SOLICITUD_CODIGOS_SPARD\",\"datos\":{\"fromAddress\":\"Calle 123 #45-67\",\"date\":\"2024-03-29\",\"fromName\":\"Juan Pérez\",\"fromIdentification\":\"123456789\",\"fromPhone\":\"3001234567\"}},{\"tipoDocumento\":\"AUTORIZACION_TRAMITES\",\"datos\":{\"fromAddress\":\"Calle 123 #45-67\",\"date\":\"2024-03-29\",\"fromName\":\"Juan Pérez\",\"fromIdentification\":\"123456789\",\"fromPhone\":\"3001234567\"}}]}")
    public static final String MULTIPLE_DOCUMENTOS = """
            {
                "documentos": [
                    {
                        "tipoDocumento": "SOLICITUD_CODIGOS_SPARD",
                        "datos": {
                            "fromAddress": "Calle 123 #45-67",
                            "date": "2024-03-29",
                            "fromName": "Juan Pérez",
                            "fromIdentification": "123456789",
                            "projectLocation": "Barrio El Centro",
                            "projectAddress": "Neiva",
                            "userName": "María González",
                            "userIdentification": "987654321",
                            "userLocation": "Neiva, Huila",
                            "fromName2": "Juan Pérez",
                            "fromIdentification2": "123456789",
                            "fromPhone": "3001234567",
                            "fromEmail": "juan.perez@email.com"
                        }
                    }
                ]
            }
            """;
}