package org.haulmont.simpleClinicDemo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(){
        //jdbc:hsqldb:mem:testdb
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("createTablesScript.sql")
                .addScript("fillTablesScript.sql")
                .build();
    }
}