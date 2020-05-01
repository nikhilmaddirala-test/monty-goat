import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Monty {
    static Random randomizer = new Random();

    public static int playGame(int strategy) {
        
        int win = 0;

        // Initialize
        HashMap<Integer, String> doors = new HashMap<Integer, String>();
        int prizeKey = randomizer.nextInt(3);
        doors.put(prizeKey, "Prize");
        for (int i = 0; i < 3; i++) {
            if (i != prizeKey) {
                doors.put(i, "Goat");
            }
        }

        // Player chooses and Monty reveals
        int playerKey = randomizer.nextInt(3);
        int montyKey = -1;
        for (HashMap.Entry<Integer, String> entry : doors.entrySet()) {
            if (entry.getValue() == "Goat" && entry.getKey() != playerKey) {
                montyKey = entry.getKey();
                break;
            }
        }

        if (strategy == 1) { // Switch strategy
            int newPlayerKey = 0 + 1 + 2 - playerKey - montyKey;
            if (doors.get(newPlayerKey) == "Prize") {
                win = 1;
            }
        }

        else { // Don't switch strategy
            if (doors.get(playerKey) == "Prize") {
                win = 1;
            }
        }
        return win;
    }

    public static double runTrials(int numberOfTrials, int strategy){
        int wins = 0;
        for (int i = 0; i < numberOfTrials; i++){
            int win = playGame(strategy);
            wins += win;
        }
        double winPercentage = wins * 1.0 / numberOfTrials;
        return winPercentage;
    }

    public static void main(String[] args) {
        
        int numberOfTrials = 10000;
        double switchWinRate = runTrials(numberOfTrials, 1);
        double remainWinRate = runTrials(numberOfTrials, 0);
        
        System.out.println("Switch: " + switchWinRate + " Remain: " + remainWinRate);

    }
}