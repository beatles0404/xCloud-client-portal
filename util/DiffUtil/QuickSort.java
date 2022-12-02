package com.lenovo.sap.api.util.DiffUtil;

import java.util.ArrayList;

public class QuickSort {


    public static void swap(ArrayList<LineWithNumber> a, int i, int j)
    {
        LineWithNumber temp = a.get(i);
        a.set(i,a.get(j));
        a.set(j,temp);

    }

    public static void sorted(ArrayList<LineWithNumber> lines, int lo, int hi){
        if (lo < hi){
            int partition = partition(lines, lo, hi);
            sorted(lines, lo, partition-1);
            sorted(lines, partition +1, hi);
        }
    }

    public static int partition(ArrayList<LineWithNumber> lines, int lo, int hi){
        String pivot = lines.get(hi).getContent();

        int i=lo-1;
        for(int j=lo;j<hi;j++){
            if (lines.get(j).getContent().compareToIgnoreCase(pivot) <= 0){
                i++;
                swap(lines, i,j);

            }
        }
        swap(lines, i+1, hi);

        return i+1;
    }

    public static ArrayList<LineWithNumber> quickSort(ArrayList<LineWithNumber> lines) {

        if (lines == null) {
            return null;
        }
        sorted(lines,0, lines.size()-1);
        return lines;

    }


}
