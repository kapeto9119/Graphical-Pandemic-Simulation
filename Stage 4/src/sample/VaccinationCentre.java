package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.geom.Rectangle2D;

public class VaccinationCentre {

    public double x, y;
    Rectangle2D vaccinationCentre;
    Rectangle perimeter;
    Comuna comuna;

    public VaccinationCentre(Comuna comuna){

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
