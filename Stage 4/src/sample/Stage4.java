package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Stage4 extends Application {

    public Scene mScene;
    public Simulator simulator;
    public Comuna comuna;
    public BorderPane p;
    public BorderPane borderPane;
    public SplitPane splitPane;
    public Stage _primaryStage;
    public Graph graph;
    public Group root;
    public String file;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parameters param = getParameters();
        List<String> rawParam = param.getRaw();
        if (rawParam.size() != 2) {
            System.out.println("Usage: java Stage1 <configurationFile.txt> <sound.mp3>");
            System.exit(-1);
        }
        _primaryStage = primaryStage;
        SimulatorConfig config = new SimulatorConfig(new Scanner(new File(rawParam.get(0))));
        file = rawParam.get(1);
        System.out.println(file);
        _primaryStage.setTitle("Pandemic Graphics Simulator");
        _primaryStage.getIcons().add(new Image("file:icon.png"));
        borderPane = new BorderPane();

        mScene = new Scene(borderPane, 800, 900);

        _primaryStage.setScene(mScene);

        comuna = new Comuna(file, false);
        graph = new Graph(comuna);
        simulator = new Simulator(this, mScene, 15,1, graph, comuna, SimulatorConfig.DELTA_T, (int)SimulatorConfig.N, (int)SimulatorConfig.I, SimulatorConfig.SPEED, SimulatorConfig.DELTA_THETA, SimulatorConfig.I_TIME, SimulatorConfig.D, SimulatorConfig.M, SimulatorConfig.P0, SimulatorConfig.P1, SimulatorConfig.P2);

        borderPane.setTop(new SimulatorMenuBar(this, mScene, borderPane, simulator, false));

        splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        mScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT){
                if (simulator.rate == 1){
                    simulator.rate = 0.5;
                    simulator.detectAnimationRate();
                }
                if (simulator.rate == 2){
                    simulator.rate = 1;
                    simulator.detectAnimationRate();
                }
                if (simulator.rate == 4){
                    simulator.rate = 2;
                    simulator.detectAnimationRate();
                }
            }
            if (keyEvent.getCode() == KeyCode.RIGHT){
                if (simulator.rate == 2){
                    simulator.rate = 4;
                    simulator.detectAnimationRate();
                }
                if (simulator.rate == 1){
                    simulator.rate = 2;
                    simulator.detectAnimationRate();
                }
                if (simulator.rate == 0.5){
                    simulator.rate = 1;
                    simulator.detectAnimationRate();
                }
            }
        });

        root = new Group();
        root.getChildren().add(graph.stack);

        p = new BorderPane();
        p.setCenter(comuna.getView());

        splitPane.getItems().addAll(p, root);

        borderPane.setCenter(splitPane);
        _primaryStage.show();

    }
}
