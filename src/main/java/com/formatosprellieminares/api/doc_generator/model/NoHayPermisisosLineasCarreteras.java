package com.formatosprellieminares.api.doc_generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoHayPermisisosLineasCarreteras {
    private String fromAddress;
    private String date;
    private String fromName;
    private String fromIdentification;
    private String fromLocation;
    private String fromPhone;
    private String fromEmail;
}