package sample;

import com.emxsys.chart.extension.LogarithmicAxis;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Reflection;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
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
import java.util.Properties;

import javafx.scene.chart.ValueAxis;
import javafx.util.Duration;
import sun.tools.jar.CommandLine;

import static java.lang.Math.pow;


public class Main extends Application {

    public List<Float> X;
    public List<Float> Y;
    public List<Float> Parameters;

    public static int s = 100;

    public ArrayList<XYChart.Series> array;

    public LineChart<Double,Double> chart;
    public Scene scene;
    public Label label;



    @Override
    public void start(Stage stage) {



        String fileName = "BAZA2.bin";
        //String fileName = "BAZA22.bin";
        this.X = new ArrayList<>();
        this.Parameters = new ArrayList<>();
        this.Y= new ArrayList<>();

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


        System.out.println(Parameters);
        chart = new LineChart(xAxis,yAxis);
        chart.setAnimated(false);

        XYChart.Series<Double,Double> series = new XYChart.Series();
        series.setName("Portfolio 1");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portfolio 1");

        array = new ArrayList<>();

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
                //XYChart.Data<Double,Double> num = array.get(i).getData();


            }
        }

        StackPane spLineChart = new StackPane();
        spLineChart.getChildren().add(chart);

        Button button = new Button("Up");
        Button button2 = new Button("Down");
        button.setOnMouseClicked((event)->{

            s++;
            update(button,button2);
        });

        button.setTranslateX(50);
        //button.setTranslateX(10);


        System.out.println(Y.size());

        button2.setOnMouseClicked((event)->{

            s--;
            update(button,button2);
        });


        label = new Label("D/d: "+Parameters.get(3*s)+" Ri/Rm: "+Parameters.get(3*s+1) + " Rt/Rm: "+Parameters.get(3*s+2) );
        
        label.setTranslateX(150);

        StackPane spButton = new StackPane();

        spButton.setAlignment(Pos.TOP_LEFT);
        spButton.getChildren().addAll(button,button2,label);

        VBox vbox = new VBox();
        VBox.setVgrow(spLineChart, Priority.ALWAYS);//Make line chart always grow vertically
        vbox.getChildren().addAll( spButton,spLineChart);

        scene  = new Scene(vbox,800,600);
        chart.getData().addAll(array.get(0),array.get(1),array.get(2),array.get(3),array.get(4),array.get(5),array.get(6),array.get(7),array.get(8),array.get(9),array.get(10),array.get(11),array.get(12),array.get(13),array.get(14),array.get(15),array.get(16),array.get(17),array.get(18),array.get(19),array.get(20),array.get(21),array.get(22),array.get(23),array.get(24),array.get(25),array.get(26),array.get(27),array.get(28),array.get(29),array.get(30),array.get(31),array.get(32),array.get(33),array.get(34),array.get(35),array.get(36),array.get(37));

        chart.setCreateSymbols(false);




        chart.applyCss();
        chart.layout();


        setCtrlC();

        stage.setScene(scene);
        stage.show();





//        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e->run()));
//        timeline.setCycleCount(timeline.INDEFINITE);
//        timeline.play();
    }



    public void screenshot()
    {
        WritableImage image = chart.snapshot(new SnapshotParameters(),null);
        // WritableImage image = new WritableImage(600,800);

        chart.snapshot(new SnapshotParameters(),image);
        ClipboardContent cc = new ClipboardContent();
        cc.putImage(image);
        Clipboard.getSystemClipboard().setContent(cc);
    }


        public void run()
        {

        }
        public void update(Button b1,Button b2) {
            System.out.println(s);

            if (s >= 607) {
                b1.setDisable(true);
                b2.setDisable(false);
            }
            if (s <= 0) {
                b2.setDisable(true);
                b1.setDisable(false);
            }

            if (s > 0 && s < 607) {
                b2.setDisable(false);
                b1.setDisable(false);
            }


            for (int i = 0; i < 38; i++) {
                array.get(i).getData().clear();
                for (int j = 0; j < 120; j++) {
                    // int temp = i*s*120+j;

                    int temp = s * (120 * 38) + (i * 120) + j;

                    array.get(i).getData().add(new XYChart.Data(pow(10, X.get(j)), pow(10, Y.get(temp))));

                }
            }

            label.setText("D/d: "+Parameters.get(3*s)+" Ri/Rm: "+Parameters.get(3*s+1) + " Rt/Rm: "+Parameters.get(3*s+2));
        }


    final KeyCombination keyCombinationShiftC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);

   // public CommandLine command_line = new CommandLine();
    public void setCtrlC() {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (keyCombinationShiftC.match(event)) {
                    screenshot();
                }
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
