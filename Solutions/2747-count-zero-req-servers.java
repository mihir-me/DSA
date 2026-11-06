import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        int[][] sortedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }
        
        Arrays.sort(sortedQueries, Comparator.comparingInt(a -> a[0]));
        Arrays.sort(logs, Comparator.comparingInt(a -> a[1]));
        
        int[] ans = new int[m];
        int l = 0, r = 0;
        int logCount = logs.length;
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < m; i++) {
            int qTime = sortedQueries[i][0];
            int qIdx = sortedQueries[i][1];
            int startTime = qTime - x;
            
            while (r < logCount && logs[r][1] <= qTime) {
                map.put(logs[r][0], map.getOrDefault(logs[r][0], 0) + 1);
                r++;
            }
            
            while (l < logCount && logs[l][1] < startTime) {
                int sId = logs[l][0];
                map.put(sId, map.get(sId) - 1);
                if (map.get(sId) == 0) {
                    map.remove(sId);
                }
                l++;
            }
            
            ans[qIdx] = n - map.size();
        }
        
        return ans;
    }
}