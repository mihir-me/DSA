class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int maxLength = 0;
        int left = 0;
        java.util.HashMap<Character, Integer> charMap = new java.util.HashMap<>();
        
        for (int right = 0; right < n; right++) {
            char c = s.charAt(right);
            if (charMap.containsKey(c)) {
                left = Math.max(left, charMap.get(c) + 1);
            }
            charMap.put(c, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}