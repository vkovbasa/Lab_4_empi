import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Введення параметра N
        int N = 5; // Номер студента
        int size = 20 * N;
        double[] normalData = generateNormalDistribution(size, N);
        double[] exponentialData = generateExponentialDistribution(size, N);

        // Створення осей для графіка
        Axis<Number> xAxis = new NumberAxis();
        Axis<Number> yAxis = new NumberAxis();
        xAxis.setLabel("Значення");
        yAxis.setLabel("Частота");

        // Створення гістограми для нормального розподілу
        BarChart<Number, Number> normalChart = createHistogram(normalData, "Нормальний розподіл", xAxis, yAxis);

        // Створення гістограми для показникового розподілу
        BarChart<Number, Number> exponentialChart = createHistogram(exponentialData, "Показниковий розподіл", xAxis, yAxis);

        // Відображення
        stage.setTitle("Гістограми");
        Scene scene = new Scene(normalChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private BarChart<Number, Number> createHistogram(double[] data, String title, Axis<Number> xAxis, Axis<Number> yAxis) {
        BarChart<Number, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle(title);

        // Розподіл значень у категорії (бінінг)
        int bins = 10;
        double[] binCounts = new double[bins];
        double min = 0;
        double max = data[data.length - 1];
        double binSize = (max - min) / bins;

        for (double value : data) {
            int binIndex = (int) ((value - min) / binSize);
            if (binIndex >= bins) binIndex = bins - 1; // Гарантія, що значення потрапляє у правильний бін
            binCounts[binIndex]++;
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < bins; i++) {
            double binStart = min + i * binSize;
            series.getData().add(new XYChart.Data<>(binStart, binCounts[i]));
        }

        chart.getData().add(series);
        return chart;
    }

    private double[] generateNormalDistribution(int size, int N) {
        Random random = new Random();
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            data[i] = Math.min(Math.max(random.nextGaussian() * (N / 3.0) + (N / 2.0), 0), N);
        }
        return data;
    }

    private double[] generateExponentialDistribution(int size, int N) {
        Random random = new Random();
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            double value = -Math.log(1 - random.nextDouble()) * (N / 2.0);
            data[i] = Math.min(Math.max(value, 0), N);
        }
        return data;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
