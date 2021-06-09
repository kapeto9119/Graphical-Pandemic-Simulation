package sample;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SimulatorMenuBar extends MenuBar {

    private Menu controlMenu;
    private Menu settingsMenu;

    public SimulatorMenuBar(Stage primaryStage, Simulator simulator){
        controlMenu = new Menu("Control");
        getMenus().add(controlMenu);
        settingsMenu = new Menu("Settings");
        getMenus().add(settingsMenu);

//        if (simulator.isPlaying()){
//            settingsMenu.setDisable(true);
//        }

        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start, stop);

        start.setOnAction(e->{simulator.start(this);});
        stop.setOnAction(e->{simulator.stop(this);});

    }

    public void hideSettings(){
        settingsMenu.setDisable(true);
    }

    public void showSettings(){
        settingsMenu.setDisable(false);
    }
}
