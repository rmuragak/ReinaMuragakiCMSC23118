package assignment4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.InputStream;

public class MgmCompanyGui extends Application {

    private TextField nameField, taxIdField, feeField;
    private TextField propertyNameField, cityField, rentField, ownerField, plotXField, plotYField, plotWidthField, plotDepthField, imageNameField;
    private TextArea outputArea;
    private ImageView logoView;

    private ManagementCompany managementCompany;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Property Management App");

        // === Load logo image ===
        try {
            Image logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
            logoView = new ImageView(logo);
            logoView.setFitWidth(150);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Logo image not found!");
            logoView = new ImageView(); // empty placeholder
        }

        // === Management Company Section ===
        Label companyLabel = new Label("Management Company Information");
        nameField = new TextField();
        taxIdField = new TextField();
        feeField = new TextField();
        nameField.setPromptText("Company Name");
        taxIdField.setPromptText("Tax ID");
        feeField.setPromptText("Management Fee %");

        Button createCompanyButton = new Button("New Management Company");
        createCompanyButton.setOnAction(e -> createNewMgm());

        VBox companyBox = new VBox(10, companyLabel, nameField, taxIdField, feeField, createCompanyButton);
        companyBox.setAlignment(Pos.CENTER_LEFT);
        companyBox.setPadding(new Insets(10));
        companyBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5;");

        // === Property Section ===
        Label propertyLabel = new Label("Property Information");
        propertyNameField = new TextField();
        cityField = new TextField();
        rentField = new TextField();
        ownerField = new TextField();
        plotXField = new TextField();
        plotYField = new TextField();
        plotWidthField = new TextField();
        plotDepthField = new TextField();
        imageNameField = new TextField(); // NEW field for image name

        propertyNameField.setPromptText("Property Name");
        cityField.setPromptText("City");
        rentField.setPromptText("Rent Amount");
        ownerField.setPromptText("Owner Name");
        plotXField.setPromptText("Plot X Value");
        plotYField.setPromptText("Plot Y Value");
        plotWidthField.setPromptText("Plot Width");
        plotDepthField.setPromptText("Plot Depth");
        imageNameField.setPromptText("Image File Name (lowercase)");

        setPropertyTextFields();

        Button addPropertyButton = new Button("Add Property");
        addPropertyButton.setOnAction(e -> addProperty());

        VBox propertyBox = new VBox(10, propertyLabel, propertyNameField, cityField, rentField, ownerField,
                plotXField, plotYField, plotWidthField, plotDepthField, imageNameField, addPropertyButton);
        propertyBox.setAlignment(Pos.CENTER_LEFT);
        propertyBox.setPadding(new Insets(10));
        propertyBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5;");

        // === Output Section ===
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(250);
        outputArea.setWrapText(true);

        Button displayButton = new Button("Display All Properties");
        displayButton.setOnAction(e -> displayProperties());

        VBox outputBox = new VBox(10, displayButton, outputArea);
        outputBox.setAlignment(Pos.CENTER_LEFT);
        outputBox.setPadding(new Insets(10));

        // === Main Layout ===
        VBox root = new VBox(20, logoView, companyBox, propertyBox, outputBox);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 550, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createNewMgm() {
        try {
            String name = nameField.getText();
            String taxId = taxIdField.getText();
            double fee = Double.parseDouble(feeField.getText());

            managementCompany = new ManagementCompany(name, taxId, fee);
            outputArea.setText("New Management Company created:\n" + managementCompany.getName());
        } catch (Exception e) {
            outputArea.setText("Error creating Management Company. Please check your inputs.");
        }
    }

    private void addProperty() {
        if (managementCompany == null) {
            outputArea.setText("Please create a Management Company first.");
            return;
        }

        try {
            String propertyName = propertyNameField.getText();
            String city = cityField.getText();
            double rent = Double.parseDouble(rentField.getText());
            String owner = ownerField.getText();
            String imageName = imageNameField.getText();

            int x = Integer.parseInt(plotXField.getText());
            int y = Integer.parseInt(plotYField.getText());
            int width = Integer.parseInt(plotWidthField.getText());
            int depth = Integer.parseInt(plotDepthField.getText());

            Property property = new Property(propertyName, city, rent, owner,
                    new Plot(x, y, width, depth), imageName);
            int result = managementCompany.addProperty(property);

            switch (result) {
                case -1:
                    outputArea.setText("Error: Too many properties.");
                    break;
                case -2:
                    outputArea.setText("Error: Property is null.");
                    break;
                case -3:
                    outputArea.setText("Error: Property plot not encompassed by management plot.");
                    break;
                case -4:
                    outputArea.setText("Error: Property plot overlaps another property.");
                    break;
                default:
                    outputArea.setText("Property added successfully!\n" + property.toString());
            }
        } catch (Exception e) {
            outputArea.setText("Invalid property information. Please fill all fields correctly.");
        }
    }

    private void displayProperties() {
        if (managementCompany == null) {
            outputArea.setText("No Management Company created yet.");
            return;
        }

        VBox propertiesBox = new VBox(10);
        propertiesBox.setPadding(new Insets(10));

        for (Property property : managementCompany.getProperties()) {
            Label infoLabel = new Label(property.toString());
            VBox propertyVBox = new VBox(5, infoLabel);

            // Load image safely
            if (property.getImageName() != null && !property.getImageName().isEmpty()) {
                try {
                    InputStream stream = getClass().getResourceAsStream("/images/" + property.getImageName());
                    if (stream != null) {
                        Image img = new Image(stream);
                        ImageView imgView = new ImageView(img);
                        imgView.setFitWidth(200);
                        imgView.setPreserveRatio(true);
                        propertyVBox.getChildren().add(imgView);
                    } else {
                        System.out.println("Image not found for: " + property.getPropertyName());
                    }
                } catch (Exception e) {
                    System.out.println("Failed to load image: " + property.getImageName());
                }
            }

            propertiesBox.getChildren().add(propertyVBox);
        }

        Stage imageStage = new Stage();
        imageStage.setTitle("Property Images");
        Scene scene = new Scene(new ScrollPane(propertiesBox), 450, 400);
        imageStage.setScene(scene);
        imageStage.show();
    }

    private void setPropertyTextFields() {
        propertyNameField.setDisable(false);
        cityField.setDisable(false);
        rentField.setDisable(false);
        ownerField.setDisable(false);
        plotXField.setDisable(false);
        plotYField.setDisable(false);
        plotWidthField.setDisable(false);
        plotDepthField.setDisable(false);
        imageNameField.setDisable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
