package sample;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

public class Graph {

    public XYChart.Series Susceptibles;
    public XYChart.Series Infected;
    public XYChart.Series Recovered;
    public XYChart.Series Vaccinated;
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
        Vaccinated = new XYChart.Series<>();
        Vaccinated.setName("Vaccinated");

        Susceptibles.getData().add(new XYChart.Data(0, comuna.Susceptible_Q));
        Infected.getData().add(new XYChart.Data(0, comuna.Infected_Q));
        Recovered.getData().add(new XYChart.Data(0, comuna.Recovered_Q));
        Vaccinated.getData().add(new XYChart.Data(0, comuna.Vaccinated_Q));

        stack.getData().add(Infected);
        stack.getData().add(Recovered);
        stack.getData().add(Susceptibles);
        stack.getData().add(Vaccinated);

    }

    public void updateChart(double time, double susc, double inf, double rec, double vac){
        Susceptibles.getData().add(new XYChart.Data(time,susc));
        Infected.getData().add(new XYChart.Data(time,inf));
        Recovered.getData().add(new XYChart.Data(time,rec));
        Vaccinated.getData().add(new XYChart.Data(time,vac));

    }
}
