package com.ponaguynik.passwordprotector.database;

import com.ponaguynik.passwordprotector.model.User;
import com.ponaguynik.passwordprotector.util.Password;
import org.junit.*;

import static org.junit.Assert.*;

public class DBWorkerTest {

    private User user = new User("userForTest", Password.getSaltedHash("test"));

    @Before
    public void setUp() throws Exception {
        DBConnector.loadDatabase();
        DBWorker.addUser(user);
    }

    @After
    public void tearDown() throws Exception {
        DBWorker.deleteUser(user);
        DBConnector.close();
    }

    @Test
    public void updateKeyword() throws Exception {
        user.setKeyword(Password.getSaltedHash("updated"));
        DBWorker.updateKeyword(user);
        user.setKeyword("updated");
        assertTrue(DBWorker.verifyKeyword(user));
        user.setKeyword(Password.getSaltedHash("test"));
        DBWorker.updateKeyword(user);
    }

    @Test
    public void verifyKeyword() throws Exception {
        user.setKeyword("test");
        assertTrue(DBWorker.verifyKeyword(user));
        user.setKeyword("wrong");
        assertFalse(DBWorker.verifyKeyword(user));
        user.setKeyword(Password.getSaltedHash("test"));
    }

    @Test
    public void userExists() throws Exception {
        assertTrue(DBWorker.userExists(user));
        DBWorker.deleteUser(user);
        assertFalse(DBWorker.userExists(user));
        DBWorker.addUser(user);
    }

}