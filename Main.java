package sample;

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


public class Main extends Application {

    @SuppressWarnings("unused")
	@Override
    public void start(Stage primaryStage) {
        //IMPORT THESE FROM THE DATABASE (These also need to be re-imported when the pages change)
        String[] choices = {"Pizza","Cheese Burger","Fries"}; // Food you have had before that you can easily add again
        int[] caloriesForChoices = {600,500,300}; //Number of calories for these items
        String[] descriptionsForChoices = {"Its a pizza","A burger with cheese","standard fries"}; //descriptions for these items
        int targetCaloriesForTheWeek = 14000; //Total calorie target for the week
        String[] removeChoices = {"pizza","bread"}; //Food that you have had this week (in case you want to remove an item)
        int targetCaloriesForDay = 2000;
        //IMPORT THESE FROM THE DATABASE

        //Set the title to log in
        primaryStage.setTitle("Log in");
        
        //Log in page
        Label usernameLabel = new Label("Enter Username: ");
        usernameLabel.setWrapText(true);
        TextField usernameTF = new TextField();
        Label passwordLabel = new Label("Enter Password ");
        passwordLabel.setWrapText(true);
        TextField passwordTF = new TextField();
        Button button0 = new Button("Submit");
        Button createAccountButton = new Button("Create Account");
        
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
        ChoiceBox removeCB = new ChoiceBox(FXCollections.observableArrayList(removeChoices));



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
        dataSeries3.getData().add(new XYChart.Data(0, targetCaloriesForTheWeek));
        dataSeries3.getData().add(new XYChart.Data(1, targetCaloriesForTheWeek));
        dataSeries3.getData().add(new XYChart.Data(2, targetCaloriesForTheWeek));
        dataSeries3.getData().add(new XYChart.Data(3, targetCaloriesForTheWeek));
        dataSeries3.getData().add(new XYChart.Data(4, targetCaloriesForTheWeek));
        dataSeries3.getData().add(new XYChart.Data(5, targetCaloriesForTheWeek));
        dataSeries3.getData().add(new XYChart.Data(6, targetCaloriesForTheWeek));
        dataSeries3.getData().add(new XYChart.Data(7, targetCaloriesForTheWeek));
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


        //BUTTON CODE
        
        //LogIn --> Menu
        button0.setOnAction(actionEvent ->{
        	String usernameString = usernameTF.getText();
        	//Search for user name in database
        	if(true){
        		String passwordString = passwordTF.getText();
        		//Search for password in database
        		if(true){
                	primaryStage.setTitle("Calorie Tracker");
                	primaryStage.setScene(menuScene);
        		}else{
        			System.out.println("Incorrect password.");
        		}
        		
        	}else{
        		System.out.println("This user doesn't exist.");
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
        		//Create new account in database
        	}else{
        		System.out.println("Passwords don't match");
        	}
        	primaryStage.setTitle("Log in");
        	primaryStage.setScene(logIn);
        });
        
        //Menu --> LogIn (logged out)
        logOut.setOnAction(actionEvent ->{
        	primaryStage.setTitle("Log In");
        	primaryStage.setScene(logIn);
        });
        
        //Menu --> addMeal
        button1.setOnAction(actionEvent -> {
            primaryStage.setTitle("Add a new meal");
            primaryStage.setScene(addMeal);
        });
        //Menu --> existingMeal
        button2.setOnAction(actionEvent -> {
            primaryStage.setTitle("Add an existing meal");
            primaryStage.setScene(existingMeal);
        });
        //Menu --> removeMeal
        button3.setOnAction(actionEvent -> {
            primaryStage.setTitle("Remove a meal");
            primaryStage.setScene(removeMeal);
        });
        //Menu --> viewTargets
        button4.setOnAction(actionEvent -> {
            //GET THE DATA FROM THE DATABASE
            int[] weeklyCaloriesArray = {1000,2000,3000,2000,2500};
            //GET THE DATA FROM THE DATABASE


            //Update the chart with the newest values
            dataSeries1.getData().clear();
            for(int i = 0; i < weeklyCaloriesArray.length; i++){
                dataSeries1.getData().add(new XYChart.Data(i,weeklyCaloriesArray[i]));
            }
            int current = 0;
            dataSeries2.getData().clear();
            for(int i = 0; i < weeklyCaloriesArray.length; i++){
                current = current + weeklyCaloriesArray[i];
                dataSeries2.getData().add(new XYChart.Data(i,current));
            }

            int weeklyCalorie = current;
            int day = weeklyCaloriesArray.length;

            primaryStage.setTitle("View targets and graphs");
            currentCalories.setText("You have consumed " + weeklyCalorie + " calories since the start of the week");
            if(weeklyCalorie > targetCaloriesForTheWeek){
                targetCalories.setText("You have exceeded your target calorie amount for this week by " + (weeklyCalorie - targetCaloriesForTheWeek));
            }
            else if(weeklyCalorie<((targetCaloriesForTheWeek/7)*day) && weeklyCalorie > ((targetCaloriesForTheWeek/9)*day)){
                targetCalories.setText("You are on track to achieving your calorie target of: " + targetCaloriesForTheWeek + " for this week");
            }
            else if(weeklyCalorie < ((targetCaloriesForTheWeek/9)*day)){
                targetCalories.setText("You are behind your target of " + targetCaloriesForTheWeek + " calories for this week");
            }
            else {
                targetCalories.setText("You are not on track to hit the targeted calorie amount (" + targetCaloriesForTheWeek + ") for this week");
            }

            primaryStage.setScene(viewTargets);
        });
        button5.setOnAction(actionEvent -> { //addMeal --> menu
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });
        button6.setOnAction(actionEvent -> { //existingMeal --> menu
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });
        button7.setOnAction(actionEvent -> { //removeMeal --> menu
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });

        exitButton.setOnAction(actionEvent -> { //exit button
            System.exit(0);
        });

        //submit new food
        button8.setOnAction(actionEvent -> { //submit a new item of food WIP
            String foodName = nameOfMealTF.getText();
            String calorieValue = mealCaloriesTF.getText();
            String foodDesc = descriptionOfFoodTF.getText();
            System.out.println(foodName + " " + calorieValue + " " + foodDesc);
            try{
                int value = Integer.valueOf(calorieValue);
                if (value > 0){
                    //SEND NAME, VALUE AND DESC TO THE DATABASE
                }
                else{
                	System.out.println("not a valid number");
                }
            }catch(NumberFormatException e){
                System.out.println("not a valid number");
            }
        });

        button9.setOnAction(actionEvent -> { //View --> menu
            primaryStage.setTitle("Calorie tracker");
            primaryStage.setScene(menuScene);
        });

        settingsButton.setOnAction(actionEvent -> { //menu --> settings page
            primaryStage.setTitle("Settings");
            primaryStage.setScene(settingsScene);
        });

        backButton.setOnAction(actionEvent -> { //settings --> menu
            primaryStage.setTitle("Calorie tracker");
            int caloriesForDayChange =  (int)calorieSlider.getValue();
            //UPDATE DATABASE WITH NEW TARGET
            primaryStage.setScene(menuScene);
        });

        //Listeners to detect the current state of dropdown menus or sliders
        //Detect selection from menu and submit button code to add a new item to the database ***FOR ADD***
        menuCB.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            foodDescLabel.setText(descriptionsForChoices[t1.intValue()]);
            submitMenu.setOnAction(actionEvent -> { //View --> menu
                System.out.println("Add the following:");
                System.out.println("1 x " + choices[t1.intValue()] + " with a total of " + caloriesForChoices[t1.intValue()] + " calories");
                //ADD this data to the database
            });
        });

        //Detect selection from menu and submit button code to add a new item to the database ***FOR REMOVE***
        removeCB.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            //foodDescLabel.setText(descriptionsForChoices[t1.intValue()]);
            submitRemove.setOnAction(actionEvent -> { //View --> menu
                System.out.println("Remove the following:");
                System.out.println("1 x " + removeChoices[t1.intValue()]);
                //ADD this data to the database
            });
        });

        calorieSlider.valueProperty().addListener((observableValue, number, t1) -> sliderCalorieLabel.setText(String.valueOf(t1.intValue())));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
