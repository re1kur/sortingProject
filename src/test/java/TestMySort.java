import java.util.*;

import org.junit.Test;
import pack.Main;

import static org.junit.Assert.assertEquals;

public class TestMySort {

    @Test
    public void testAscendingSort() {
        List<Integer> input = Arrays.asList(4, 1, 3, 2, 5);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> result = Main.mySort(input, false, null, null);
        assertEquals(expected, result);
    }

    @Test
    public void testDescendingSort() {
        List<Integer> input = Arrays.asList(4, 1, 3, 2, 5);
        List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);
        List<Integer> result = Main.mySort(input, true, null, null);
        assertEquals(expected, result);
    }

    @Test
    public void testStringSort() {
        List<String> input = Arrays.asList("banana", "apple", "cherry");
        List<String> expected = Arrays.asList("apple", "banana", "cherry");
        List<String> result = Main.mySort(input, false, null, null);
        assertEquals(expected, result);
    }

    @Test
    public void testCustomKeySort() {
        List<String> input = Arrays.asList("a", "ccc", "bb");
        List<String> expected = Arrays.asList("a", "bb", "ccc");
        List<String> result = Main.mySort(input, false, String::length, null);
        assertEquals(expected, result);
    }
}
