package sample;

import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;

public class Robot extends Line {

    private double posX;
    private double posY;
    private XYChart.Data<Number,Number> previousPosition;

    public Robot(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        previousPosition = new XYChart.Data<>(posX, posY);
    }

    public void update(double newPosX, double newPosY){
        previousPosition = new XYChart.Data<>(posX,posY);
        this.posX = newPosX;
        this.posY = newPosY;
    }

    public void updateX(double newPosX){
        this.posX = newPosX;
    }

    public void updateY(double newPosY){
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

    public XYChart.Data<Number, Number> getPreviousPosition() {
        return previousPosition;
    }
}
