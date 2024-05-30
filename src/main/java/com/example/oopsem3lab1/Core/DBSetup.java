package com.example.oopsem3lab1.Core;

import com.example.oopsem3lab1.Core.Repository.DBRepository;
import com.example.oopsem3lab1.Core.Repository.Repository;
import lombok.Getter;

import java.sql.SQLException;

public class DBSetup {
    // TODO: Possibly read from json or smth like that
    private static String DB_URL = "jdbc:postgresql://localhost:5432/dev";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "postgres";

    @Getter
    private static DBSetup instance = new DBSetup();

    private Repository repository;

    public Repository getRepository()
    {
        try {
            if (repository.getConnection().isClosed())
            {
                repository.recreateConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return repository;
    }

    private DBSetup() {
        repository = new DBRepository(DB_URL, DB_USER, DB_PASSWORD);
    }
}