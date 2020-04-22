import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MainUserInteraction extends Application {
    int userID = 0;
    private ArrayList<String> choices = new ArrayList<>();
    ArrayList<Integer> caloriesForChoices = new ArrayList<>();
    ArrayList<String> datesEaten = new ArrayList<>();
    ArrayList<Integer> caloriesForRecordMeals = new ArrayList<>();
    ArrayList<String> ingredientsFromDB = new ArrayList<>();
    ArrayList<Double> ingredientsCalFromDB = new ArrayList<>();
    ArrayList<String> removeRecords = new ArrayList<>();
    int targetCaloriesForWeek = 14000; //Total calorie target for the week
    int targetCaloriesForDay = targetCaloriesForWeek / 7;
    public void updateArrays(){
        String sql;
        caloriesForChoices.clear();
        datesEaten.clear();
        caloriesForRecordMeals.clear();
        ingredientsFromDB.clear();
        ingredientsCalFromDB.clear();
        removeRecords.clear();
        choices.clear();

        sql = "SELECT MealName, CalorieCount FROM Meal";
        for (String[] result : sqlDatabase.getFromDatabase(sql)){
            //System.out.println(result[0]);
            String choice = result[0];
            choices.add(choice);
            caloriesForChoices.add(Integer.parseInt(result[1]));
        }

        sql = "SELECT WeeklyTarget FROM User WHERE UserID = " + userID ;

        try {
            targetCaloriesForWeek = Integer.parseInt((sqlDatabase.getFromDatabase(sql).get(0)[0]));
        } catch (Exception e){
            targetCaloriesForWeek = 14000;
        } finally {
            targetCaloriesForDay = targetCaloriesForWeek / 7;
        }

        sql = "SELECT Date, CalorieCount FROM MealRecord, Meal WHERE MealRecord.MealID = Meal.MealID AND MealRecord.UserID = " + userID;
        for (String[] results :sqlDatabase.getFromDatabase(sql)){
            datesEaten.add(results[0]);
            caloriesForRecordMeals.add(Integer.parseInt(results[1]));
        }

        sql = "SELECT IngredientName, CaloriesPerGram FROM Ingredients";
        for (String[] results:sqlDatabase.getFromDatabase(sql)){
            ingredientsFromDB.add(results[0]);
            ingredientsCalFromDB.add(Double.parseDouble(results[1]));
        }

        sql = "SELECT MealName FROM Meal, MealRecord WHERE Meal.MealID = MealRecord.MealID AND  MealRecord.UserID = " + userID;
        for (String[] results:sqlDatabase.getFromDatabase(sql)){
            removeRecords.add(results[0]);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void start(Stage primaryStage) {
        //IMPORT THESE FROM THE DATABASE (These also need to be re-imported when the pages change)

        updateArrays();
        //Set the title to log in
        primaryStage.setTitle("Log in");
        ArrayList fullIngredientList = new ArrayList();
        ArrayList fullCaloriesList = new ArrayList();

        //Log in page
        Label usernameLabel = new Label("Enter Username: ");
        usernameLabel.setWrapText(true);
        TextField usernameTF = new TextField();
        Label passwordLabel = new Label("Enter Password ");
        passwordLabel.setWrapText(true);
        TextField passwordTF = new TextField();
        Button button0 = new Button("Submit");
        Button createAccountButton = new Button("Create Account");
        Label loginInfoLabel = new Label();

        //Create account page
        Label createUsername = new Label("Enter Username: ");
        createUsername.setWrapText(true);
        TextField createUsernameTf = new TextField();
        Label createPassword = new Label("Enter Password ");
        createPassword.setWrapText(true);
        TextField createPasswordTf = new TextField();
        Label confirmPassword = new Label("Confirm Password ");
        confirmPassword.setWrapText(true);
        TextField confirmPasswordTf = new TextField();
        Button finaliseCreate = new Button("Submit");

        //menu page
        Button button1 = new Button("Add a new meal");
        button1.setWrapText(true);
        Button button2 = new Button("Add a meal you have had before");
        button2.setWrapText(true);
        Button button3 = new Button("Remove a meal");
        button3.setWrapText(true);
        Button button4 = new Button("View graphs and targets");
        button4.setWrapText(true);
        Button exitButton = new Button("Exit");
        Button settingsButton = new Button("settings");
        Button logOut = new Button("Log Out");

        //add meal page
        Button button5 = new Button("go back");
        button5.setWrapText(true);
        Label nameLabel = new Label("Name of food: ");
        nameLabel.setWrapText(true);
        TextField nameOfMealTF = new TextField();
        Label calorieLabel = new Label("Number of calories: ");
        calorieLabel.setWrapText(true);
        TextField mealCaloriesTF = new TextField();
        Button button8 = new Button("Submit");
        button5.setWrapText(true);
        Label descriptionLabel = new Label("Description of food item:");
        descriptionLabel.setWrapText(true);
        TextField descriptionOfFoodTF = new TextField();
        Button altIngredientsButton = new Button("Input ingredients instead");
        altIngredientsButton.setWrapText(true);

        //ingredients menu
        Button createMealButton = new Button("Create Meal");
        Button addNewIngredientButton = new Button("Add ingredient");
        Button backToMainInput = new Button("Back");

        //Create a new ingredient page
        Label ingredientNameLabel = new Label("Name");
        TextField ingredientName = new TextField();
        Label ingredientCalorieLabel = new Label("Calories (per gram)");
        TextField ingredientCalorie = new TextField();
        Button submitIngredient = new Button("Submit");


        //Add a new ingredient to a meal
        Label addedIngredientsLabel = new Label();
        ChoiceBox possibleIngredients = new ChoiceBox(FXCollections.observableArrayList(ingredientsFromDB));
        Button addIngredientToMeal = new Button("Add ingredient");
        Button createMeal = new Button("Create");
        TextField nameYourCreation = new TextField();


        //Add an existing meal page
        Button button6 = new Button("go back");
        button6.setWrapText(true);
        Button submitMenu = new Button("Add");
        Label foodDescLabel = new Label();
        foodDescLabel.setWrapText(true);
        //Choice box from the imported array of choices
        ChoiceBox menuCB = new ChoiceBox(FXCollections.observableArrayList(choices));


        //remove a meal page
        Button button7 = new Button("go back");
        button7.setWrapText(true);
        Button submitRemove = new Button("remove");
        ChoiceBox removeCB = new ChoiceBox(FXCollections.observableArrayList(removeRecords));



        //view graphs and targets page
        Button button9 = new Button("go back");
        button9.setWrapText(true);
        Label currentCalories = new Label("");
        Label targetCalories = new Label("");
        //Calories per day chart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Day");
        NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(7);
        xAxis.setTickUnit(1);
        yAxis.setLabel("Number of calories consumed");
        LineChart lineChart = new LineChart(xAxis,yAxis);
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Number of calories eaten per day");
        dataSeries1.getData().add(new XYChart.Data( 1, 2044));
        dataSeries1.getData().add(new XYChart.Data( 2, 2134));
        dataSeries1.getData().add(new XYChart.Data(3, 1800));
        dataSeries1.getData().add(new XYChart.Data(4, 2003));
        dataSeries1.getData().add(new XYChart.Data(5, 2400));
        dataSeries1.getData().add(new XYChart.Data(6, 3500));
        dataSeries1.getData().add(new XYChart.Data(7, 3100));
        lineChart.getData().add(dataSeries1);
        NumberAxis xAxis2 = new NumberAxis();
        xAxis2.setLabel("Day");
        xAxis2.setAutoRanging(false);
        xAxis2.setUpperBound(7);
        xAxis2.setTickUnit(1);
        NumberAxis yAxis2 = new NumberAxis();
        yAxis2.setLabel("Total number of calories consumed");
        LineChart lineChart2 = new LineChart(xAxis2,yAxis2);
        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Cumulative number of calories eaten");
        dataSeries2.getData().add(new XYChart.Data(0, 0));
        dataSeries2.getData().add(new XYChart.Data(1, 2044));
        dataSeries2.getData().add(new XYChart.Data(2, 4000));
        dataSeries2.getData().add(new XYChart.Data(3, 6409));
        dataSeries2.getData().add(new XYChart.Data(4, 9000));
        dataSeries2.getData().add(new XYChart.Data(5, 11786));
        dataSeries2.getData().add(new XYChart.Data(6, 13666));
        dataSeries2.getData().add(new XYChart.Data(7, 15111));
        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.setName("Target calories");
        dataSeries3.getData().add(new XYChart.Data(0, targetCaloriesForWeek));
        dataSeries3.getData().add(new XYChart.Data(1, targetCaloriesForWeek));
        dataSeries3.getData().add(new XYChart.Data(2, targetCaloriesForWeek));
        dataSeries3.getData().add(new XYChart.Data(3, targetCaloriesForWeek));
        dataSeries3.getData().add(new XYChart.Data(4, targetCaloriesForWeek));
        dataSeries3.getData().add(new XYChart.Data(5, targetCaloriesForWeek));
        dataSeries3.getData().add(new XYChart.Data(6, targetCaloriesForWeek));
        dataSeries3.getData().add(new XYChart.Data(7, targetCaloriesForWeek));
        lineChart2.getData().add(dataSeries2);
        lineChart2.getData().add(dataSeries3);

        //settings page
        Slider calorieSlider = new Slider(0,16000,targetCaloriesForDay);
        calorieSlider.setShowTickLabels(true);
        calorieSlider.setShowTickMarks(true);
        calorieSlider.setMajorTickUnit(500);
        calorieSlider.setMinorTickCount(1);
        calorieSlider.setSnapToTicks(true);
        Button backButton = new Button("go back");
        Label sliderCalorieLabel = new Label(String.valueOf(targetCaloriesForDay));
        Label infoCalorieLabel = new Label("Please input your daily calorie target");

        //Log in gridPane
        GridPane gridPane0 = new GridPane();
        gridPane0.add(usernameLabel, 0, 0, 1, 1);
        gridPane0.add(usernameTF,1,0,2,1);
        gridPane0.add(passwordLabel, 0, 1, 1, 1);
        gridPane0.add(passwordTF,1,1,2,1);
        gridPane0.add(button0, 0, 3, 1, 1);
        gridPane0.add(createAccountButton, 0, 4, 1, 1);
        gridPane0.add(loginInfoLabel,1,3,1,1);
        //Log in scene
        Scene logIn = new Scene(gridPane0, 300, 400);
        primaryStage.setScene(logIn);
        primaryStage.show();

        //menu gridPane
        GridPane gridPane1 = new GridPane();
        gridPane1.add(button1, 0,0,1,1);
        gridPane1.add(button2, 0,1,1,1);
        gridPane1.add(button3, 0,2,1,1);
        gridPane1.add(button4, 0,3,1,1);
        gridPane1.add(settingsButton, 0,4,1,1);
        gridPane1.add(logOut, 0, 5, 1, 1);
        gridPane1.add(exitButton, 0,6,1,1);
        gridPane1.setHgap(10);
        gridPane1.setVgap(10);
        gridPane1.getColumnConstraints().add(new ColumnConstraints(100));
        //Menu scene
        Scene menuScene = new Scene(gridPane1, 300,400);

        //addMeal gridPane
        GridPane gridPane2 = new GridPane();
        gridPane2.add(button5, 0,10,1,1);
        gridPane2.add(nameOfMealTF,1,0,2,1);
        gridPane2.add(nameLabel,0,0,1,1);
        gridPane2.add(calorieLabel,0,1,1,1);
        gridPane2.add(mealCaloriesTF,1,1,2,1);
        gridPane2.add(button8,0,9,1,1);
        gridPane2.add(descriptionLabel,0,3,1,1);
        gridPane2.add(descriptionOfFoodTF,1,3,2,1);
        gridPane2.add(altIngredientsButton,1,2,1,1);

        ColumnConstraints col50 = new ColumnConstraints();
        col50.setPercentWidth(50);
        gridPane2.getColumnConstraints().addAll(col50, col50);


        Scene addMeal = new Scene(gridPane2,300,400);
        gridPane2.setVgap(10);
        gridPane2.setHgap(10);

        //Add an existing meal page
        GridPane gridPane3 = new GridPane();
        gridPane3.add(button6,0,10,1,1);
        gridPane3.add(menuCB,0,0,1,1);
        gridPane3.add(submitMenu,1,0,1,1);
        gridPane3.add(foodDescLabel,0,1,1,1);
        Scene existingMeal = new Scene(gridPane3,300,400);

        //Remove a meal page
        GridPane gridPane4 = new GridPane();
        gridPane4.add(button7,0,10,1,1);
        gridPane4.add(removeCB,0,0,1,1);
        gridPane4.add(submitRemove,1,0,1,1);
        Scene removeMeal = new Scene(gridPane4,300,400);


        //View targets and graphs page
        GridPane gridPane5 = new GridPane();
        gridPane5.add(button9,0,10,1,1);
        gridPane5.add(currentCalories,0,0,1,1);
        gridPane5.add(targetCalories,0,1,1,1);
        gridPane5.add(lineChart,0,2,1,1);
        gridPane5.add(lineChart2,0,3,1,1);
        Scene viewTargets = new Scene(gridPane5,600,800);

        //Settings page
        GridPane gridPane6 = new GridPane();
        gridPane6.add(calorieSlider,0,1,2,1);
        gridPane6.add(backButton,0,10,1,1);
        gridPane6.add(sliderCalorieLabel,2,1,1,1);
        gridPane6.add(infoCalorieLabel,0,0,2,1);

        //Create Account gridPane
        GridPane gridPane7 = new GridPane();
        gridPane7.add(createUsername, 1, 1, 1, 1);
        gridPane7.add(createUsernameTf, 2, 2, 1, 1);
        gridPane7.add(createPassword, 1, 3, 1, 1);
        gridPane7.add(createPasswordTf, 2, 4, 1, 1);
        gridPane7.add(confirmPassword, 1, 5, 1, 1);
        gridPane7.add(confirmPasswordTf, 2, 6, 1, 1);
        gridPane7.add(finaliseCreate, 1, 7, 1, 1);
        Scene createAccount = new Scene(gridPane7, 300, 400);

        ColumnConstraints col20 = new ColumnConstraints();
        col20.setPercentWidth(20);
        ColumnConstraints col30 = new ColumnConstraints();
        col30.setPercentWidth(40);
        gridPane6.getColumnConstraints().addAll(col50, col30,col20);
        Scene settingsScene = new Scene(gridPane6, 600,400);

        //Ingredients menu
        GridPane gridPane8 = new GridPane();
        gridPane8.add(addNewIngredientButton,0,0,1,1);
        gridPane8.add(createMealButton,0,1,1,1);
        gridPane8.add(backToMainInput,0,2,1,1);
        Scene ingredientMenuScene = new Scene(gridPane8, 300,400);

        //New ingredient add
        GridPane gridPane9 = new GridPane();
        gridPane9.add(ingredientNameLabel,0,0,1,1);
        gridPane9.add(ingredientName,1,0,1,1);
        gridPane9.add(ingredientCalorieLabel,0,1,1,1);
        gridPane9.add(ingredientCalorie,1,1,1,1);
        gridPane9.add(submitIngredient,0,2,1,1);
        Scene ingredientCreateScene = new Scene(gridPane9, 300,400);

        //Create meal out of ingredients gridPane
        GridPane gridPane10 = new GridPane();
        gridPane10.add(addedIngredientsLabel,0,2,1,1);
        gridPane10.add(possibleIngredients,0,1,1,1);
        gridPane10.add(addIngredientToMeal,1,1,1,1);
        gridPane10.add(createMeal,1,0,1,1);
        gridPane10.add(nameYourCreation,0,0,1,1);
        Scene ingredientCreateMealScene = new Scene(gridPane10,300,400);


        //BUTTON CODE

        //LogIn --> Menu
        button0.setOnAction(actionEvent ->{
            String usernameString = usernameTF.getText();
            String passwordString = passwordTF.getText();
            boolean loginSuccess = authorize.login(usernameString, passwordString);
            //Search for user in database
            if(loginSuccess){
                primaryStage.setTitle("Calorie Tracker");
                primaryStage.setScene(menuScene);
                userID = Integer.parseInt((sqlDatabase.getFromDatabase("SELECT UserID FROM User WHERE UserName = '" + usernameString + "'").get(0)[0]));
                updateArrays();
            }
            else{
                System.out.println("This user doesn't exist or the password is incorrect.");
            }
        });

        //Log In --> Create Account
        createAccountButton.setOnAction(actionEvent ->{
            primaryStage.setTitle("Create Account");
            primaryStage.setScene(createAccount);
        });


        //Create Account --> Log In
        finaliseCreate.setOnAction(actionEvent ->{
            String newUsername = createUsernameTf.getText();
            String newPassword = createPasswordTf.getText();
            String checkPassword = confirmPasswordTf.getText();
            if (newPassword.equals(checkPassword)){
                authorize.createAccount(newUsername, newPassword);
            }else {
                System.out.println("Passwords don't match");
            }
            primaryStage.setTitle("Log in");
            primaryStage.setScene(logIn);
        });

        //Menu --> LogIn (logged out)

        logOut.setOnAction(actionEvent ->{
            userID = 0;
            updateArrays();
            primaryStage.setTitle("Log In");
            primaryStage.setScene(logIn);
        });

        //Menu --> addMeal
        button1.setOnAction(actionEvent -> {
            updateArrays();
            primaryStage.setTitle("Add a new meal");
            primaryStage.setScene(addMeal);
        });
        //Menu --> existingMeal
        button2.setOnAction(actionEvent -> {
            updateArrays();
            primaryStage.setTitle("Add an existing meal");
            menuCB.getItems().clear();
            menuCB.setItems(FXCollections.observableArrayList(choices));
            primaryStage.setScene(existingMeal);
        });
        //Menu --> removeMeal
        button3.setOnAction(actionEvent -> {
            updateArrays();
            primaryStage.setTitle("Remove a meal");
            removeCB.getItems().clear();
            removeCB.setItems(FXCollections.observableArrayList(removeRecords));
            primaryStage.setScene(removeMeal);
        });
        //Menu --> viewTargets
        button4.setOnAction(actionEvent -> {
            updateArrays();

            System.out.println(caloriesForRecordMeals);
            System.out.println(datesEaten);
            if(!datesEaten.isEmpty()){
                ArrayList<Integer> caloriesPerDay = new ArrayList<>();
                int first;
                first = Integer.parseInt(datesEaten.get(0).substring(0, 2));
                int numberOfEntries = datesEaten.size();
                int daysThroughCurrentWeek = 1;
                caloriesPerDay.add(0);
                for(int i = 0; i < caloriesForRecordMeals.size(); i++){
                    //System.out.println("1"  + caloriesPerDay);
                    if(Integer.parseInt(datesEaten.get(i).substring(0,2)) == first){
                        caloriesPerDay.set(caloriesPerDay.size()-1, caloriesPerDay.get(caloriesPerDay.size()-1) + caloriesForRecordMeals.get(i));
                    }else{
                        caloriesPerDay.add(caloriesForRecordMeals.get(i));
                        daysThroughCurrentWeek = daysThroughCurrentWeek + 1;
                    }
                }
                System.out.println(caloriesPerDay);
                System.out.println(daysThroughCurrentWeek);
                //Update the chart with the newest values
                dataSeries1.getData().clear();
                for(int i = 0; i < daysThroughCurrentWeek; i++){
                    dataSeries1.getData().add(new XYChart.Data(i, caloriesPerDay.get(i)));
                }
                int current = 0;
                dataSeries2.getData().clear();
                for(int i = 0; i < daysThroughCurrentWeek; i++){
                    current = current + caloriesPerDay.get(i);
                    dataSeries2.getData().add(new XYChart.Data(i,current));
                }
                dataSeries3.getData().clear();
                for(int i = 0; i< 7; i++){
                    dataSeries3.getData().add(new XYChart.Data(i,targetCaloriesForWeek));
                }

                int weeklyCalorie = current;

                primaryStage.setTitle("View targets and graphs");
                currentCalories.setText("You have consumed " + weeklyCalorie + " calories since the start of the week");
                if(weeklyCalorie > targetCaloriesForWeek){
                    targetCalories.setText("You have exceeded your target calorie amount for this week by " + (weeklyCalorie - targetCaloriesForWeek));
                }
                else if(weeklyCalorie<((targetCaloriesForWeek /7)*daysThroughCurrentWeek) && weeklyCalorie > ((targetCaloriesForWeek /9)*daysThroughCurrentWeek)){
                    targetCalories.setText("You are on track to achieving your calorie target of: " + targetCaloriesForWeek + " for this week");
                }
                else if(weeklyCalorie < ((targetCaloriesForWeek /9)*daysThroughCurrentWeek)){
                    targetCalories.setText("You are behind your target of " + targetCaloriesForWeek + " calories for this week");
                }
                else {
                    targetCalories.setText("You are not on track to hit the targeted calorie amount (" + targetCaloriesForWeek + ") for this week");
                }
            }
            else{
                dataSeries1.getData().clear();
                dataSeries2.getData().clear();
                currentCalories.setText("No data");
                targetCalories.setText("");
            }
            primaryStage.setScene(viewTargets);
        });
        button5.setOnAction(actionEvent -> { //addMeal --> menu
            updateArrays();
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });
        backToMainInput.setOnAction(actionEvent -> { //ingredients --> addMeal
            updateArrays();
            primaryStage.setTitle("Add a new meal");
            primaryStage.setScene(addMeal);
        });
        button6.setOnAction(actionEvent -> { //existingMeal --> menu
            updateArrays();
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });
        button7.setOnAction(actionEvent -> { //removeMeal --> menu
            updateArrays();
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });

        exitButton.setOnAction(actionEvent -> { //exit button
            System.exit(0);
        });

        //submit new food
        button8.setOnAction(actionEvent -> { //submit a new item of food WIP
            updateArrays();
            String foodName = nameOfMealTF.getText();
            String calorieValue = mealCaloriesTF.getText();
            String foodDesc = descriptionOfFoodTF.getText();
            System.out.println(foodName + " " + calorieValue + " " + foodDesc);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy");
            LocalDateTime now = LocalDateTime.now();
            //System.out.println(dtf.format(now));
            try{
                int value = Integer.parseInt(calorieValue);
                if (value > 0){
                    String sql = "INSERT INTO Meal (MealName, CalorieCount) VALUES ('" + foodName + "'," +calorieValue + ")";
                    sqlDatabase.changeDatabase(sql);
                    sql = "SELECT MealID FROM Meal WHERE MealName = '" + foodName +"'";
                    int mealID = Integer.parseInt(sqlDatabase.getFromDatabase(sql).get(0)[0]);
                    sql = "INSERT INTO MealRecord (UserID, MealID, Date) VALUES ('" + userID + "','" + mealID + "','" + dtf.format(now) +"')";
                    sqlDatabase.changeDatabase(sql);
                }
                else{
                    System.out.println("not a valid number");
                }
            }catch(NumberFormatException e){
                System.out.println("not a valid number");
            }
            updateArrays();
        });

        button9.setOnAction(actionEvent -> { //View --> menu
            updateArrays();
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });

        settingsButton.setOnAction(actionEvent -> { //menu --> settings page
            updateArrays();
            primaryStage.setTitle("Settings");
            primaryStage.setScene(settingsScene);
        });

        backButton.setOnAction(actionEvent -> { //settings --> menu
            updateArrays();
            primaryStage.setTitle("Calorie tracker");
            int caloriesForDayChange =  (int)calorieSlider.getValue();

            String sql = "UPDATE User SET WeeklyTarget = " + (caloriesForDayChange * 7) + " WHERE UserID = " + userID ;
            sqlDatabase.changeDatabase(sql);
            updateArrays();
            primaryStage.setScene(menuScene);
        });

        //Listeners to detect the current state of dropdown menus or sliders
        //Detect selection from menu and submit button code to add a new item to the database ***FOR ADD***
        menuCB.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            submitMenu.setOnAction(actionEvent -> { //View --> menu
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy");
                LocalDateTime now = LocalDateTime.now();
                String sql = "INSERT INTO Meal (MealName, CalorieCount) VALUES ('" + choices.get(t1.intValue()) + "','" + caloriesForChoices.get(t1.intValue()) + "')";
                sqlDatabase.changeDatabase(sql);
                sql = "SELECT MealID FROM Meal WHERE MealName = '" + choices.get(t1.intValue()) + "'";
                int mealID = Integer.parseInt(sqlDatabase.getFromDatabase(sql).get(sqlDatabase.getFromDatabase(sql).size()-1)[0]);
                sql = "INSERT INTO MealRecord (UserID, MealID, Date, MealTypeID) VALUES (" + userID + "," + mealID + ", '" +dtf.format(now) + "', 1)";
                sqlDatabase.changeDatabase(sql);
                updateArrays();

            });
        });

        //Detect selection from menu and submit button code to add a new item to the database ***FOR REMOVE***
        removeCB.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            submitRemove.setOnAction(actionEvent -> { //View --> menu
                System.out.println("Remove the following:");
                System.out.println("1 x " + removeRecords.get(t1.intValue()));

                String sql = "SELECT MealID FROM Meal WHERE MealName = '" + removeRecords.get(t1.intValue()) + "'";
                int mealID = Integer.parseInt(sqlDatabase.getFromDatabase(sql).get(0)[0]) ;

                        sql = "DELETE FROM MealRecord WHERE MealID = " + mealID + " AND UserID = " + userID;
                        sqlDatabase.changeDatabase(sql);



            });
        });

        //addMeal --> ingredientMenu
        altIngredientsButton.setOnAction(actionEvent -> {
            updateArrays();
            primaryStage.setTitle("Ingredient Menu");
            primaryStage.setScene(ingredientMenuScene);
        });

        //ingredientMenu --> createIngredient
        addNewIngredientButton.setOnAction(actionEvent -> {
            updateArrays();
            primaryStage.setTitle("Create Ingredient");
            primaryStage.setScene(ingredientCreateScene);
        });

        // ingredientMenu --> createMeal
        createMealButton.setOnAction(actionEvent -> {
            updateArrays();
            primaryStage.setTitle("Create Meal");
            primaryStage.setScene(ingredientCreateMealScene);
        });

        //submit ingredient (createIngredient --> ingredientMenu)
        submitIngredient.setOnAction(actionEvent -> {
            primaryStage.setTitle("Ingredient Menu");
            String ingredientNameStr = ingredientName.getText();
            try{
                int ingredientCalorieInt = Integer.parseInt(ingredientCalorie.getText());
                String sql = "INSERT INTO Ingredients (IngredientName, CaloriesPerGram) VALUES ('" + ingredientNameStr +"','" + ingredientCalorieInt + "')";
                sqlDatabase.changeDatabase(sql);
            }
            catch(NumberFormatException ignored){}
            primaryStage.setScene(ingredientMenuScene);
            updateArrays();
        });

        // create a new meal with the added ingredients (LABEL)
        addIngredientToMeal.setOnAction(actionEvent -> {
            updateArrays();
            String current = addedIngredientsLabel.getText();
            fullIngredientList.add(possibleIngredients.getValue());
            addedIngredientsLabel.setText(current + possibleIngredients.getValue() + "\n");
        });

        // create a new meal with the added ingredients (BUTTON)
        createMeal.setOnAction(actionEvent -> {
            primaryStage.setTitle("Ingredient Menu");
            primaryStage.setScene(ingredientMenuScene);
            int caloriesToAdd = 0;
            String nameOfCreation = nameYourCreation.getText();
            for (Object o : fullCaloriesList) {
                caloriesToAdd = caloriesToAdd + Integer.parseInt(o.toString());
            }
        });


        calorieSlider.valueProperty().addListener((observableValue, number, t1) -> sliderCalorieLabel.setText(String.valueOf(t1.intValue())));
    }

    private static Database.SqlInterface sqlDatabase;
    private static Authentication authorize;

    public static void main(String[] args) {
        sqlDatabase = new Database.SqlInterface("C:\\Users\\richy\\Desktop\\CSED Coursework 2\\src\\CSED Database.db");
        authorize = new Authentication(sqlDatabase);
        launch(args);
    }
}