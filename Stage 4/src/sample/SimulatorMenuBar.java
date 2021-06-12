package sample;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
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

    public SimulatorMenuBar(Stage4 stage4, Scene mScene, BorderPane bpMain, Simulator simulator, boolean mute){
        Menu controlMenu = new Menu("Control");
        getMenus().add(controlMenu);
        settingsMenu = new Menu("Settings");
        getMenus().add(settingsMenu);

        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start, stop);

        MenuItem settings = new MenuItem("Parameters");
        settingsMenu.getItems().add(settings);

        start.setOnAction(e->{simulator.start(this);});
        stop.setOnAction(e->{simulator.stop(this);});

        settingsMenu.setOnAction(e -> {
            Stage settingStage = new Stage();
            settingStage.setTitle("Settings");

            BorderPane bp = new BorderPane();

            Button btn1 = new Button("Back");
            Button btn2 = new Button("Submit Parameters");

            Label title = new Label("Settings");
            title.setFont(new Font("Cambria", 30));
            //title.setPrefSize(50, 50);

            ToggleButton toggleBtn = new ToggleButton("Mute");

            Label label7 = new Label("Width:");
            TextField _WIDTH = new TextField(String.valueOf(SimulatorConfig.WIDTH));
            Label label8 = new Label("Length:");
            TextField _LENGTH = new TextField(String.valueOf(SimulatorConfig.LENGTH));
            HBox wAndL = new HBox(10);
            wAndL.getChildren().addAll(label7, _WIDTH, label8, _LENGTH, toggleBtn);
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

            Label label13 = new Label("N° of Vaccination Centres:");
            Slider _NUM_VAC = new Slider(0, 50, SimulatorConfig.NUM_VAC);
            _NUM_VAC.setPrefWidth(200);
            _NUM_VAC.setMajorTickUnit(10);
            _NUM_VAC.setMinorTickCount(1);
            _NUM_VAC.setSnapToTicks(true);
            _NUM_VAC.setShowTickMarks(true);
            _NUM_VAC.setShowTickLabels(true);
            HBox hboxNUMVAC = new HBox(10);
            hboxNUMVAC.getChildren().addAll(label13, _NUM_VAC);
            hboxNUMVAC.setAlignment(Pos.CENTER);

            Label label14 = new Label("Size of Vaccination Centres:");
            Slider _VAC_SIZE = new Slider(0, 70, SimulatorConfig.VAC_SIZE);
            _VAC_SIZE.setPrefWidth(200);
            _VAC_SIZE.setMajorTickUnit(10);
            _VAC_SIZE.setMinorTickCount(5);
            _VAC_SIZE.setSnapToTicks(true);
            _VAC_SIZE.setShowTickMarks(true);
            _VAC_SIZE.setShowTickLabels(true);
            HBox hboxVACSIZE = new HBox(10);
            hboxVACSIZE.getChildren().addAll(label14, _VAC_SIZE);
            hboxVACSIZE.setAlignment(Pos.CENTER);

            Label label15 = new Label("Time of Vaccination Initialization:");
            Slider _VAC_TIME = new Slider(0, 100, SimulatorConfig.VAC_TIME);
            _VAC_TIME.setPrefWidth(200);
            _VAC_TIME.setMajorTickUnit(10);
            _VAC_TIME.setMinorTickCount(10);
            _VAC_TIME.setSnapToTicks(true);
            _VAC_TIME.setShowTickMarks(true);
            _VAC_TIME.setShowTickLabels(true);
            HBox hboxVACTIME = new HBox(10);
            hboxVACTIME.getChildren().addAll(label15, _VAC_TIME);
            hboxVACTIME.setAlignment(Pos.CENTER);

            BorderPane bpBtn = new BorderPane();
            bpBtn.setLeft(btn1);
            bpBtn.setCenter(btn2);
            BorderPane.setMargin(btn1, new Insets(3, 3, 3, 3));

            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(title, wAndL, nbox, ibox, hboxspeed, hboxangle, hboxdir, hboxitime, hboxiD, hboxM, hboxp0, hboxp1, hboxp2, hboxNUMVAC, hboxVACSIZE, hboxVACTIME, bpBtn);

            Scene s = new Scene(vbox, 480, 750);
            settingStage.setScene(s);
            settingStage.setResizable(false);
            settingStage.show();

            bpMain.disableProperty().bind(settingStage.showingProperty());

            btn1.setOnAction(g -> {
                settingStage.hide();
            });

            btn2.disableProperty().bind(Bindings.isEmpty(_I_TIME.textProperty()).and(Bindings.isEmpty(_D.textProperty())));

            btn2.setOnAction(f -> {

                boolean mute1 = toggleBtn.isSelected();

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
                SimulatorConfig.NUM_VAC = _NUM_VAC.getValue();
                SimulatorConfig.VAC_SIZE = _VAC_SIZE.getValue();
                SimulatorConfig.VAC_TIME = _VAC_TIME.getValue();

                stage4.root.getChildren().remove(stage4.graph.stack);

                stage4.graph.Susceptibles.getData().clear();
                stage4.graph.Infected.getData().clear();
                stage4.graph.Recovered.getData().clear();

                stage4.graph.stack.getData().clear();

                stage4.comuna.refreshView();
                stage4.comuna = new Comuna(stage4.file, mute);

                stage4.comuna.view = new ComunaView(stage4.comuna);

                stage4.graph = new Graph(stage4.comuna);

                stage4.splitPane.getItems().removeAll(stage4.p, stage4.root);

                stage4.root = new Group();
                stage4.root.getChildren().add(stage4.graph.stack);

                stage4.simulator = new Simulator(stage4, stage4.mScene, 15, 1, stage4.graph, stage4.comuna, SimulatorConfig.DELTA_T, (int)SimulatorConfig.N, (int)SimulatorConfig.I, SimulatorConfig.SPEED, SimulatorConfig.DELTA_THETA, SimulatorConfig.I_TIME, SimulatorConfig.D, SimulatorConfig.M, SimulatorConfig.P0, SimulatorConfig.P1, SimulatorConfig.P2);
                stage4.simulator.simulationTime = 0;

                stage4.simulator.vacss = new VaccinationCentre(stage4.comuna);

                stage4.borderPane.setTop(new SimulatorMenuBar(stage4, mScene, stage4.borderPane, stage4.simulator, mute1));

                stage4.p.setCenter(stage4.comuna.getView());
                stage4.splitPane.getItems().addAll(stage4.p, stage4.root);

                stage4.borderPane.setCenter(stage4.splitPane);

                stage4.mScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
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
                    }
                });

                settingStage.hide();
                hideSettings();
            });
        });
    }

    public void hideSettings(){
        settingsMenu.setDisable(true);
    }

    public void showSettings(){
        settingsMenu.setDisable(false);
    }
}
