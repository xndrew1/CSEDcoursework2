package Database;

import java.sql.*;
import java.util.ArrayList;


/**
 * DatabaseElements.Database Class
 * Manages connections to database and SQL queries
 */
public class SqlInterface {

    //Stores the connection to the database
    private Connection dbConnection;
    
    //Constant which defines an integer in SQL
    public static final int integerId = 4;

    //Constant which defines text in SQL
    public static final int textId = 12;
    
    //Constant which defines an real number in SQL
    public static final int realId = 7;

    /**
     * Constructor for the database class, establishes a connection to the database
     * @param filePath filePath of the database
     */
    public SqlInterface(String filePath)
    {
        //Defines dbConnection
        dbConnection = null;
        try {
            //Attempts to find the proper driver
            Class.forName("org.sqlite.JDBC");
            
            //Establishes the connection to the database using the driver
            dbConnection = DriverManager.getConnection("jdbc:sqlite:"+filePath);
            
            //Informs the user that the database was properly connected to
            System.out.println("Opened database successfully");
        } catch ( Exception e ) {
            //Print the proper error message if any of the above statements failed
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
    }

    /***
     * Handles select operations, operations which return data, but don't change the database
     * @param query SQL Query input
     * @return Data output, (This is casted to string)
     */
    public ArrayList<String[]> getFromDatabase(String query)
    {
        //Initialise the array list which data will be stored in
        ArrayList<String[]> records = new ArrayList<String[]>();
        
        //Initialise the class that will recieve the results from the database
        ResultSet results = null;
        
        //ResulSets close upon being returned to outside of their scope, so the results are casted to strings
        
        try {
            //Make an SQL query to the database an place the results in a ResultSet
            Statement sqlStatement = dbConnection.createStatement();
            results = sqlStatement.executeQuery(query);
            
            //Get the metadata of the result set
            ResultSetMetaData resultsMetadata = results.getMetaData();

            //Define a string
            String currentValue;
            
            //Iterate through all the records in the ResultSet
            while (results.next()) {
                //Add a string array onto the end of the records arraylist
                records.add(new String[resultsMetadata.getColumnCount()]);
                
                //Iterates through all the columns in the ResultSet
                for (int index = 1; index <= resultsMetadata.getColumnCount(); index++) {
                    
                    //Get the name of the column
                    String columnName = resultsMetadata.getColumnName(index);
                    
                    //Get the data type stored in the column
                    int columnType = resultsMetadata.getColumnType(index);

                    //Compare columnType to the data type constants
                    switch (columnType)
                    {
                        case (integerId):
                            //Cast integer to string
                            currentValue = String.format("%d",results.getInt(columnName));
                            break;
                        case (textId):
                            //Store the string
                            currentValue = results.getString(columnName);
                            break;
                        case (realId):
                            //Cast Real number to string
                            currentValue = String.format("%f",results.getFloat(columnName));
                            break;
                        default:
                            //Set currentValue to blank
                            currentValue = "";
                    }
                    //Add the value to the array
                    records.get(records.size()-1)[index-1] = currentValue;

                }
            }
            //Close the statement
            sqlStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Return the records arrraylist
        return records;
    }

    /***
     * Handles insert, update, etc. operations, operations that change the database, but don't return data
     * @param instruction SQL Instruction
     */
    public void changeDatabase(String instruction)
    {
        try {

            //Make an SQL Query to the database
            Statement sqlStatement = dbConnection.createStatement();

            //Update the database based on the instruction
            sqlStatement.executeUpdate(instruction);

            //Close the query
            sqlStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* public static void main( String args[] ) {
        SqlInterface currentSqlInterface = new SqlInterface("CSED Database.db");
    }*/
}
