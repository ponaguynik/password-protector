package com.ponaguynik.passwordprotector.database;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class DBConnectorTest {

    @Test
    public void loadDatabase() throws Exception {
        DBConnector.loadDatabase();
        assertTrue(Files.exists(Paths.get("./database.db")));
        DBConnector.close();
    }
}