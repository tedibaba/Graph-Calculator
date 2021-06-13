import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.animation.KeyFrame;


import java.util.ArrayList;


public class MainController {

    @FXML
    TextField squaredTerm;
    @FXML
    TextField xTerm;
    @FXML
    TextField constantTerm;
    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    LineChart<Double, Double> graph;
    @FXML
    TextField minDomainOfPlane;
    @FXML
    TextField maxDomainOfPlane;
    @FXML
    TextField minDomainOfGraph;
    @FXML
    TextField maxDomainOfGraph;
    @FXML
    ListView functions;
    @FXML
    Label errorMessage;

    ArrayList<ArrayList<Double>> equations = new ArrayList<>();
    ErrorThread error;
    boolean changed = false;

    //Get numbers from users to graph the equation
    @FXML
    private void sketchGraph() throws InterruptedException {
        try{
            System.out.println(xTerm.getText());
            Double squaredTermNumber = (squaredTerm.getText().isEmpty()) ? 0 : Double.parseDouble(squaredTerm.getText());
            Double xTermNumber = (xTerm.getText().isEmpty()) ? 0 :Double.parseDouble(xTerm.getText());
            Double constantTermNumber = (constantTerm.getText().isEmpty()) ? 0 :Double.parseDouble(constantTerm.getText());
            System.out.println("Squared Term: " + squaredTermNumber + " X term: " + xTermNumber + " constant term: " + constantTermNumber);
            ArrayList<Double> equation = new ArrayList<>();
            equation.add(squaredTermNumber);
            equation.add(xTermNumber);
            equation.add(constantTermNumber);
            equations.add(equation);
            ArrayList<Double> minAndMax = minAndMax();
            Double minimum = (xAxis.getLowerBound() >= minAndMax.get(0)) ? xAxis.getLowerBound() : minAndMax.get(0);
            Double maximum = (xAxis.getUpperBound() <= minAndMax().get(1)) ? xAxis.getUpperBound() : minAndMax.get(1);
            plotGraph(squaredTermNumber, xTermNumber, constantTermNumber, minimum, maximum);
            functions.getItems().add(squaredTermNumber + "x^2 + " + xTermNumber + "x + " + constantTermNumber);
        }catch(IllegalArgumentException x){
            error();
        }


    }

    //Plotting the graph on the line chart
    private void plotGraph(Double squaredTerm, Double xTerm, Double constantTerm, Double min, Double max){
        XYChart.Series<Double, Double> points = new XYChart.Series<Double, Double>();

        for (double x = min; x <= max; x += 0.01){
            points.getData().add(new XYChart.Data<>(x, squaredTerm * Math.pow(x, 2) + xTerm * x + constantTerm));
        }
        graph.getData().add(points);
    }

    private ArrayList<Double> minAndMax(){
        Double minDomain = (minDomainOfGraph.getText().isEmpty()) ? xAxis.getLowerBound() : Double.parseDouble(minDomainOfGraph.getText());
        Double maxDomain = (maxDomainOfGraph.getText().isEmpty()) ? xAxis.getUpperBound() : Double.parseDouble(maxDomainOfGraph.getText());
        ArrayList<Double> minAndMax = new ArrayList<>();
        minAndMax.add(minDomain);
        minAndMax.add(maxDomain);

        return minAndMax;
    }

    @FXML
    private void clearGraph(){
        graph.getData().clear();
    }

    @FXML
    private void changeMinDomainOfPlane() throws InterruptedException {
        try {
            if (!minDomainOfPlane.getText().isEmpty()){
                double minimumDomain = Double.parseDouble(minDomainOfPlane.getText());
                xAxis.setLowerBound(minimumDomain);
                ArrayList<Double> minAndMax = minAndMax();
                Double minimum = (xAxis.getLowerBound() >= minAndMax.get(0)) ? xAxis.getLowerBound() : minAndMax.get(0);
                Double maximum = (xAxis.getUpperBound() <= minAndMax().get(1)) ? xAxis.getUpperBound() : minAndMax.get(1);
                for (ArrayList<Double> graph : equations){
                    plotGraph(graph.get(0), graph.get(1), graph.get(2), minimum, maximum);
                }
            } else {
                xAxis.setLowerBound(-10);
            }
        } catch (IllegalArgumentException e){
            error();
        }
    }

    @FXML
    private void changeMaxDomainOfPlane() throws InterruptedException {
        try {
            if (!maxDomainOfPlane.getText().isEmpty()) {
                double maximumDomain = Double.parseDouble(maxDomainOfPlane.getText());
                xAxis.setUpperBound(maximumDomain);
//                ArrayList<Double> minAndMax = minAndMax();
//                Double minimum = (xAxis.getLowerBound() >= minAndMax.get(0)) ? xAxis.getLowerBound() : minAndMax.get(0);
//                Double maximum = (xAxis.getUpperBound() <= minAndMax().get(1)) ? xAxis.getUpperBound() : minAndMax.get(1);
//                for (ArrayList<Double> graph : equations){
//                    plotGraph(graph.get(0), graph.get(1), graph.get(2), minimum, maximum);
//                }
            } else{
                xAxis.setUpperBound(10);
            }
        } catch (IllegalArgumentException e){
            error();
        }
    }

    @FXML
    private void changeMinDomain() throws InterruptedException {
        try {
            if (!minDomainOfGraph.getText().isEmpty()) {
                ArrayList<Double> minAndMax = minAndMax();
                Double minimum = (xAxis.getLowerBound() >= minAndMax.get(0)) ? xAxis.getLowerBound() : minAndMax.get(0);
                Double maximum = (xAxis.getUpperBound() <= minAndMax().get(1)) ? xAxis.getUpperBound() : minAndMax.get(1);
                graph.getData().clear();
                for (ArrayList<Double> graph : equations){
                    System.out.println("Hello");
                    plotGraph(graph.get(0), graph.get(1), graph.get(2), minimum, maximum);
                }
            }
        } catch (IllegalArgumentException e){
            error();
        }
    }

    @FXML
    private void changeMaxDomain() throws InterruptedException {
        try {
            if (maxDomainOfGraph.getText() != null) {
                ArrayList<Double> minAndMax = minAndMax();
                Double minimum = (xAxis.getLowerBound() >= minAndMax.get(0)) ? xAxis.getLowerBound() : minAndMax.get(0);
                Double maximum = (xAxis.getUpperBound() <= minAndMax().get(1)) ? xAxis.getUpperBound() : minAndMax.get(1);
                graph.getData().clear();
                for (ArrayList<Double> graph : equations){
                    System.out.println("HEY");
                    plotGraph(graph.get(0), graph.get(1), graph.get(2), minimum, maximum);
                }
            } else if(maxDomainOfGraph.getText().isEmpty()){
                xAxis.setUpperBound(10);
            }
        } catch (IllegalArgumentException e){
            error();
        }
    }

    private void error(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(!changed){
                    errorMessage.setText("Invalid numbers");
                    changed = true;
                } else {
                    errorMessage.setText("");
                    changed = false;
                }
            }
                }));
        timeline.setCycleCount(2);
        timeline.play();
    }
}
