package sample;

import com.emxsys.chart.extension.LogarithmicAxis;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.ValueAxis;

import static java.lang.Math.pow;


public class Main extends Application {

    @Override public void start(Stage stage) {



        //String fileName = "BAZA2.bin";
        String fileName = "BAZA22.bin";
        List<Float> X = new ArrayList<>();
        List<Float> Parameters = new ArrayList<>();
        List<Float> Y = new ArrayList<>();

        try {
            FileInputStream fileIs = new FileInputStream(fileName);
            DataInputStream is = new DataInputStream(fileIs);

            for (int i = 0; i < 120; i++)
                X.add(Float.intBitsToFloat(Integer.reverseBytes(is.readInt())));

            boolean endOfFile = false;
            while(!endOfFile)
            {
                try
                {
                    for (int i = 0; i < 3; i++)
                        Parameters.add(Float.intBitsToFloat(Integer.reverseBytes(is.readInt())));

                    for (int i = 0; i < 120; i++)
                        Y.add(Float.intBitsToFloat(Integer.reverseBytes(is.readInt())));
                }
                catch(EOFException ex)
                {
                    endOfFile = true;
                }
            }


            //System.out.println(Y.size());
            System.out.println("koniec");
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogarithmicAxis xAxis = new LogarithmicAxis("Domain",0.1d,100d);
        LogarithmicAxis yAxis = new LogarithmicAxis("Range",0.1d,10000d);


        LineChart<Double,Double> chart = new LineChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Portfolio 1");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portfolio 1");

        ArrayList<XYChart.Series> array = new ArrayList<>();

        for (int i =0; i<38;i++)
        {
            array.add(new XYChart.Series());
            array.get(i).setName(Parameters.get((3*i)+2).toString());
        }


        for(int i =0; i<38;i++)
        {
            for(int j = 0; j < 120;j++)
            {
                int temp = i*120+j;
                array.get(i).getData().add(new XYChart.Data(pow(10,X.get(j)),pow(10,Y.get(temp))));
            }
        }

        StackPane spLineChart = new StackPane();
        spLineChart.getChildren().add(chart);

        Button button = new Button("Up");
        button.setOnMouseClicked((event)->{
            System.out.println("You just clicked me");
        });

        button.setTranslateX(50);
        //button.setTranslateX(10);




        Button button2 = new Button("Down");
        button2.setOnMouseClicked((event)->{
            System.out.println("You just clicked me");
        });

        StackPane spButton = new StackPane();

        spButton.setAlignment(Pos.TOP_LEFT);
//        spButton.getChildren().add(button);
//        spButton.getChildren().add(button2);
        spButton.getChildren().addAll(button,button2);

        VBox vbox = new VBox();
        VBox.setVgrow(spLineChart, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll( spButton,spLineChart);

        Scene scene  = new Scene(vbox,800,600);
        chart.getData().addAll(array.get(0),array.get(1),array.get(2),array.get(3),array.get(4),array.get(5),array.get(6),array.get(7),array.get(8),array.get(9),array.get(10),array.get(11),array.get(12),array.get(13),array.get(14),array.get(15),array.get(16),array.get(17),array.get(18),array.get(19),array.get(20),array.get(21),array.get(22),array.get(23),array.get(24),array.get(25),array.get(26),array.get(27),array.get(28),array.get(29),array.get(30),array.get(31),array.get(32),array.get(33),array.get(34),array.get(35),array.get(36),array.get(37));

        chart.setCreateSymbols(false);


        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
