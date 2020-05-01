import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MontyGoat {
    static Random randomizer = new Random();

    public static int[] playGame(int strategy) {

        int win = 0;
        int monty = 0;

        // Initialize doors
        HashMap<Integer, String> doors = new HashMap<Integer, String>();
        int numberOfDoors = 3;
        for (int i = 0; i < numberOfDoors; i++) {
            doors.put(i, "Nothing");
        }

        // Put goats behind doors
        int numberOfGoats = randomizer.nextInt(4);
        ArrayList<Integer> doorKeys = new ArrayList<>(Arrays.asList(0, 1, 2));
        Collections.shuffle(doorKeys);
        for (int i = 0; i < numberOfGoats; i++) {
            doors.put(doorKeys.get(i), "Goat");
        }

        // Put prizes behind doors
        for (HashMap.Entry<Integer, String> door : doors.entrySet()) {
            if (door.getValue() == "Nothing") {
                door.setValue("Prize");
            }
        }

        // Player chooses
        int playerKey = randomizer.nextInt(3);

        // Is Monty able to open another door?
        int montyKey = -1;
        for (HashMap.Entry<Integer, String> entry : doors.entrySet()) {
            if (entry.getValue() == "Goat" && entry.getKey() != playerKey) {
                montyKey = entry.getKey();
                break;
            }
        }

        // Suppose Monty can't open another door
        if (montyKey == -1) {
            if (doors.get(playerKey) == "Prize") {
                win = 1;
            }
        }

        // Suppose Monty CAN open another door; player chooses strategy
        else {
            monty = 1;
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

        }
        int[] result = { monty, win };
        return result;

    }

    public static double runTrials(int numberOfTrials, int strategy) {
        int wins = 0;
        int montys = 0;
        for (int i = 0; i < numberOfTrials; i++) {
            int[] result = playGame(strategy);
            montys += result[0];
            wins += result[1];
        }
        double winPercentage = wins * 1.0 / montys;
        return winPercentage;
    }

    public static void main(String[] args) {

        int numberOfTrials = 1000000;
        double switchWinRate = runTrials(numberOfTrials, 1);
        double remainWinRate = runTrials(numberOfTrials, 0);

        System.out.println("Switch: " + switchWinRate + " Remain: " + remainWinRate);

    }
}