package sample;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimulatorMenuBar extends MenuBar {

    private final Menu settingsMenu;

    public SimulatorMenuBar(Stage2 stage2, Scene mScene, BorderPane bpMain, Simulator simulator){
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

            BorderPane bpBtn = new BorderPane();
            bpBtn.setLeft(btn1);
            bpBtn.setCenter(btn2);
            BorderPane.setMargin(btn1, new Insets(3, 3, 3, 3));

            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(wAndL, nbox, ibox, hboxspeed, hboxangle, hboxdir, hboxitime, hboxiD, bpBtn);

            Scene s = new Scene(vbox, 450, 370);
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

                stage2.comuna.refreshView();
                stage2.comuna = new Comuna();

                stage2.comuna.view = new ComunaView(stage2.comuna);

                stage2.simulator = new Simulator(stage2, stage2.mScene, 15, 1, stage2.comuna, SimulatorConfig.DELTA_T, (int)SimulatorConfig.N, (int)SimulatorConfig.I, (int)SimulatorConfig.SPEED, (int)SimulatorConfig.DELTA_THETA, (int)SimulatorConfig.I_TIME, (int)SimulatorConfig.D);
                stage2.simulator.simulationTime = 0;

                stage2.borderPane.setTop(new SimulatorMenuBar(stage2, mScene, stage2.borderPane, stage2.simulator));


                stage2.p.getChildren().add(stage2.comuna.getView());


                stage2.mScene.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.LEFT){
                        if (stage2.simulator.rate == 1){
                            stage2.simulator.rate = 0.5;
                            stage2.simulator.detectAnimationRate();
                        }
                        if (stage2.simulator.rate == 2){
                            stage2.simulator.rate = 1;
                            stage2.simulator.detectAnimationRate();
                        }
                        if (stage2.simulator.rate == 4){
                            stage2.simulator.rate = 2;
                            stage2.simulator.detectAnimationRate();
                        }
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT){
                        if (stage2.simulator.rate == 2){
                            stage2.simulator.rate = 4;
                            stage2.simulator.detectAnimationRate();
                        }
                        if (stage2.simulator.rate == 1){
                            stage2.simulator.rate = 2;
                            stage2.simulator.detectAnimationRate();
                        }
                        if (stage2.simulator.rate == 0.5){
                            stage2.simulator.rate = 1;
                            stage2.simulator.detectAnimationRate();
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
