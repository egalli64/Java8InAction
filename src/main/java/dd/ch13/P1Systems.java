package dd.ch13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class P1Systems {
    public static void main(String[] args) {
        List<List<Integer>> result = subsets(Arrays.asList(1, 4, 9));
        System.out.println(result);
    }

    public static List<List<Integer>> subsets(List<Integer> values) {
        if (values.isEmpty()) {
            List<List<Integer>> result = new ArrayList<>();
            result.add(Collections.emptyList());
            return result;
        }

        Integer first = values.get(0);
        List<Integer> others = values.subList(1, values.size());
        List<List<Integer>> lhs = subsets(others);
        List<List<Integer>> rhs = insertAll(first, lhs);
        return concat(lhs, rhs);
    }

    private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> cur : lists) {
            List<Integer> copy = new ArrayList<>();
            copy.add(first);
            copy.addAll(cur);
            result.add(copy);
        }
        return result;
    }

    private static List<List<Integer>> concat(List<List<Integer>> lhs, List<List<Integer>> rhs) {
        List<List<Integer>> result = new ArrayList<>(lhs);
        result.addAll(rhs);
        return result;
    }
}
