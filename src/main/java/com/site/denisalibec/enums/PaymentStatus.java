package com.site.denisalibec.enums;

//TODO: pentru viitor cand aplicam statusul tranzactiilor
/**
 * Enum care defineste statusul platii in cadrul unei comenzi.
 * Este folosit in entitatea Payment pentru a urmari progresul unei plati.

 * NOTA: Functionalitatea de actualizare automata/manuala a statusului NU este inca activa.
 * Va fi folosita intr-o etapa ulterioara pentru a marca platile ca PAID sau FAILED.
 */
public enum PaymentStatus {
    PENDING, // Plata in curs de procesare
    PAID,    // Plata finalizata cu succes
    FAILED   // Plata esuata
}