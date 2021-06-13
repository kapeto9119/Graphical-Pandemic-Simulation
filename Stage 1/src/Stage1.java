import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Stage1 extends Application {
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
        primaryStage.setTitle("Pandemic Graphics Simulator");
        BorderPane borderPane = new BorderPane();
        primaryStage.setScene(new Scene(borderPane, 700, 500));
        primaryStage.getIcons().add(new Image("file:icon.png"));
        SimulatorConfig config = new SimulatorConfig(new Scanner(new File(rawParam.get(0))));
        Comuna comuna = new Comuna();
        Simulator simulator = new Simulator(10,1,comuna);
        borderPane.setTop(new SimulatorMenuBar(simulator));
        StackPane p = new StackPane();
        p.getChildren().add(comuna.getView());
        StackPane.setAlignment(comuna.getView(), Pos.CENTER);
        borderPane.setCenter(p);
        primaryStage.show();
    }
}