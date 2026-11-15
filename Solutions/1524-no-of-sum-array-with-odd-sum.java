class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1_000_000_007;
        long oddCount = 0;
        long evenCount = 1; // Base case: prefix sum of 0 is even
        long currentSum = 0;
        long totalSubarrays = 0;

        for (int num : arr) {
            currentSum += num;
            
            if (currentSum % 2 == 0) {
                totalSubarrays = (totalSubarrays + oddCount) % MOD;
                evenCount++;
            } else {
                totalSubarrays = (totalSubarrays + evenCount) % MOD;
                oddCount++;
            }
        }

        return (int) totalSubarrays;
    }
}