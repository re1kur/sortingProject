package pack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

public class MySortWithVisualization extends Application {
    private static final int BAR_WIDTH = 20;
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 400;
    private static List<Integer> array;

    private Pane pane;
    private boolean reverse;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        array = generateRandomArray();
        reverse = false; // По умолчанию сортировка по возрастанию
        pane = new Pane();
        pane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        drawArray(array, -1, -1);

        new Thread(() -> {
            try {
                quickSort(array, 0, array.size() - 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        stage.setScene(new Scene(pane));
        stage.setTitle("Quick Sort Visualization");
        stage.show();
    }

    private List<Integer> generateRandomArray() {
        Random random = new Random();
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            array.add(random.nextInt(100 - 10 + 1) + 10);
        }
        return array;
    }

    private void quickSort(List<Integer> array, int low, int high) throws InterruptedException {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private int partition(List<Integer> array, int low, int high) throws InterruptedException {
        int pivot = array.get(high);
        int i = low - 1;

        int delay = 300;
        for (int j = low; j < high; j++) {
            if ((reverse && array.get(j) > pivot) || (!reverse && array.get(j) < pivot)) {
                i++;
                Collections.swap(array, i, j);
                updateVisualization(array, i, j);
                Thread.sleep(delay);
            }
        }
        Collections.swap(array, i + 1, high);
        updateVisualization(array, i + 1, high);
        Thread.sleep(delay);
        return i + 1;
    }

    private void updateVisualization(List<Integer> array, int highlightedIndex1, int highlightedIndex2) {
        Platform.runLater(() -> drawArray(array, highlightedIndex1, highlightedIndex2));
    }

    private void drawArray(List<Integer> array, int highlightedIndex1, int highlightedIndex2) {
        pane.getChildren().clear();
        int barHeightUnit = WINDOW_HEIGHT / (Collections.max(array) + 10);

        for (int i = 0; i < array.size(); i++) {
            int height = array.get(i) * barHeightUnit;
            Rectangle bar = new Rectangle(
                    i * BAR_WIDTH,
                    WINDOW_HEIGHT - height,
                    BAR_WIDTH - 2,
                    height
            );

            bar.setFill(Color.GREY);
            if (i == highlightedIndex1 || i == highlightedIndex2) {
                bar.setFill(Color.RED);
            }
            pane.getChildren().add(bar);
        }
    }
}
