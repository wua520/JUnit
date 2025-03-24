package JosephusSolver0;

import java.util.LinkedList;

public class JosephusSolver {

    public static int solveJosephus(int n, int k) {
        LinkedList<Integer> people = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }

        int index = 0;
        while (people.size() > 1) {
            index = (index + k - 1) % people.size(); // 找到要淘汰的索引
            people.remove(index);
        }

        return people.get(0);
    }
}
