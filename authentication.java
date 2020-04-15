import java.util.ArrayList;

public class authentication{
    public static void main(String[] args){
        Database sqlDatabase = new Database("CSED Database.db");
        String g = encrypt("football457", 11);
        System.out.println(decrypt(g, 11));
    }

    String username;
    String password;
    
    String sql = "";
    Database sqlDatabase;

    public boolean checkUsernameExists(String username){
        Boolean usernameExists = false;
        
        sql = "SELECT UserName FROM User";
        ArrayList<String[]> dbUsernames = sqlDatabase.getFromDatabase(sql);
                
        for(String[] array: dbUsernames){
            if(username.equals(array[0])){
                usernameExists = true; 
            }
        }
        
        return usernameExists; //is false unless first if statement is true
    }
    
    //https://stackoverflow.com/questions/19108737/java-how-to-implement-a-shift-cipher-caesar-cipher/31601568
    public static String encrypt(String str, int shift){
        String s = "";
        int len = str.length();
        for(int i = 0; i < len; i++){
            s += (char)(str.charAt(i) + shift);
        }
        return s;
    }

    public static String decrypt(String str, int shift){
       String s = "";
       int len = str.length();
       for(int i = 0; i < len; i++){
            s += (char)(str.charAt(i) - shift);
       }
       return s;
    }


    public void createAccount(){
        if (checkUsernameExists(username)){
            System.out.println("This username already exists, please choose a unique Username");
            //output this username already exists (on a label)
        }
        else{
            if (validPassword(password)){
                //account created in database
            }
        }        
    }
    
    public boolean validPassword(String passwordAttempt){
        Boolean containsLetters = false;
        Boolean containsNumbers = false;
        Boolean passwordValid = false;

        //https://www.tutorialspoint.com/Check-if-a-string-contains-a-number-using-Java
        char[] passwordChars = passwordAttempt.toCharArray();

        for (char c : passwordChars){
            if (Character.isDigit(c)){
                containsNumbers = true;
            }
            if (Character.isAlphabetic(c)){
                containsLetters = true;
            }
        }

        if (passwordAttempt.length() >= 8){
            if(containsLetters && containsNumbers){
                passwordValid = true;
            }
            else{
                System.out.println("The password must contain at least one letter and one number.");
                //Needs at least one letter and one number
            }            
        }
        else{
           System.out.println("The password must be at least 8 characters long.");
            //Too short password
        }
        return passwordValid;
    }
 
    public void login(String username, String password){
        if (checkUsernameExists(username)){
            sql = "SELECT PassHash FROM User WHERE UserName = '" + username + "'";
            ArrayList<String[]> dbPasswords = sqlDatabase.getFromDatabase(sql);
            
            String cipherPass = dbPasswords.get(0)[0];
            String plainPass = decrypt(cipherPass, cipherPass.length());

            if (plainPass.equals(password)){
                System.out.println("Password is correct.");
                //login works
            }
            else{
                System.out.println("Password is incorrect.");
                //login fails
            }            
        }
    }
}
