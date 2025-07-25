import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        
        while (t-- > 0) {
            String[] input = br.readLine().split(" ");
            int distance = Integer.parseInt(input[0]);
            int initialBattery = Integer.parseInt(input[1]);
            
            int[] result = solve(distance, initialBattery);
            System.out.println(result[0] + " " + result[1]);
        }
    }
    
    static int[] solve(int distance, int battery) {
        // dp[i][j][k] = minimum time to cover distance i with battery j after k consecutive moves
        // We use a map for sparse representation
        Map<String, Integer> dp = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[2] != b[2]) return a[2] - b[2]; // time
            return b[1] - a[1]; // battery (higher is better)
        });
        
        // [distance, battery, time, consecutive_moves]
        pq.offer(new int[]{distance, battery, 0, 0});
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int dist = current[0];
            int batt = current[1];
            int time = current[2];
            int moves = current[3];
            
            if (dist == 0) {
                return new int[]{time, batt};
            }
            
            String key = dist + "," + batt + "," + moves;
            if (dp.containsKey(key) && dp.get(key) <= time) {
                continue;
            }
            dp.put(key, time);
            
            // Option 1: Charge (resets consecutive moves to 0)
            pq.offer(new int[]{dist, batt + 1, time + 1, 0});
            
            // Option 2: Move (if we have enough battery)
            int batteryCost = moves; // 0 for first consecutive move, 1 for second, etc.
            if (batt >= batteryCost && dist > 0) {
                pq.offer(new int[]{dist - 1, batt - batteryCost, time + 1, moves + 1});
            }
        }
        
        return new int[]{-1, -1}; // Should not reach here
    }
}