package sample;

import com.emxsys.chart.extension.LogarithmicAxis;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.ValueAxis;


import javafx.stage.Stage;

public class Main extends Application {

    @Override public void start(Stage stage) {

        LogarithmicAxis xAxis = new LogarithmicAxis("Domain",0.1d,100d);
        LogarithmicAxis yAxis = new LogarithmicAxis("Range",0.1d,10000d);


       LineChart<Double,Double> chart = new LineChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(0.20, 23.0));
        series.getData().add(new XYChart.Data(0.50, 14.0));
        series.getData().add(new XYChart.Data(0.6, 15.0));
       // series.getData().add(new XYChart.Data(0.2, 24.0));


        Scene scene  = new Scene(chart,800,600);
        chart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
