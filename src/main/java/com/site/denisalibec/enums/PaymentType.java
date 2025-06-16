package com.site.denisalibec.enums;

//TODO: pe viitor cand implementam metoda de plata
/**
 * Enum care defineste metodele de plata disponibile in aplicatie.
 * Este folosit pentru a seta tipul de plata in cadrul unei comenzi.
 */
public enum PaymentType {
    CARD,               // Plata cu cardul
    CASH_ON_DELIVERY,   // Plata ramburs, la livrare
    BANK_TRANSFER       // Plata prin transfer bancar
}