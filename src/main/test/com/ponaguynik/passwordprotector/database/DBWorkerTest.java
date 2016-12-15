package com.ponaguynik.passwordprotector.database;

import com.ponaguynik.passwordprotector.other.Password;
import com.ponaguynik.passwordprotector.scenes.main_scene.DataForm;
import org.junit.*;

import java.sql.ResultSet;

import static org.junit.Assert.*;

public class DBWorkerTest {

    private static String username;
    private static String keyword;

    @BeforeClass
    public static void setUp() throws Exception {
        assertTrue(DBConnector.loadDatabase());
        username = "TestUsername";
        keyword = "TestKeyword";
        //Add a test user to a database
        String query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')", username, keyword);
        DBConnector.execute(query);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        //Delete the test user from the database
        String query = String.format("DELETE FROM users WHERE username = '%s'", username);
        DBConnector.execute(query);
        DBConnector.close();
    }

    @Test
    public void testAddUser() throws Exception {
        //Delete the test user from the database
        String query = String.format("DELETE FROM users WHERE username = '%s'", username);
        DBConnector.execute(query);

        //Add a test user to a database
        DBWorker.addUser(username, keyword);

        //Check whether the user was added to a database
        query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        ResultSet result = DBConnector.executeQuery(query);
        assertEquals(keyword, result.getString("keyword"));
    }

    @Test
    public void testUpdateKeyword() throws Exception {
        String newKeyword = "NewTestKeyword";

        //Update user's keyword
        DBWorker.updateKeyword(username, newKeyword);

        //Check whether the user's keyword has been changed
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        ResultSet result = DBConnector.executeQuery(query);
        assertEquals(newKeyword, result.getString("keyword"));

        DBWorker.updateKeyword(username, keyword);
    }

    @Test
    public void testDeleteUser() throws Exception {
        //Delete the test user
        DBWorker.deleteUser(username);

        //Check whether the user was deleted
        String query = String.format("SELECT keyword FROM users WHERE username = '%s'", username);
        ResultSet result = DBConnector.executeQuery(query);
        assertTrue(result.isClosed());

        //Add a test user to a database
        query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')", username, keyword);
        DBConnector.execute(query);
    }

    @Test
    public void testAddDataForm() throws Exception {
        //Add a new data form to the database
        DBWorker.addDataForm(username);

        //Check whether the data form has been added to the database
        String query = String.format("SELECT * FROM users_data WHERE username = '%s'", username);
        ResultSet result = DBConnector.executeQuery(query);
        assertFalse(result.isClosed());

        //Delete the test data form from the database
        query = String.format("DELETE FROM users_data WHERE username = '%s'", username);
        DBConnector.execute(query);
    }

    @Test
    public void testDeleteDataForm() throws Exception {
        //Add a new data form to the database
        String query = String.format("INSERT INTO users_data (username, title, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", username, "", "", "");
        DBConnector.execute(query);
        query = String.format("SELECT * FROM users_data WHERE username = '%s'", username);
        ResultSet result = DBConnector.executeQuery(query);

        //Delete the test data form from the database
        DBWorker.deleteDataForm(result.getInt("id"));

        //Check whether the data form has been deleted from the database
        query = String.format("SELECT * FROM users_data WHERE username = '%s'", username);
        result = DBConnector.executeQuery(query);
        assertTrue(result.isClosed());
    }

    @Test
    public void testVerifyKeyword() throws Exception {
        //Delete the test user from the database
        String query = String.format("DELETE FROM users WHERE username = '%s'", username);
        DBConnector.execute(query);

        //Add a test user to a database
        query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')",
                username, Password.getSaltedHash(keyword));
        DBConnector.execute(query);

        assertTrue(DBWorker.verifyKeyword(username, keyword));
        assertFalse(DBWorker.verifyKeyword(username, keyword + "wrong"));

        //Delete the test user from the database
        query = String.format("DELETE FROM users WHERE username = '%s'", username);
        DBConnector.execute(query);

        //Add a test user to a database
        query = String.format("INSERT INTO users (username, keyword) VALUES('%s', '%s')", username, keyword);
        DBConnector.execute(query);
    }

    @Test
    public void testUserExists() throws Exception {
        assertTrue(DBWorker.userExists(username));
        assertFalse(DBWorker.userExists(username + "asdfe"));
    }
}