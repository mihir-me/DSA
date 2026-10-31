class Solution {
    public int uniquePaths(int[][] grid) {
        final int MOD = 1_000_000_007;
        int m = grid.length;
        int n = grid[0].length;

        // dp[i][j][0]: paths entering (i, j) moving down
        // dp[i][j][1]: paths entering (i, j) moving right
        int[][][] dp = new int[m][n][2];
        
        // Initializing with 1 valid path moving down from (0, 0)
        // Since grid[0][0] == 0, the sum dp[0][0][0] + dp[0][0][1] = 1,
        // which correctly propagates 1 path right and 1 path down.
        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0) {
                    if (grid[i - 1][j] == 0) {
                        dp[i][j][0] = (dp[i - 1][j][0] + dp[i - 1][j][1]) % MOD;
                    } else {
                        dp[i][j][0] = dp[i - 1][j][1];
                    }
                }

                if (j > 0) {
                    if (grid[i][j - 1] == 0) {
                        dp[i][j][1] = (dp[i][j - 1][0] + dp[i][j - 1][1]) % MOD;
                    } else {
                        dp[i][j][1] = dp[i][j - 1][0];
                    }
                }
            }
        }

        return (dp[m - 1][n - 1][0] + dp[m - 1][n - 1][1]) % MOD;
    }
}