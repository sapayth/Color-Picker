/*
 * Sapayth Hossain
 */
package ColorFinder;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

/*
 * @author sapaythhossain
 */
public class ColorPicker extends Application implements NativeMouseMotionListener {

    private Stage window;
    private Scene scene;
    private double width;
    private double height;
    private String title;
    private Label colorLabel;
    private Label rgbLabel;
    private Label htmlLabel;
    private Label rgbValueLabel;
    private Label htmlValueLabel;
    private Label bValueLabel;

    private int RGBr;
    private int RGBg;
    private int RGBb;

    private Color currentColor;
    private Robot robot;

    @Override
    public void start(Stage primaryStage) throws Exception {

        createHook();   // create jnativehook to detact whole screen

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

        // color preview
        VBox leftRow = new VBox(10);
        leftRow.setPadding(new Insets(5, 5, 5, 5));
        leftRow.getChildren().addAll(colorLabel);

        rgbLabel = new Label("RGB: ");
        htmlLabel = new Label("HTML: ");

        rgbValueLabel = new Label("");
        htmlValueLabel = new Label("");
        bValueLabel = new Label();

        // labels
        VBox middleRow = new VBox();
        middleRow.setPadding(new Insets(5, 0, 0, 0));
        middleRow.getChildren().addAll(rgbLabel, htmlLabel);

        // RGB and hex values
        VBox rightRow = new VBox();
        rightRow.setPadding(new Insets(5, 0, 0, 0));
        rightRow.getChildren().addAll(rgbValueLabel, htmlValueLabel, bValueLabel);

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

        Platform.setImplicitExit(true);
        window.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public void changeColor() {
        Point p = MouseInfo.getPointerInfo().getLocation();
        currentColor = robot.getPixelColor(p.x, p.y);
        RGBr = currentColor.getRed();
        RGBg = currentColor.getGreen();
        RGBb = currentColor.getBlue();

        String colorString = RGBr + ", " + RGBg + ", " + RGBb;
        colorLabel.setStyle(
                "-fx-background-color: rgba(" + RGBr + "," + RGBg + "," + RGBb + ",1);"
        );
        String hex = String.format("#%02x%02x%02x", RGBr, RGBg, RGBb);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                htmlValueLabel.setText(hex.toUpperCase());
                rgbValueLabel.setText(colorString);
            }
        });
    }

    private void createHook() {
        clearLog();
        try {
            //starts the hook
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {

        }
        // add the listener for the hook
        GlobalScreen.addNativeMouseMotionListener(this);
    }

    // Disable all console output
    public void clearLog() {
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nme) {
        changeColor();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nme) {}

    public static void main(String[] args) {
        launch(args);
    }
}