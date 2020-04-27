import java.util.Set;
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

            if (amount == 0)
              return 1;

            if (amount < 0)
              return 0;

            //if ()


            return 0;
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
