import java.util.ArrayList;

public class Authentication {

    Database.SqlInterface sqlDatabase;
    public Authentication(Database.SqlInterface database){
        this.sqlDatabase = database;
    }

    //the sql command that will be executed on the database through the database class
    String sql = "";

    //checks if the database contains a user with that username
    public boolean checkUsernameExists(String username) {
        //assumes that the username will not be found
        boolean usernameExists = false;

        //queries the database and returns all usernames stored
        sql = "SELECT UserName FROM User";
        ArrayList<String[]> dbUsernames = sqlDatabase.getFromDatabase(sql);

        //loops through all usernames in the database and tests if they match the entered username
        for (String[] array : dbUsernames) {
            if (username.equals(array[0])) {
                usernameExists = true;
            }
        }

        //returns true if the username is found
        return usernameExists;
    }

    //encrypts a string using a Caesar Cipher
    //https://stackoverflow.com/questions/19108737/java-how-to-implement-a-shift-cipher-caesar-cipher/31601568
    public static String encrypt(String str) {
        //determines the length of the string and therefore the shift
        String s = "";
        int shift = str.length();

        //shifts each character forwards in the string by the length of the string
        for (char c : str.toCharArray()) {
            s += (char) (c + shift);
        }
        return s;
    }

    //decrypts a string using a Caesar Cipher
    public static String decrypt(String str) {
        //determines the length of the string and therefore the shift
        String s = "";
        int shift = str.length();

        //shifts each character backwards in the string by the length of the string
        for (char c : str.toCharArray()) {
            s += (char) (c - shift);
        }
        return s;
    }

    //creates an record in the user table in the database according to user inputted data
    public void createAccount(String username, String password) {
        //checks if the username already exists as the username must be unique
        if (checkUsernameExists(username)) {
            System.out.println("This username already exists, please choose a unique Username");
        }
        else {
            //checks that the user inputted password follows the rules for a password
            if (validPassword(password)) {
                //encrypts the password and creates a record in the database
                password = encrypt(password);
                sql = "INSERT INTO User (UserName, PassHash, WeeklyTarget) VALUES ('" + username + "','" + password + "',14000)";
                sqlDatabase.changeDatabase(sql);
            }
        }
    }

    //checks if the password is valid according to certain conditions
    public boolean validPassword(String passwordAttempt) {
        //the password must pass contain letters and numbers and must be more than 8 digits long
        boolean containsLetters = false;
        boolean containsNumbers = false;
        boolean passwordValid = false;

        //https://www.tutorialspoint.com/Check-if-a-string-contains-a-number-using-Java
        char[] passwordChars = passwordAttempt.toCharArray();

        //checks if there is at least one number and one letter in the password
        for (char c : passwordChars) {
            if (Character.isDigit(c)) {
                containsNumbers = true;
            }
            if (Character.isAlphabetic(c)) {
                containsLetters = true;
            }
        }

        //checks if the password is longer than or equal to 8 letters
        if (passwordAttempt.length() >= 8) {
            if (containsLetters && containsNumbers) {
                passwordValid = true;
            } else {
                System.out.println("The password must contain at least one letter and one number.");
                //Needs at least one letter and one number
            }
        } else {
            System.out.println("The password must be at least 8 characters long.");
            //Too short password
        }

        //returns if the password has met all 3 conditions
        return passwordValid;
    }

    //checks if a user has the correct password for the username and allows them to login if TRUE
    public boolean login(String username, String password) {
        //checks that the username exists in the database already
        if (checkUsernameExists(username)) {

            //retrieves the encrypted password for that user from the database
            sql = "SELECT PassHash FROM User WHERE UserName = '" + username + "'";
            ArrayList<String[]> dbPasswords = sqlDatabase.getFromDatabase(sql);

            String cipherPass = dbPasswords.get(0)[0];
            String plainPass = decrypt(cipherPass);

            //checks if the decrypted password from the database matches the user entered password
            if (plainPass.equals(password)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}