package sample;

import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;

public class Robot extends Line {

    private double posX;
    private double posY;

    public Robot(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void update(double newPosX, double newPosY){
        this.posX = newPosX;
        this.posY = newPosY;
    }

    public XYChart.Data<Number,Number> currentPosition(){
        return new XYChart.Data<>(posX,posY);
    }

    public double getPosY() {
        return posY;
    }

    public double getPosX() {
        return posX;
    }

}
