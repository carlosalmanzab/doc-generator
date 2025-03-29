package com.formatosprellieminares.api.doc_generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionTramites {
    private String fromAddress;
    private String date;
    private String fromName;
    private String fromIdentification;
    private String fromLocation;
    private String toName;
    private String toIdentification;
    private String projectName;
    private String projectLocation;
    private String projectAddress;
    private String fromName2;
    private String fromIdentification2;
    private String fromPhone;
    private String fromEmail;
}