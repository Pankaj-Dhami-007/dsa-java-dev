package com.dhami.hibernatedeepdrive.hibernate.examples.bootstrap;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourceRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public DataSourceRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        System.out.println("DataSource : " + dataSource.getClass().getName());
    }
}