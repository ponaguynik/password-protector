package com.ponaguynik.passwordprotector.database;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;

public class DBConnectorTest {

    @BeforeClass
    public static void setUp() {
        assertTrue(DBConnector.loadDatabase());
    }

    @AfterClass
    public static void tearDown() {
        DBConnector.close();
    }

    @Test
    public void testExecute() throws Exception {
        DBConnector.execute("INSERT INTO users (username, keyword) VALUES('TestUsername', 'TestKeyword')");
        DBConnector.execute("DELETE FROM users WHERE username = 'TestUsername'");
    }

    @Test
    public void testExecuteQuery() throws Exception {
        DBConnector.execute("INSERT INTO users (username, keyword) VALUES('TestUsername', 'TestKeyword')");
        ResultSet result = DBConnector.executeQuery("SELECT keyword FROM users WHERE username = 'TestUsername'");
        DBConnector.execute("DELETE FROM users WHERE username = 'TestUsername'");

        assertNotNull(result);
    }
}