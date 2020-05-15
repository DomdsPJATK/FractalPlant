package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    public FlowPane chartPane;
    private LineChart<Number, Number> lineChart;
    @FXML
    private Button btnStart;

    private Robot robot;
    private XYChart.Series series;
    private boolean isWorking = false;
    private ScheduledExecutorService scheduledExecutorService;

    @FXML
    public void initialize() {
        setLineChart();
        robot = new Robot(10, 0);
        series = new XYChart.Series();
    }

    public void btnAcction(ActionEvent event) {
        if(!isWorking){
            isWorking = true;
            btnStart.setText("Stop");
            draw();
        } else {
            isWorking = false;
            scheduledExecutorService.shutdownNow();
            btnStart.setText("Start");
            series.getData().clear();
        }
    }

    public void draw() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                updateSeries();
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void updateSeries() {
        lineChart.getData().remove(series);
        robot.update(robot.getPosX() + ((Math.pow(-1,(int)(Math.random()*2)))*(Math.random()*5)), robot.getPosY() + ((Math.pow(-1,(int)(Math.random()*2)))*(Math.random()*5)));
        series.getData().add(robot.currentPosition());
        lineChart.getData().add(series);
        Node line = lineChart.getData().get(0).getNode();
        line.setStyle(".default-color1.chart-series-line { -fx-stroke: #f0e68c; }");
    }

    private void setLineChart(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setId("lineChart");
        lineChart.getYAxis().setTickLabelsVisible(false);
        lineChart.getYAxis().setOpacity(0);
        lineChart.getXAxis().setTickLabelsVisible(false);
        lineChart.getXAxis().setOpacity(0);
        lineChart.setLegendVisible(false);
        lineChart.axisSortingPolicyProperty().set(LineChart.SortingPolicy.NONE);
        lineChart.animatedProperty().setValue(false);
        chartPane.getChildren().add(lineChart);
    }


}
