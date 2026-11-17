class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                cost[i][j] = getCost(s, i, j);
            }
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], 100000);
        }
        dp[0][0] = 0;
        for (int j = 1; j <= k; j++) {
            for (int i = j * 2; i <= n; i++) {
                for (int p = (j - 1) * 2; p <= i - 2; p++) {
                    dp[i][j] = Math.min(dp[i][j], dp[p][j - 1] + cost[p][i - 1]);
                }
            }
        }
        return dp[n][k];
    }

    private int getCost(String s, int left, int right) {
        int len = right - left + 1;
        int min = 100000;
        for (int d = 1; d < len; d++) {
            if (len % d != 0) continue;
            int changes = 0;
            int groupSize = len / d;
            for (int start = 0; start < d; start++) {
                for (int i = 0; i < groupSize / 2; i++) {
                    int l = left + start + i * d;
                    int r = left + start + (groupSize - 1 - i) * d;
                    if (s.charAt(l) != s.charAt(r)) changes++;
                }
            }
            min = Math.min(min, changes);
        }
        return min;
    }
}