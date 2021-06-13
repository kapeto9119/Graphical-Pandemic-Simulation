
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Stage2 extends Application {
    /*
    variables
     */
    public Scene mScene;
    public Simulator simulator;
    public Comuna comuna;
    public StackPane p;
    public BorderPane borderPane;
    public Stage _primaryStage;

    /*
    main del programa
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*
    start para javaFX
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        realizar un buen ajuste del dato ingresado "file.txt", en caso de fallar se hace un print que muestra un mensaje de error en uso.
         */
        Parameters param = getParameters();
        List<String> rawParam = param.getRaw();
        if (rawParam.size() != 1) {
            System.out.println("Usage: java Stage1 <configurationFile.txt>");
            System.exit(-1);
        }
        /*
        Ajuste y llamado de la interfaz para mostrarla por pantalla, usando metodos y creando o inicializando objetos
        para cumplir con los requerimientos necesarios segun el como se estructura el menu de la aplicacion pedida.
        Se usan los metodos de las bibliotecas de JavaFx.
         */
        _primaryStage = primaryStage;
        SimulatorConfig config = new SimulatorConfig(new Scanner(new File(rawParam.get(0))));
        _primaryStage.setTitle("Pandemic Graphics Simulator");
        borderPane = new BorderPane();
        mScene = new Scene(borderPane, SimulatorConfig.WIDTH + 50, SimulatorConfig.LENGTH + 50);

        _primaryStage.setScene(mScene);
        primaryStage.getIcons().add(new Image("file:icon.png"));

        comuna = new Comuna();
        simulator = new Simulator(this, mScene, 15,1, comuna, SimulatorConfig.DELTA_T, (int)SimulatorConfig.N, (int)SimulatorConfig.I, SimulatorConfig.SPEED, SimulatorConfig.DELTA_THETA, SimulatorConfig.I_TIME, SimulatorConfig.D);
        borderPane.setTop(new SimulatorMenuBar(this, mScene, borderPane, simulator));

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

        p = new StackPane();
        p.getChildren().add(comuna.getView());
        StackPane.setAlignment(comuna.getView(), Pos.CENTER);

        borderPane.setCenter(p);
        _primaryStage.show();
    }
}