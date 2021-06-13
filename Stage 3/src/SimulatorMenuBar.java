import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SimulatorMenuBar extends MenuBar {

    private final Menu settingsMenu;

    /*
    creacion del menu del simulador, con sus respectivos items pedidos.
    */
    public SimulatorMenuBar(Stage3 stage4, Scene mScene, BorderPane bpMain, Simulator simulator){
        /*
        items del menu
        */
        Menu controlMenu = new Menu("Control");
        getMenus().add(controlMenu);
        settingsMenu = new Menu("Settings");
        getMenus().add(settingsMenu);
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start, stop);
        MenuItem settings = new MenuItem("Parameters");
        settingsMenu.getItems().add(settings);

        /*
        setters de acciones del simualdor
        */
        start.setOnAction(e->{simulator.start(this);});
        stop.setOnAction(e->{simulator.stop(this);});

        /*
        setters de acciones del menu del simualdor, para la ventana aparte de condiguracion
        de los parametros con los que se haran las simulaciones.
        los botones usados especifican cual apartado sera cambiado, realizando asi una configuracion
        a gusto del consumidor, colocando los parametros que el requiera utilizar.
        tambien tiene la forma y el como sera cada botton especificado en la ventana, de esta forma
        este subconjunto de bottones le daran forma a los resultados de los experimentos para modelar la pandemia
        segun los parametros que ingrese el usuario.
        */
        settingsMenu.setOnAction(e -> {
            Stage settingStage = new Stage();
            settingStage.setTitle("Settings");

            Button btn1 = new Button("Back");
            Button btn2 = new Button("Submit Parameters");

            Label title = new Label("Settings");
            title.setFont(new Font("Cambria", 30));

            Label label7 = new Label("Width:");
            TextField _WIDTH = new TextField(String.valueOf(SimulatorConfig.WIDTH));
            Label label8 = new Label("Length:");
            TextField _LENGTH = new TextField(String.valueOf(SimulatorConfig.LENGTH));
            HBox wAndL = new HBox(10);
            wAndL.getChildren().addAll(label7, _WIDTH, label8, _LENGTH);
            wAndL.setAlignment(Pos.CENTER);

            Label label0 = new Label("Number of individuals:");
            Spinner _N = new Spinner(1, ((SimulatorConfig.LENGTH * SimulatorConfig.WIDTH) / 25), SimulatorConfig.N);
            _N.setEditable(true);
            _N.setPrefSize(75, 25);
            HBox nbox = new HBox(10);
            nbox.getChildren().addAll(label0, _N);
            nbox.setAlignment(Pos.CENTER);

            Label label1 = new Label("Number of infected:");
            Spinner _I = new Spinner(1, ((double) _N.getValue()), SimulatorConfig.I);
            _I.setEditable(true);
            _I.setPrefSize(75, 25);
            HBox ibox = new HBox(10);
            ibox.getChildren().addAll(label1, _I);
            ibox.setAlignment(Pos.CENTER);

            Label label2 = new Label("Speed:");
            Slider _SPEED = new Slider(0, 10, SimulatorConfig.SPEED);
            _SPEED.setPrefWidth(200);
            _SPEED.setMajorTickUnit(1);
            _SPEED.setMinorTickCount(5);
            _SPEED.setSnapToTicks(true);
            _SPEED.setShowTickMarks(true);
            _SPEED.setShowTickLabels(true);
            HBox hboxspeed = new HBox(10);
            hboxspeed.getChildren().addAll(label2, _SPEED);
            hboxspeed.setAlignment(Pos.CENTER);

            Label label3 = new Label("Direction variation range (Delta_Theta):");
            Slider _DELTA_THETA = new Slider(0, 1, SimulatorConfig.DELTA_THETA);
            _DELTA_THETA.setPrefWidth(200);
            _DELTA_THETA.setMajorTickUnit(0.1);
            _DELTA_THETA.setMinorTickCount(1);
            _DELTA_THETA.setSnapToTicks(true);
            _DELTA_THETA.setShowTickMarks(true);
            _DELTA_THETA.setShowTickLabels(true);
            HBox hboxangle = new HBox(10);
            hboxangle.getChildren().addAll(label3, _DELTA_THETA);
            hboxangle.setAlignment(Pos.CENTER);

            Label label6 = new Label("Direction variation rate (Delta_T):");
            Spinner _DELTA_T = new Spinner(0.1, 1, SimulatorConfig.DELTA_T);
            _DELTA_T.setEditable(true);
            _DELTA_T.setPrefSize(75, 25);
            HBox hboxdir = new HBox(10);
            hboxdir.getChildren().addAll(label6, _DELTA_T);
            hboxdir.setAlignment(Pos.CENTER);

            Label label4 = new Label("Recovering starts within:");
            TextField _I_TIME = new TextField(String.valueOf(SimulatorConfig.I_TIME));
            HBox hboxitime = new HBox(10);
            hboxitime.getChildren().addAll(label4, _I_TIME);
            hboxitime.setAlignment(Pos.CENTER);

            Label label5 = new Label("Distance of Infection:");
            TextField _D = new TextField(String.valueOf(SimulatorConfig.D));
            HBox hboxiD = new HBox(10);
            hboxiD.getChildren().addAll(label5, _D);
            hboxiD.setAlignment(Pos.CENTER);

            Label label9 = new Label("Mask usage percentage:");
            TextField _M = new TextField(String.valueOf(SimulatorConfig.M));
            HBox hboxM = new HBox(10);
            hboxM.getChildren().addAll(label9, _M);
            hboxM.setAlignment(Pos.CENTER);

            Label label10 = new Label("Infection probability both");
            Label label10_1 = new Label("wearing masks (P0):");
            VBox a = new VBox();
            a.setAlignment(Pos.CENTER);
            a.getChildren().addAll(label10, label10_1);
            Slider _P0 = new Slider(0, 1, SimulatorConfig.P0);
            _P0.setPrefWidth(200);
            _P0.setMajorTickUnit(0.1);
            _P0.setMinorTickCount(1);
            _P0.setSnapToTicks(true);
            _P0.setShowTickMarks(true);
            _P0.setShowTickLabels(true);
            HBox hboxp0 = new HBox(10);

            hboxp0.getChildren().addAll(a, _P0);
            hboxp0.setAlignment(Pos.CENTER);

            Label label11 = new Label("Infection probability");
            Label label11_1 = new Label("one wearing masks (P1):");
            VBox b = new VBox();
            b.setAlignment(Pos.CENTER);
            b.getChildren().addAll(label11, label11_1);
            Slider _P1 = new Slider(0, 1, SimulatorConfig.P1);
            _P1.setPrefWidth(200);
            _P1.setMajorTickUnit(0.1);
            _P1.setMinorTickCount(1);
            _P1.setSnapToTicks(true);
            _P1.setShowTickMarks(true);
            _P1.setShowTickLabels(true);
            HBox hboxp1 = new HBox(10);
            hboxp1.getChildren().addAll(b, _P1);
            hboxp1.setAlignment(Pos.CENTER);

            Label label12 = new Label("Infection probability");
            Label label12_1 = new Label("no one wearing masks (P2):");
            VBox c = new VBox();
            c.setAlignment(Pos.CENTER);
            c.getChildren().addAll(label12, label12_1);
            Slider _P2 = new Slider(0, 1, SimulatorConfig.P2);
            _P2.setPrefWidth(200);
            _P2.setMajorTickUnit(0.1);
            _P2.setMinorTickCount(1);
            _P2.setSnapToTicks(true);
            _P2.setShowTickMarks(true);
            _P2.setShowTickLabels(true);
            HBox hboxp2 = new HBox(10);
            hboxp2.getChildren().addAll(c, _P2);
            hboxp2.setAlignment(Pos.CENTER);

            BorderPane bpBtn = new BorderPane();
            bpBtn.setLeft(btn1);
            bpBtn.setCenter(btn2);
            BorderPane.setMargin(btn1, new Insets(3, 3, 3, 3));

            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(title, wAndL, nbox, ibox, hboxspeed, hboxangle, hboxdir, hboxitime, hboxiD, hboxM, hboxp0, hboxp1, hboxp2, bpBtn);

            Scene s = new Scene(vbox, 480, 600);
            settingStage.setScene(s);
            settingStage.setResizable(false);
            settingStage.show();

            bpMain.disableProperty().bind(settingStage.showingProperty());

            btn1.setOnAction(g -> {
                settingStage.hide();
            });

            btn2.disableProperty().bind(Bindings.isEmpty(_I_TIME.textProperty()).and(Bindings.isEmpty(_D.textProperty())));

            btn2.setOnAction(f -> {


                SimulatorConfig.N = (double) _N.getValue();

                SimulatorConfig.I = (double) _I.getValue();

                SimulatorConfig.DELTA_T = (double) _DELTA_T.getValue();

                if (_I_TIME.getText() == null || _I_TIME.getText().trim().isEmpty()){
                    _I_TIME.setText(String.valueOf(SimulatorConfig.I_TIME));
                }
                SimulatorConfig.I_TIME = Double.parseDouble(_I_TIME.getText());

                if (_WIDTH.getText() == null || _WIDTH.getText().trim().isEmpty()){
                    _WIDTH.setText(String.valueOf(SimulatorConfig.WIDTH));
                }
                SimulatorConfig.WIDTH = Double.parseDouble(_WIDTH.getText());

                if (_LENGTH.getText() == null || _LENGTH.getText().trim().isEmpty()){
                    _LENGTH.setText(String.valueOf(SimulatorConfig.LENGTH));
                }
                SimulatorConfig.LENGTH = Double.parseDouble(_LENGTH.getText());

                SimulatorConfig.SPEED = _SPEED.getValue();

                SimulatorConfig.DELTA_THETA = _DELTA_THETA.getValue();

                if (_D.getText() == null || _D.getText().trim().isEmpty()){
                    _D.setText(String.valueOf(SimulatorConfig.D));
                }
                SimulatorConfig.D = Double.parseDouble(_D.getText());

                stage4.graph.Susceptibles.getData().clear();
                stage4.graph.Infected.getData().clear();
                stage4.graph.Recovered.getData().clear();

                stage4.comuna.refreshView();
                stage4.comuna = new Comuna();

                stage4.comuna.view = new ComunaView(stage4.comuna);

                stage4.graph = new Graph(stage4.comuna);

                stage4.simulator = new Simulator(stage4, stage4.mScene, 15, 1, stage4.graph, stage4.comuna, SimulatorConfig.DELTA_T, (int)SimulatorConfig.N, (int)SimulatorConfig.I, SimulatorConfig.SPEED, SimulatorConfig.DELTA_THETA, SimulatorConfig.I_TIME, SimulatorConfig.D, SimulatorConfig.M, SimulatorConfig.P0, SimulatorConfig.P1, SimulatorConfig.P2);
                stage4.simulator.simulationTime = 0;

                stage4.root.getChildren().add(stage4.graph.stack);

                stage4.borderPane.setTop(new SimulatorMenuBar(stage4, mScene, stage4.borderPane, stage4.simulator));

                stage4.p.setCenter(stage4.comuna.getView());

                stage4.mScene.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.LEFT){
                        if (stage4.simulator.rate == 1){
                            stage4.simulator.rate = 0.5;
                            stage4.simulator.detectAnimationRate();
                        }
                        if (stage4.simulator.rate == 2){
                            stage4.simulator.rate = 1;
                            stage4.simulator.detectAnimationRate();
                        }
                        if (stage4.simulator.rate == 4){
                            stage4.simulator.rate = 2;
                            stage4.simulator.detectAnimationRate();
                        }
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT){
                        if (stage4.simulator.rate == 2){
                            stage4.simulator.rate = 4;
                            stage4.simulator.detectAnimationRate();
                        }
                        if (stage4.simulator.rate == 1){
                            stage4.simulator.rate = 2;
                            stage4.simulator.detectAnimationRate();
                        }
                        if (stage4.simulator.rate == 0.5){
                            stage4.simulator.rate = 1;
                            stage4.simulator.detectAnimationRate();
                        }
                    }
                });

                settingStage.hide();
                hideSettings();
            });
        });
    }
    /*
    esconder los settings
    */
    public void hideSettings(){
        settingsMenu.setDisable(true);
    }
    /*
    mostrar los settings
    */
    public void showSettings(){
        settingsMenu.setDisable(false);
    }
}
