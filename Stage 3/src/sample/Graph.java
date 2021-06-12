package sample;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

public class Graph {

    public XYChart.Series Susceptibles;
    public XYChart.Series Infected;
    public XYChart.Series Recovered;
    public StackedAreaChart stack;

    public Graph(Comuna comuna){

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        stack = new StackedAreaChart(xAxis,yAxis);
        stack.setTitle("Pandemic Evolution Chart");
        stack.setAnimated(false);

        Susceptibles = new XYChart.Series<>();
        Susceptibles.setName("Susceptibles");
        Infected = new XYChart.Series<>();
        Infected.setName("Infected");
        Recovered = new XYChart.Series<>();
        Recovered.setName("Recovered");

        Susceptibles.getData().add(new XYChart.Data(0, comuna.Susceptible_Q));
        Infected.getData().add(new XYChart.Data(0, comuna.Infected_Q));
        Recovered.getData().add(new XYChart.Data(0, comuna.Recovered_Q));

        stack.getData().add(Infected);
        stack.getData().add(Recovered);
        stack.getData().add(Susceptibles);

    }

    public void updateChart(double time, double susc, double inf, double rec){
        Susceptibles.getData().add(new XYChart.Data(time,susc));
        Infected.getData().add(new XYChart.Data(time,inf));
        Recovered.getData().add(new XYChart.Data(time,rec));
    }
}
