package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class VaccinationCentre {

    public double x, y;
    Rectangle2D vaccinationCentre;
    Rectangle perimeter;
    Comuna comuna;
    private ArrayList<VaccinationCentre> vacs = new ArrayList<>();

    public VaccinationCentre(Comuna _comuna){

        comuna = _comuna;

        double Valor_max_X = SimulatorConfig.WIDTH - SimulatorConfig.VAC_SIZE;
        double Valor_min_X = 0;

        double Valor_max_Y = SimulatorConfig.LENGTH - SimulatorConfig.VAC_SIZE;
        double Valor_min_Y = 0;

        x = ((Math.random() * (Valor_max_X - Valor_min_X)) + Valor_min_X);
        y = ((Math.random() * (Valor_max_Y - Valor_min_Y)) + Valor_min_Y);

        vaccinationCentre = new Rectangle2D.Double(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);

        perimeter = new Rectangle(SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE, Color.LIGHTGREEN);
        perimeter.setX(x - (SimulatorConfig.VAC_SIZE / 2));
        perimeter.setY(y + (SimulatorConfig.VAC_SIZE / 2));

    }

    public void initVac(){
        for (int i = 0; i < SimulatorConfig.NUM_VAC; i++){
            VaccinationCentre vac = new VaccinationCentre(comuna);
            vacs.add(vac);

            for (int k = 0; k < vacs.size(); k++){
                for (int l = 0; l < vacs.size(); l++){
                    if (vacs.get(k) != vacs.get(l)){
                        while (vacs.get(k).vaccinationCentre.intersects(vacs.get(l).vaccinationCentre)){
                            vacs.get(k).updateRandomCoor();
                        }
                    }
                }
            }
            comuna.view.getChildren().add(vac.perimeter);
        }
    }

    public void detectVaccineCentre(ArrayList<Pedestrian> _PedestrianList){
        for (Pedestrian pedestrian : _PedestrianList) {
            for (VaccinationCentre vacunatorio : vacs) {
                if (vacunatorio.vaccinationCentre.contains(pedestrian.getX(), pedestrian.getY())) {
                    if (pedestrian.getStatus().equals("susceptible")) {
                        pedestrian.setStatus("vacunado");
                        comuna.Susceptible_Q -= 1;
                        comuna.Vaccinated_Q += 1;
                    }
                }
            }
        }
    }

    public void updateRandomCoor(){
        double Valor_max_X = SimulatorConfig.WIDTH - SimulatorConfig.VAC_SIZE;
        double Valor_min_X = 0;

        double Valor_max_Y = SimulatorConfig.LENGTH - SimulatorConfig.VAC_SIZE;
        double Valor_min_Y = 0;

        this.x = ((Math.random() * (Valor_max_X - Valor_min_X)) + Valor_min_X);
        this.y = ((Math.random() * (Valor_max_Y - Valor_min_Y)) + Valor_min_Y);

        vaccinationCentre.setFrame(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);
        this.perimeter.setX(x - (SimulatorConfig.VAC_SIZE / 2));
        this.perimeter.setY(y + (SimulatorConfig.VAC_SIZE / 2));
    }
}
