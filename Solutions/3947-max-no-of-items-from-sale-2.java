import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int cheapest = Integer.MAX_VALUE;
        int maxFactor = 0;
        for (int[] item : items) {
            if (item[1] < cheapest) cheapest = item[1];
            if (item[0] > maxFactor) maxFactor = item[0];
        }

        long[] freq = new long[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        for (int i = 1; i <= maxFactor; i++) {
            if (freq[i] > 0) {
                for (int multiple = 2 * i; multiple <= maxFactor; multiple += i) {
                    freq[i] += freq[multiple];
                }
            }
        }

        TreeMap<Long, Long> costCount = new TreeMap<>();
        long twoCheapest = 2L * cheapest;
        for (int[] item : items) {
            long cost = item[1];
            long count = freq[item[0]] - 1L;
            if (count > 0 && cost <= twoCheapest) {
                costCount.put(cost, costCount.getOrDefault(cost, 0L) + count);
            }
        }

        long rem = budget;
        long total = 0;
        for (Map.Entry<Long, Long> entry : costCount.entrySet()) {
            long cost = entry.getKey();
            long count = entry.getValue();
            long include = Math.min(count, rem / cost);
            total += 2L * include;
            rem -= include * cost;
        }

        total += rem / cheapest;
        return (int) total;
    }
}