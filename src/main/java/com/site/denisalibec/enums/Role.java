package com.site.denisalibec.enums;

//TODO: de implementat cand creezi useri sa le dai un role
/**
 * Enum care defineste rolurile disponibile in aplicatie.
 * Acestea sunt folosite pentru autorizare si acces la functionalitati.
 */
public enum Role {
    CLIENT, // Rol implicit pentru utilizatori obisnuiti
    ADMIN   // Rol cu drepturi administrative (ex: modificare stoc, vizualizare plati etc.)
}