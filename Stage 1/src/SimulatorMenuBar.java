import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class SimulatorMenuBar extends MenuBar {

    /*
    Constructor del menu del simulador, con sus respectivos items pedidos.
    */
    public SimulatorMenuBar (Simulator simulator){
        Menu controlMenu = new Menu("Control");
        getMenus().add(controlMenu);
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start, stop);
        start.setOnAction(e->{simulator.start();});
        stop.setOnAction(e->{simulator.stop();});
    }
}
