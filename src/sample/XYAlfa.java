package sample;

import javafx.scene.chart.XYChart;

public class XYAlfa {

    private XYChart.Data<Number, Number> coords;
    private double alfa;

    public XYAlfa(XYChart.Data<Number, Number> coords, double alfa) {
        this.coords = coords;
        this.alfa = alfa;
    }

    public XYChart.Data<Number, Number> getCoords() {
        return coords;
    }

    public double getAlfa() {
        return alfa;
    }

}
