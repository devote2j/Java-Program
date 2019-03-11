/***
 * Program: 	A GUI program to get input from users and calculate monthly payment, year total payment
 * and eligibility according to the income inputs. If user enter non-numeric value. The background color
 * of any TextField in which a user types will change to red. 
 *
 * @author:		Yang,Bowen
 *
 * Created: 	3/10/2019
 */

import java.io.*;
import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;
import java.util.*;

public class LoanDriver extends Application {

	Color backgroundColor = null;

	BackgroundFill backgroundFill = new BackgroundFill(backgroundColor, null, null);

	Background background = new Background(backgroundFill);
	
	private final String FILE_NAME1 = "/Users/itsfiziks/eclipse-workspace/CIS279HW5/src/Java-Program/annual_interest_rates.txt";
	private final String FILE_NAME2 = "/Users/itsfiziks/eclipse-workspace/CIS279HW5/src/Java-Program/loan_terms.txt";
	
	private double userTextField1Num = 0.0;
	private double userTextField2Num = 0.0;
	private double userTextField3Num = 0.0;
	private double userTextField4Num = 0.0;
	private double userTextField5Num = 0.0;
	private double userTextField6Num = 0.0;
	private int userTextField7Num = 0;
	private double userTextField8Num = 0.0;
	
	private ComboBox<String> annualInterestRatesBox = null;
	private ComboBox<String> termInYearsBox = null;
	
	private ArrayList<String> annualInterestRatesList = new ArrayList<String>();
	private ArrayList<String> termInYearsList = new ArrayList<String>();
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final String FONT_NAME = "Georgia";
		final double ELIGIBILITY_PERCENTAGE = 0.25;

		primaryStage.setTitle("Loan Payment and Eligibility Calculator Form");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(20);
		grid.setPadding(new Insets(10, 10, 10, 10));

		//	Read in data from files
		readData();
		
		//	Create borders
		Paint borderColor = new Color(0, 0, 0, 0.6);

		BorderStrokeStyle borderStrokeStyle = new BorderStrokeStyle(StrokeType.OUTSIDE, StrokeLineJoin.ROUND, StrokeLineCap.ROUND, 0.0, 0.0, null);

		CornerRadii cornerRadii = new CornerRadii(4.0);

		BorderWidths borderWidth = new BorderWidths(2.0);

		BorderStroke borderStroke = new BorderStroke(borderColor, borderStrokeStyle, cornerRadii, borderWidth);

		BorderStroke[] borderStrokeArray = new BorderStroke[1];

		borderStrokeArray[0] = borderStroke;

		Border border = new Border(borderStrokeArray);

		//	Salary and wages widgets
		Label label1 = new Label("Salary and wages");
		label1.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label1, 0, 0); //	Column 0, row 0

		TextField userTextField1 = new TextField();
		userTextField1.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField1.setBorder(border);
		userTextField1.setOnKeyTyped(e -> {
			boolean allDigits = userTextField1.getText().chars().allMatch(Character::isDigit);
			boolean allValidNumbers = isNumeric(userTextField1.getText());
			
			if(allValidNumbers || allDigits) {
				backgroundColor = Color.rgb(255, 255, 255);
			}
			else {
				backgroundColor = Color.rgb(255, 0, 0, 0.6);
			}
			
//			if(allLetters) {
//				backgroundColor = Color.rgb(255, 0, 0,0.6);
//			}
//			else {
//				backgroundColor = Color.rgb(255, 255, 255);
//			}
			
			backgroundFill = new BackgroundFill(backgroundColor, null, null);

			background = new Background(backgroundFill);

			userTextField1.setBackground(background);
		});

		GridPane.setConstraints(userTextField1, 1, 0); //	Column 1, row 0

		//	Interest income widgets
		Label label2 = new Label("Interest income");
		label2.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label2, 0, 1); //	Column 0, row 1

		TextField userTextField2 = new TextField();
		userTextField2.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField2.setBorder(border);
		GridPane.setConstraints(userTextField2, 1, 1); //	Column 1, row 1

		userTextField2.setOnKeyTyped(e -> {
			boolean allDigits = userTextField2.getText().chars().allMatch(Character::isDigit);
			boolean allValidNumbers = isNumeric(userTextField2.getText());

			if(allDigits || allValidNumbers) {
				backgroundColor = Color.rgb(255, 255, 255);
			}
			else {
				backgroundColor = Color.rgb(255, 0, 0, 0.6);
			}

			backgroundFill = new BackgroundFill(backgroundColor, null, null);

			background = new Background(backgroundFill);

			userTextField2.setBackground(background);
		});

		//	Investment income widgets
		Label label3 = new Label("Investment income");
		label3.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label3, 0, 2); //	Column 0, row 2

		TextField userTextField3 = new TextField();
		userTextField3.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField3.setBorder(border);
		GridPane.setConstraints(userTextField3, 1, 2); //	Column 1, row 2

		userTextField3.setOnKeyTyped(e -> {
			boolean allDigits = userTextField3.getText().chars().allMatch(Character::isDigit);
			boolean allValidNumbers = isNumeric(userTextField3.getText());
		
			if(allDigits || allValidNumbers) {
				backgroundColor = Color.rgb(255, 255, 255);
			}
			else {
				backgroundColor = Color.rgb(255, 0, 0, 0.6);
			}

			backgroundFill = new BackgroundFill(backgroundColor, null, null);

			background = new Background(backgroundFill);

			userTextField3.setBackground(background);
		});

		//	Other income widgets
		Label label4 = new Label("Other income");
		label4.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label4, 0, 3); //	Column 0, row 3

		TextField userTextField4 = new TextField();
		userTextField4.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField4.setBorder(border);
		GridPane.setConstraints(userTextField4, 1, 3); //	Column 1, row 3

		userTextField4.setOnKeyTyped(e -> {
			boolean allDigits = userTextField4.getText().chars().allMatch(Character::isDigit);
			boolean allValidNumbers = isNumeric(userTextField4.getText());

			if(allDigits || allValidNumbers) {
				backgroundColor = Color.rgb(255, 255, 255);
			}
			else {
				backgroundColor = Color.rgb(255, 0, 0, 0.6);
			}

			backgroundFill = new BackgroundFill(backgroundColor, null, null);

			background = new Background(backgroundFill);

			userTextField4.setBackground(background);
		});

		//	Total income widgets
		Label label5 = new Label("Total income");
		label5.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label5, 0, 4); //	Column 0, row 4

		TextField userTextField5 = new TextField();
		userTextField5.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField5.setBorder(border);
		GridPane.setConstraints(userTextField5, 1, 4); //	Column 1, row 4

		//	Annual interest rate widgets
		Label label6 = new Label("Annual interest rate(n.nnn%)");
		label6.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label6, 4, 0); //	Column 4, row 0

//		TextField userTextField6 = new TextField();
//		userTextField6.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
//		userTextField6.setBorder(border);
//		GridPane.setConstraints(userTextField6, 5, 0); //	Column 5, row 0

//		userTextField6.setOnKeyTyped(e -> {
//			boolean allDigits = userTextField6.getText().chars().allMatch(Character::isDigit);
//			boolean allValidNumbers = isNumeric(userTextField6.getText());
//
//			if(allDigits || allValidNumbers) {
//				backgroundColor = Color.rgb(255, 255, 255);
//			}
//			else {
//				backgroundColor = Color.rgb(255, 0, 0, 0.6);
//			}
//
//			backgroundFill = new BackgroundFill(backgroundColor, null, null);
//
//			background = new Background(backgroundFill);
//
//			userTextField6.setBackground(background);
//		});
		
		//	AnnualInterestRates ComboBox
		ObservableList<String> olAnnualInterestRates = FXCollections.observableArrayList(annualInterestRatesList);
		
		annualInterestRatesBox = new ComboBox<String>();
		annualInterestRatesBox.setEditable(false);
		annualInterestRatesBox.setPrefWidth(235);
		annualInterestRatesBox.setPromptText("select annual interest rate");
		annualInterestRatesBox.setItems(olAnnualInterestRates);
		GridPane.setConstraints(annualInterestRatesBox, 5, 0);
		
		//	Term in years widgets
		Label label7 = new Label("Term in years");
		label7.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label7, 4, 1); //	Column 4, row 1

//		TextField userTextField7 = new TextField();
//		userTextField7.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
//		userTextField7.setBorder(border);
//		GridPane.setConstraints(userTextField7, 5, 1); //	Column 5, row 1
//
//		userTextField7.setOnKeyTyped(e -> {
//			boolean allDigits = userTextField7.getText().chars().allMatch(Character::isDigit);
//			boolean allValidNumbers = isNumeric(userTextField7.getText());
//
//			if(allDigits || allValidNumbers) {
//				backgroundColor = Color.rgb(255, 255, 255);
//			}
//			else {
//				backgroundColor = Color.rgb(255, 0, 0, 0.6);
//			}
//
//			backgroundFill = new BackgroundFill(backgroundColor, null, null);
//
//			background = new Background(backgroundFill);
//
//			userTextField7.setBackground(background);
//		});
		
		//	TermInYears ComboBox
		ObservableList<String> olTermInYears = FXCollections.observableArrayList(termInYearsList);
		
		termInYearsBox = new ComboBox<String>();
		termInYearsBox.setEditable(false);
		termInYearsBox.setPrefWidth(235);
		termInYearsBox.setPromptText("select years");
		termInYearsBox.setItems(olTermInYears);
		GridPane.setConstraints(termInYearsBox, 5, 1);
		
		//	Loan amount widgets
		Label label8 = new Label("Loan amount");
		label8.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label8, 4, 2); //	Column 4, row 2

		TextField userTextField8 = new TextField();
		userTextField8.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField8.setBorder(border);
		GridPane.setConstraints(userTextField8, 5, 2); //	Column 5, row 2

		userTextField8.setOnKeyTyped(e -> {
			boolean allDigits = userTextField8.getText().chars().allMatch(Character::isDigit);
			boolean allValidNumbers = isNumeric(userTextField8.getText());

			if(allDigits || allValidNumbers) {
				backgroundColor = Color.rgb(255, 255, 255);
			}
			else {
				backgroundColor = Color.rgb(255, 0, 0, 0.6);
			}

			backgroundFill = new BackgroundFill(backgroundColor, null, null);

			background = new Background(backgroundFill);

			userTextField8.setBackground(background);
		});

		//	Monthly payment widgets
		Label label9 = new Label("Monthly payment");
		label9.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label9, 4, 3); //	Column 4, row 3

		TextField userTextField9 = new TextField();
		userTextField9.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField9.setBorder(border);
		GridPane.setConstraints(userTextField9, 5, 3); //	Column 5, row 3

		//	Total payments over life of loan widgets
		Label label10 = new Label("Total payments over life of loan");
		label10.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(label10, 4, 4); //	Column 4, row 4

		TextField userTextField10 = new TextField();
		userTextField10.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		userTextField10.setBorder(border);
		GridPane.setConstraints(userTextField10, 5, 4); //	Column 5, row 4

		//	Text keyPressNotification
		Text keyPressNotification = new Text();
		keyPressNotification.setFont(Font.font(FONT_NAME, FontWeight.BOLD, 14));
		GridPane.setConstraints(keyPressNotification, 4, 5);

		// 	Button1
		Button button1 = new Button();
		button1.setText("Calculate Payment");
		button1.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		GridPane.setConstraints(button1, 0, 5);

		//	Button1 action
		button1.setOnAction(e -> {
			inputToNumber(userTextField1.getText(), userTextField2.getText(), userTextField3.getText(), userTextField4.getText(),
			    annualInterestRatesBox, termInYearsBox, userTextField8.getText());
			userTextField5Num = userTextField1Num + userTextField2Num + userTextField3Num + userTextField4Num;
			Loan loan = new Loan();
			loan.setAnnualInterestRate(userTextField6Num);
			loan.setTermInYears(userTextField7Num);
			loan.setLoanAmount(userTextField8Num);
			loan.calcMonthlyPayment();
			loan.calcTotalPayment();
			userTextField5.setText(new DecimalFormat("##.##").format(userTextField5Num));
			userTextField9.setText(new DecimalFormat("##.##").format(loan.getMonthlyPayment()));
			userTextField10.setText(new DecimalFormat("##.##").format(loan.getTotalPayment()));

			//	Calculate eligibility
			double eligibilityPercentage = loan.getMonthlyPayment() / (userTextField5Num / 12);
			if(eligibilityPercentage > ELIGIBILITY_PERCENTAGE) {
				keyPressNotification.setText("Sorry, you are not eligible for the loan.\nYour monthly payment takes more than 25%\nof your "
						+ "total monthly income which is: " + new DecimalFormat("##.##").format(eligibilityPercentage * 100) + "%.");
			}
			else {
				keyPressNotification.setText("You are eligible for the loan.\nYour monthly payment takes " + new DecimalFormat("##.##").format(eligibilityPercentage * 100) +
						"%" + "\nof your total monthly income.");
			}
		});

		//	Button2
		Button button2 = new Button();
		button2.setText("Cancel");
		button2.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 18));
		button2.setOnAction(e -> primaryStage.close());
		GridPane.setConstraints(button2, 1, 5);
		
		//	Add all widgets
		grid.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, userTextField1,
				userTextField2, userTextField3, userTextField4, userTextField5, annualInterestRatesBox, termInYearsBox, userTextField8,
				userTextField9, userTextField10, keyPressNotification, button1, button2);

		Scene scene = new Scene(grid, 1100, 450);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void readData() {
		File file1 = new File(FILE_NAME1);
		File file2 = new File(FILE_NAME2);
		Scanner inputFile = null;
		
		//	Read in "annual_interest_rates.txt" data
		try {
			inputFile = new Scanner(file1);
			while(inputFile.hasNextLine()) {
				annualInterestRatesList.add(inputFile.nextLine());
			}
		}
		catch(FileNotFoundException e) {
			annualInterestRatesBox.getItems().addAll(
				"3.00",
				"3.25",
				"3.50",
				"3.75",
				"4.00",
				"4.25",
				"4.50",
				"4.75",
				"5.00",
				"5.25",
				"5.50",
				"5.75",
				"6.00"
			);
		}
		catch(Exception e) {
			annualInterestRatesBox.getItems().addAll(
				"3.00",
				"3.25",
				"3.50",
				"3.75",
				"4.00",
				"4.25",
				"4.50",
				"4.75",
				"5.00",
				"5.25",
				"5.50",
				"5.75",
				"6.00"
			);
		}
		
		//	Read in "loan_terms.txt" data
		try {
			inputFile = new Scanner(file2);
			while(inputFile.hasNextLine()) {
				termInYearsList.add(inputFile.nextLine());
			}
		}
		catch(FileNotFoundException e) {
			termInYearsBox.getItems().addAll(
				"5",
				"10",
				"15",
				"20",
				"25",
				"30"
			);
		}
		catch(Exception e) {
			termInYearsBox.getItems().addAll(
				"5",
				"10",
				"15",
				"20",
				"25",
				"30"
			);
		}
		inputFile.close();
	}

	//	Convert all user inputs to numbers
	public void inputToNumber(String userTextField1, String userTextField2, String userTextField3, String userTextField4,
					ComboBox<String> annualInterestRatesBox, ComboBox<String> termInYearsBox, String userTextField8) {
    userTextField1Num = Double.parseDouble(userTextField1);
		userTextField2Num = Double.parseDouble(userTextField2);
		userTextField3Num = Double.parseDouble(userTextField3);
		userTextField4Num = Double.parseDouble(userTextField4);
		userTextField6Num = Double.parseDouble(annualInterestRatesBox.getValue().toString()) / 100;	// Calculate annual interest rate according to the n.nnn% input
		userTextField7Num = Integer.parseInt(termInYearsBox.getValue().toString());
		userTextField8Num = Double.parseDouble(userTextField8);
	}

	//	Test if the string input in the TextField is plain number
	public static boolean isNumeric(String inputString) {
		try {
			Double.parseDouble(inputString);
		}
		catch(NumberFormatException | NullPointerException e) {
			return false;
		}
		return true;
	}
}
