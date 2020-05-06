import java.util.Set;
import java.util.*;
// TODO of course, you may wish to import more things...

public abstract class CoinChanger {
    abstract public int minCoins(int amount, Set<Integer> denominations);

    private static void checkArguments(int amount, Set<Integer> denominations) {

      if (amount < 1)
          throw new IllegalArgumentException("Amount must be at least 1");

      if (denominations.isEmpty())
          throw new IllegalArgumentException("At least one denomination is required");

      if (!(denominations.contains(1)))
          throw new IllegalArgumentException("Denominations must have a 1-unit coin");

      var all_Pos = true;
      for (var i:denominations){
        if (i <= 0){
          all_Pos = false;
          throw new IllegalArgumentException("Denominations must all be positive");

        }
      }

    }


    public static class TopDown extends CoinChanger {
        public int minCoins(int amount, Set<Integer> denominations) {
            checkArguments(amount, denominations);
            int[] denomination = new int[denominations.size()];
            int index = 0;
            for (Iterator<Integer> it = denominations.iterator(); it.hasNext(); ) {
              Integer f = it.next();
              denomination[index] = f.intValue();
              index++;
            }
            int[] memo = new int[amount + 1];
            return minCoins(amount, denomination, memo);
        }


        public int minCoins(int amount, int[] coins, int[] memo){
          if (amount < 0) {
            return -1;
          }
          if (memo[amount] != 0) {
            return memo[amount];
          }
          if (amount == 0) {
            return 0;
          }


          int val = Integer.MAX_VALUE;
          for (int coin: coins) {

            if (amount - coin < 0){
              continue;
            }
            memo[amount - coin] = minCoins(amount - coin, coins, memo);
            if (memo[amount - coin] >= 0 && 1 + memo[amount - coin] < val) {
              val = memo[amount - coin] + 1;
            }
          }
          memo[amount] = val != Integer.MAX_VALUE ? val : -1;
          return memo[amount];
        }
    }

    public static class BottomUp extends CoinChanger {
        public int minCoins(int amount, Set<Integer> denominations) {
            checkArguments(amount, denominations);

            var table = new int[amount+1];
            table[0] = 0;

            for (var i = 0; i < table.length; i++) {

              table[i] = Integer.MAX_VALUE;
              for (var d: denominations) {

                if (d <= i){

                  table[i] = Math.min(table[i], table[i-d]+1);
                }
              }
            }



            return table[amount];

        }
    }
}
