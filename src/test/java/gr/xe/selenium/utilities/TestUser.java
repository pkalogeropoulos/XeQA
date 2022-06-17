package gr.xe.selenium.utilities;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This is an object representation of our test user. We want to hide all the details
 * about how we retrieve the user's data and where they are stored
 * @author pkalogerop
 */
public class TestUser {

    private String username;
    private String password;

    public TestUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Creates a new <code>TestUser</code>
     *
     * @return a properly defined <code>TestUser</code> object
     */
    public static TestUser createTestUser() throws Exception {
        //At this point the test user's username and password are stored in
        //a file locally. This method retrieves the data from that file,
        //but even if the implementation changes (for instance we can store the 
        //test user's data in a database), clients of this method should not be
        //aware of these changes.

        TestUser testUser;

        //Get the data from the file
        String path = System.getProperty("user.dir");
        String userDataPath = path + "/src/main/config/credentials.csv";
        BufferedReader br = new BufferedReader(new FileReader(userDataPath));

        //Ommit the first line, it contains headers
        br.readLine();

        //Get the second row that contains the test user's data
        String[] data = br.readLine().split(",");

        //The username in our file is the first row and the password the second row.
        testUser = new TestUser(data[0], data[1]);

        return testUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
