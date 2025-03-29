package com.formatosprellieminares.api.doc_generator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudCodigosSPARD {
    private String fromAddress;
    private String date;
    private String fromName;
    private String fromIdentification;
    private String projectLocation;
    private String projectAddress;
    private String userName;
    private String userIdentification;
    private String userLocation;
    private String fromName2;
    private String fromIdentification2;
    private String fromPhone;
    private String fromEmail;
}