import java.util.*;

class Solution {
    static final long MOD1 = 1000000007L;
    static final long MOD2 = 1000000009L;
    static final long BASE1 = 100019L;
    static final long BASE2 = 100043L;

    public int smallestUniqueSubarray(int[] nums) {
        int left = 1;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (hasUnique(nums, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean hasUnique(int[] nums, int len) {
        int n = nums.length;

        long pow1 = 1;
        long pow2 = 1;

        for (int i = 0; i < len; i++) {
            pow1 = pow1 * BASE1 % MOD1;
            pow2 = pow2 * BASE2 % MOD2;
        }

        long h1 = 0;
        long h2 = 0;

        for (int i = 0; i < len; i++) {
            h1 = (h1 * BASE1 + nums[i]) % MOD1;
            h2 = (h2 * BASE2 + nums[i]) % MOD2;
        }

        Map<Long, Integer> freq = new HashMap<>();

        long key = (h1 << 32) | h2;
        freq.put(key, 1);

        for (int i = len; i < n; i++) {
            h1 = (h1 * BASE1 + nums[i] - pow1 * nums[i - len] % MOD1 + MOD1) % MOD1;
            h2 = (h2 * BASE2 + nums[i] - pow2 * nums[i - len] % MOD2 + MOD2) % MOD2;

            key = (h1 << 32) | h2;
            freq.put(key, freq.getOrDefault(key, 0) + 1);
        }

        for (int count : freq.values()) {
            if (count == 1) return true;
        }

        return false;
    }
}