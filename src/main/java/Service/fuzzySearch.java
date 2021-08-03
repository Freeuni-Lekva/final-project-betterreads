package Service;

public class fuzzySearch{
    public static int levDistance(String s1, String s2){
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for(int i = 0; i <= s1.length(); i++){
            for(int j = 0; j <= s2.length(); j++){
                if(i == 0){
                    dp[i][j] = j;
                    continue;
                }
                if(j == 0){
                    dp[i][j] = i;
                    continue;
                }
                char c1 = s1.charAt(i-1);
                char c2 = s2.charAt(j-1);
                int add = 0;
                if(c1 != c2) add++;
                dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
            }
        }

        return dp[s1.length()][s2.length()];
    }
}
