package com.site.denisalibec.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.NoOpIdentityColumnSupport;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;  // SQLite acceptă coloane cu auto-increment
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new NoOpIdentityColumnSupport();  // Folosim implementarea implicită pentru gestionarea ID-urilor
    }

    @Override
    public boolean supportsLimit() {
        return true;  // SQLite suportă LIMIT
    }

    @Override
    public boolean supportsOffset() {
        return true;  // SQLite suportă OFFSET
    }

    @Override
    public String getAddColumnString() {
        return "ADD COLUMN";  // SQLite folosește această sintaxă pentru a adăuga coloane
    }

    @Override
    public boolean hasDataTypeInColumnDefinition() {
        return false;  // SQLite nu are nevoie de tipuri de date în definiția coloanei
    }

    @Override
    public boolean supportsSequence() {
        return false;  // SQLite nu are suport pentru secvențe
    }

    @Override
    public boolean supportsUnique() {
        return true;  // SQLite suportă constrângerea UNIQUE
    }

    @Override
    public String getTableTypeString() {
        return "";  // SQLite nu folosește un tip specific de tabel
    }

    @Override
    public boolean isReadOnly() {
        return false;  // SQLite nu este read-only
    }
}
