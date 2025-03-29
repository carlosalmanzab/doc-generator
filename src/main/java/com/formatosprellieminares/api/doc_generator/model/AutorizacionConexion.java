package com.formatosprellieminares.api.doc_generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionConexion {
    private String fromAddress;
    private String date;
    private String fromName;
    private String fromIdentification;
    private String fromLocation;
    private String toName;
    private String toIdentification;
    private String toLocation;
    private String fromName2;
    private String fromIdentification2;
    private String fromPhone;
    private String structureCode;
    private String structureLocation;
    private String structureType;
    private String structureHeight;
    private String structureMaterial;
    private String structureCondition;
    private String structureOwner;
    private String structureOwnerId;
    private String structureOwnerAddress;
    private String structureOwnerPhone;
    private String structureOwnerEmail;
    private String structureOwnerSignature;
    private String structureOwnerDate;
    private String structureOwnerWitness;
    private String structureOwnerWitnessId;
    private String structureOwnerWitnessSignature;
    private String structureOwnerWitnessDate;
}