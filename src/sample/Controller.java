package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    public FlowPane chartPane;
    public TextField textInput;
    private LineChart<Number, Number> lineChart;
    @FXML
    private Button btnStart;

    private Robot robot;
    private XYChart.Series currentSeries;
    private boolean isWorking = false;
    private ScheduledExecutorService scheduledExecutorService;
    private String instructions;
    private StringReader reader;
    private double currentAngle;
    private LinkedList<XYChart.Series<Number,Number>> seriesList;
    private LinkedList<XYAlfa> stack;

    @FXML
    public void initialize() {
        setLineChart();
        currentSeries = new XYChart.Series();
        seriesList = new LinkedList<>();
        seriesList.add(currentSeries);
        stack = new LinkedList<>();
    }

    public void btnAcction(ActionEvent event) {
        if(!isWorking){
            startDrawing();
        } else {
            stopDrawing();
        }
    }

    public void draw() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                if(!reader.hasNextChar()) stopDrawing();
                else updateSeries(reader.nextChar());
            });
        }, 0, 25, TimeUnit.MILLISECONDS);
    }

    public void updateSeries(char sign) {
        lineChart.getData().removeAll();

        switch(sign) {
            case '-':
                currentAngle = currentAngle - 25;
                break;
            case '+':
                currentAngle = currentAngle + 25;
                break;
            case 'F':
                robot.update(robot.getPosX() + Math.cos(Math.toRadians(currentAngle)), robot.getPosY() + Math.sin(Math.toRadians(currentAngle)));
                seriesList.getLast().getData().add(robot.currentPosition());
                break;
            case '[':
                stack.add(new XYAlfa(robot.currentPosition(), currentAngle));
                break;
            case ']':
                currentSeries = new XYChart.Series();
                XYChart.Data<Number, Number> lastData = stack.getLast().getCoords();
                currentSeries.getData().add(lastData);
                seriesList.add(currentSeries);
                robot.update(lastData.getXValue().doubleValue(), lastData.getYValue().doubleValue());
                currentAngle = stack.getLast().getAlfa();
                stack.remove(stack.getLast());
                break;
            case 'X':
                break;
        }

        System.out.println(sign + " "  + robot.getPosX() + " " + robot.getPosY());

        lineChart.getData().clear();
        lineChart.getData().addAll(seriesList);

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

    private void startDrawing(){
        currentAngle = 90-25;
        robot = new Robot(0,0);
        seriesList.getLast().getData().add(new XYChart.Data(0,0));
        reader = new StringReader(createInstructions());
        isWorking = true;
        btnStart.setText("Stop");
        draw();
    }

    private void stopDrawing(){
        isWorking = false;
        if(scheduledExecutorService != null) scheduledExecutorService.shutdownNow();
        btnStart.setText("Start");
        currentSeries.getData().clear();
    }

    private String createInstructions(){
        String instructions = "X";
        int iter = Integer.parseInt(textInput.getText());
        for (int i = 0; i < iter; i++) {
            instructions = instructions.replaceAll("F", "FF");
            instructions = instructions.replaceAll("X", "F+[[X]-X]-F[-FX]+X");
        }
        System.out.println(instructions);
        return instructions;
    }


}
