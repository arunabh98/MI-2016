package com.iitb.moodindigo.mi2016;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darshan3 on 18/12/16.
 */

public class Utils {
    public static List<GsonModels.Event> mergeSort(List<GsonModels.Event> whole) {
        List<GsonModels.Event> left = new ArrayList<>();
        List<GsonModels.Event> right = new ArrayList<>();
        int center;

        if (whole.size() == 1)
            return whole;
        else {
            center = whole.size() / 2;
            // copy the left half of whole into the left.
            for (int i = 0; i < center; i++) {
                left.add(whole.get(i));
            }

            //copy the right half of whole into the new arraylist.
            for (int i = center; i < whole.size(); i++) {
                right.add(whole.get(i));
            }

            // Sort the left and right halves of the arraylist.
            left = mergeSort(left);
            right = mergeSort(right);


            // Merge the results back together.
            merge(left, right, whole);

        }
        return whole;
    }

    public static void merge(List<GsonModels.Event> left, List<GsonModels.Event> right,
                              List<GsonModels.Event> whole) {

        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;


        // As long as neither the left nor the right arraylist has
        // been used up, keep taking the smaller of left.get(leftIndex)
        // or right.get(rightIndex) and adding it at both.get(bothIndex).
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ((left.get(leftIndex).compareByTime(right.get(rightIndex)))) {
                whole.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                whole.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }

        List<GsonModels.Event> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            // The left arraylist has been use up...
            rest = right;
            restIndex = rightIndex;
        } else {
            // The right arraylist has been used up...
            rest = left;
            restIndex = leftIndex;
        }

        // Copy the rest of whichever arraylist (left or right) was
        // not used up.
        for (int i = restIndex; i < rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }
}
