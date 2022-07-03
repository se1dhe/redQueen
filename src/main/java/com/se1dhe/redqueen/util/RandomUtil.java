package com.se1dhe.redqueen.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUtil {

    public static List<Integer> rnd(int winnerCount, int participantCount) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < winnerCount; i++) {
            int result = r.nextInt(participantCount);
            if (!numbers.contains(result)) {
                numbers.add(result);
            }
        }
        return numbers;
    }


}
