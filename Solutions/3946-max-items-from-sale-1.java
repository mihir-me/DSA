import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxFactor = 0;
        for (int[] it : items) {
            maxFactor = Math.max(maxFactor, it[0]);
        }

        int[] freeCount = new int[n];
        for (int i = 0; i < n; i++) {
            int fi = items[i][0];
            int cnt = 0;
            for (int m = fi; m <= maxFactor; m += fi) {
                for (int j = 0; j < n; j++) {
                    if (i != j && items[j][0] == m) {
                        cnt++;
                    }
                }
            }
            freeCount[i] = cnt;
        }

        int INF = Integer.MIN_VALUE / 4;
        int[] unactivated = new int[budget + 1];
        int[] activated = new int[budget + 1];
        Arrays.fill(unactivated, INF);
        Arrays.fill(activated, INF);
        unactivated[0] = 0;

        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            int bonus = 1 + freeCount[i];
            int[] nextUnact = Arrays.copyOf(unactivated, budget + 1);
            int[] nextAct = Arrays.copyOf(activated, budget + 1);

            for (int b = price; b <= budget; b++) {
                if (unactivated[b - price] != INF) {
                    nextAct[b] = Math.max(nextAct[b], unactivated[b - price] + bonus);
                }
                if (activated[b - price] != INF) {
                    nextAct[b] = Math.max(nextAct[b], activated[b - price] + bonus);
                }
            }

            for (int b = price; b <= budget; b++) {
                if (nextAct[b - price] != INF) {
                    nextAct[b] = Math.max(nextAct[b], nextAct[b - price] + 1);
                }
            }

            unactivated = nextUnact;
            activated = nextAct;
        }

        int ans = 0;
        for (int b = 0; b <= budget; b++) {
            ans = Math.max(ans, Math.max(unactivated[b], activated[b]));
        }
        return ans;
    }
}