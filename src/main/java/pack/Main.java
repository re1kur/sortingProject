package pack;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.BiFunction;

public class Main {

    public static <T> List<T> mySort(
            List<T> array,
            boolean reverse,
            Function<T, Comparable> key,
            BiFunction<T, T, Integer> cmp) {

        if (array == null || array.size() <= 1) {
            return array;
        }

        // Wrapper для сравнения элементов
        Comparator<T> comparator = (a, b) -> {
            if (cmp != null) {
                return cmp.apply(a, b);
            }
            Comparable keyA = key != null ? key.apply(a) : (Comparable) a;
            Comparable keyB = key != null ? key.apply(b) : (Comparable) b;
            return keyA.compareTo(keyB);
        };

        if (reverse) {
            comparator = comparator.reversed();
        }

        List<T> sortedArray = new ArrayList<>(array);
        quickSort(sortedArray, 0, sortedArray.size() - 1, comparator);
        return sortedArray;
    }

    private static <T> void quickSort(List<T> array, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, comparator);
            quickSort(array, low, pivotIndex - 1, comparator);
            quickSort(array, pivotIndex + 1, high, comparator);
        }
    }

    private static <T> int partition(List<T> array, int low, int high, Comparator<T> comparator) {
        T pivot = array.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(array.get(j), pivot) <= 0) {
                i++;
                Collections.swap(array, i, j);
            }
        }
        Collections.swap(array, i + 1, high);
        return i + 1;
    }

    public static void main(String[] args) {
        try {
            if (args.length < 4) {
                System.out.println("Usage: java Main <dataType| 0 = String, 1 - Integer> <file_path> <output_path> <reversed| 1 = true, 0 = false>");
                return;
            }
            int dataType = Integer.parseInt(args[0]);
            String inputFilePath = args[1];
            String outputFilePath = args[2];
            boolean reverse = false;
            int reverseInt = Integer.parseInt(args[3]);
            if (reverseInt == 1) reverse = true;
            List<String> lines = readFile(inputFilePath);
            List<?> sortedData;

            if (dataType == 1) {
                List<Integer> numbers = new ArrayList<>();
                for (String line : lines) {
                    numbers.add(Integer.parseInt(line.trim()));
                }
                sortedData = mySort(numbers, reverse, null, null);
            } else {
                sortedData = mySort(lines, reverse, null, null);
            }
            writeFile(outputFilePath, sortedData);
            System.out.println("Сортировка завершена. Результат записан в файл: " + outputFilePath);
        } catch (Exception _) {
            System.out.println();
        }
    }

    private static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static void writeFile(String filePath, List<?> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Object line : data) {
                writer.write(line.toString());
                writer.newLine();
            }
        }
    }
}
