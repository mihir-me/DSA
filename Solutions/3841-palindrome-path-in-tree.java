import java.util.*;

class Solution {
    private int[] bit;
    private int n;

    private void update(int i, int val) {
        while (i <= n) {
            bit[i] ^= val;
            i += i & -i;
        }
    }

    private int query(int i) {
        int ans = 0;
        while (i > 0) {
            ans ^= bit[i];
            i -= i & -i;
        }
        return ans;
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        bit = new int[n + 2];

        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        int log = 1;
        while ((1 << log) <= n) log++;

        int[][] up = new int[log][n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] prefix = new int[n];
        int[] parent = new int[n];
        int[] iter = new int[n];
        int[] stack = new int[n];
        int timer = 0;
        int top = 0;

        Arrays.fill(parent, -1);
        parent[0] = 0;
        stack[top++] = 0;

        tin[0] = timer++;
        prefix[0] = 1 << (s.charAt(0) - 'a');

        while (top > 0) {
            int u = stack[top - 1];

            if (iter[u] < graph[u].size()) {
                int v = graph[u].get(iter[u]++);

                if (v == parent[u]) continue;

                parent[v] = u;
                up[0][v] = u;
                depth[v] = depth[u] + 1;
                prefix[v] = prefix[u] ^ (1 << (s.charAt(v) - 'a'));
                tin[v] = timer++;
                stack[top++] = v;
            } else {
                tout[u] = timer - 1;
                top--;
            }
        }

        up[0][0] = 0;

        for (int j = 1; j < log; j++) {
            for (int i = 0; i < n; i++) {
                up[j][i] = up[j - 1][up[j - 1][i]];
            }
        }

        for (int i = 0; i < n; i++) {
            update(tin[i] + 1, prefix[i] ^ prefix[i]);
        }

        char[] chars = s.toCharArray();
        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int delta = (1 << (chars[u] - 'a')) ^ (1 << (c - 'a'));

                if (delta != 0) {
                    update(tin[u] + 1, delta);
                    update(tout[u] + 2, delta);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int a = u;
                int b = v;

                if (depth[a] < depth[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }

                int diff = depth[a] - depth[b];

                for (int j = 0; j < log; j++) {
                    if ((diff & (1 << j)) != 0) a = up[j][a];
                }

                if (a != b) {
                    for (int j = log - 1; j >= 0; j--) {
                        if (up[j][a] != up[j][b]) {
                            a = up[j][a];
                            b = up[j][b];
                        }
                    }
                    a = up[0][a];
                }

                int lca = a;
                int maskU = prefix[u] ^ query(tin[u] + 1);
                int maskV = prefix[v] ^ query(tin[v] + 1);
                int mask = maskU ^ maskV ^ (1 << (chars[lca] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }
}