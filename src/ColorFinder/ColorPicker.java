/*
 * Sapayth Hossain
 */
package ColorFinder;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * @author sapaythhossain
 */
public class ColorPicker extends Application {

    private Stage window;
    private Scene scene;
    private double width;
    private double height;
    private String title;
    private Label colorLabel;
    private Label rgbLabel;
    private Label htmlLabel;
    private Label rValueLabel;
    private Label htmlValueLabel;
    private Label bValueLabel;

    private int RGBr;
    private int RGBg;
    private int RGBb;

    private Color currentColor;
    private Robot robot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        title = "Color Picker";
        width = 220;
        height = 80;
        
        robot = new Robot();

        window = primaryStage;
        window.setTitle(title);

        colorLabel = new Label();
        colorLabel.setWrapText(true);
        colorLabel.setMinSize(50, 50);
        colorLabel.setStyle(
                "-fx-background-color: rgba(" + RGBr + "," + RGBg + "," + RGBb + ",1);"
            );

        VBox leftRow = new VBox(10);
        leftRow.getChildren().addAll(colorLabel);

        rgbLabel = new Label("RGB: ");
        htmlLabel = new Label("HTML: ");

        rValueLabel = new Label("");
        htmlValueLabel = new Label("");
        bValueLabel = new Label();

        VBox middleRow = new VBox();
        middleRow.getChildren().addAll(rgbLabel, htmlLabel);

        VBox rightRow = new VBox();
        rightRow.getChildren().addAll(rValueLabel, htmlValueLabel, bValueLabel);

        HBox layout = new HBox(10);
        layout.getChildren().addAll(leftRow, middleRow, rightRow);

        EventHandler handler = (EventHandler) (Event event) -> {
            changeColor();
        };

        scene = new Scene(layout, width, height);
        scene.setOnMouseMoved(handler);
        scene.setOnKeyTyped(handler);
        
        
        
        window.setScene(scene);
        window.show();
    }

    public void changeColor() {
        Point p = MouseInfo.getPointerInfo().getLocation();
        currentColor = robot.getPixelColor(p.x, p.y);

        RGBr = currentColor.getRed();
        RGBg = currentColor.getGreen();
        RGBb = currentColor.getBlue();

        String colorString = String.valueOf(RGBr) + ", "
                + String.valueOf(RGBg) + ", " + String.valueOf(RGBb);
        
        colorLabel.setStyle(
                "-fx-background-color: rgba(" + RGBr + "," + RGBg + "," + RGBb + ",1);"
            );
        String hex = String.format("#%02x%02x%02x", RGBr, RGBg, RGBb);
        htmlValueLabel.setText(hex);
        rValueLabel.setText(colorString);
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}
