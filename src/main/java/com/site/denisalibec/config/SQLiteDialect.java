package com.site.denisalibec.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.dialect.identity.NoOpIdentityColumnSupport;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
        // Înregistrarea tipurilor de coloane
        registerColumnType(1, "integer");
        registerColumnType(2, "real");
        registerColumnType(3, "text");
        registerColumnType(4, "blob");
    }

    @Override
    public String getTableTypeString() {
        return " engine=InnoDB"; // Poți schimba acest lucru dacă ai nevoie de o altă metodă de stocare
    }

    // Implementarea pentru obținerea cheii primare
    @Override
    public GetGeneratedKeysDelegate getGeneratedKeysDelegate(JdbcEnvironment jdbcEnvironment) {
        return new GetGeneratedKeysDelegate() {
            @Override
            public String getSelectString() {
                return "SELECT last_insert_rowid()"; // SQLite folosește last_insert_rowid() pentru a obține ID-ul generat
            }
        };
    }

    // SQLite nu are suport pentru auto-increment direct, deci nevoia de a folosi NoOpIdentityColumnSupport
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public boolean hasDataTypeInColumnDefinition() {
        return false;
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public boolean supportsOffset() {
        return true;
    }

    @Override
    public boolean supportsSequence() {
        return false;
    }

    @Override
    public boolean supportsUnique() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public String getAddColumnString() {
        return "add column";  // SQLite folosește această sintaxă pentru a adăuga coloane
    }

    // Aceasta este metoda care va returna tipul de susținere al coloanei de identitate
    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new NoOpIdentityColumnSupport(); // SQLite folosește o metodă specială de generare a ID-urilor
    }
}
