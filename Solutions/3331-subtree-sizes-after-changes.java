import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] origTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            origTree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            origTree[parent[i]].add(i);
        }

        int[] newParent = Arrays.copyOf(parent, n);
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        dfsReassign(0, origTree, s, lastSeen, newParent);

        List<Integer>[] finalTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            finalTree[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            finalTree[newParent[i]].add(i);
        }

        int[] answer = new int[n];
        computeSubtreeSizes(0, finalTree, answer);
        return answer;
    }

    private void dfsReassign(int node, List<Integer>[] origTree, String s, int[] lastSeen, int[] newParent) {
        int chIdx = s.charAt(node) - 'a';
        int prevAncestor = lastSeen[chIdx];
        if (prevAncestor != -1) {
            newParent[node] = prevAncestor;
        }
        lastSeen[chIdx] = node;

        for (int child : origTree[node]) {
            dfsReassign(child, origTree, s, lastSeen, newParent);
        }

        lastSeen[chIdx] = prevAncestor;
    }

    private int computeSubtreeSizes(int node, List<Integer>[] finalTree, int[] answer) {
        int size = 1;
        for (int child : finalTree[node]) {
            size += computeSubtreeSizes(child, finalTree, answer);
        }
        answer[node] = size;
        return size;
    }
}