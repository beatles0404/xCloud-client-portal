package com.lenovo.sap.api.util.DiffUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DiffUtil {

    public static ArrayList<String> readTxt(String filepath) {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filepath));
            BufferedReader br = new BufferedReader(isr);
            ArrayList<String> fileList = new ArrayList<>();
            String line = null;
            while((line=br.readLine())!= null){
                fileList.add(line);
            }
            return fileList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<Snake> findDiff(ArrayList<LineWithNumber> l1, ArrayList<LineWithNumber> l2){


        int length1 = l1.size();
        int length2 = l2.size();

        ArrayList<Snake> snakes = new ArrayList<>(Math.max(length1,length2)*4/3+1);


        int[][] operationF = ShortestEditDist(length1,length2,l1,l2);

        int i=length1, j=length2;
        while (i>0||j>0){
            Snake snake = new Snake();

            if (operationF[i][j] == 3){
                i--;
                j--;
                continue;
            }else if (operationF[i][j] == 2) {
                snake.setK(i);
                snake.setOperation("down");
                snakes.add(snake);
                i--;

            }else if(operationF[i][j] == 1) {
                snake.setK(j);
                snake.setOperation("right");
                snakes.add(snake);
                j--;
            }else {
                if(i==0){
                    snake.setK(j);
                    snake.setOperation("right");
                    snakes.add(snake);
                    j--;
                }else {
                    snake.setK(i);
                    snake.setOperation("down");
                    snakes.add(snake);
                    i--;
                }
            }
        }

        return snakes;
    }




    public static int[][] ShortestEditDist(int m, int n, ArrayList<LineWithNumber> l1, ArrayList<LineWithNumber> l2){

        int[][] f = new int[m+1][n+1];
        int[][] operationF = new int[m+1][n+1];

        //0 - init; 1 - right; 2 - down; 3 - diagonal
        for (int i = 1; i <= m; i++) {
            operationF[i][0]=0;
            f[i][0] = i;
        }

        for (int j = 1; j <= n; j++) {
            operationF[0][j]=0;
            f[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (!(l1.get(i - 1).getContent()).equals(l2.get(j - 1).getContent())) {
                    if (f[i - 1][j]<f[i][j - 1]){
                        operationF[i][j] = 2;
                        f[i][j] = f[i - 1][j]+1;
                    }else{
                        operationF[i][j] = 1;
                        f[i][j] = f[i][j-1]+1;
                    }

                } else {
                    operationF[i][j] = 3;
                    f[i][j] = f[i - 1][j - 1];
                }
            }
        }

        return operationF;
    }

    public static HashMap<String, ArrayList<LineWithNumber>> printDiff(ArrayList<Snake> snakes,ArrayList<LineWithNumber> l1, ArrayList<LineWithNumber> l2){
        HashMap<String, ArrayList<LineWithNumber>> diff = new HashMap<>(2);
        ArrayList<LineWithNumber> diffOfOld = new ArrayList<>(l2.size()*4/3+1);
        ArrayList<LineWithNumber> diffOfNew = new ArrayList<>(l1.size()*4/3+1);
        for(int i=snakes.size()-1;i>=0;i--){
            Snake snake = snakes.get(i);
            String operation = snake.getOperation();
            if (operation=="down"){
                diffOfNew.add(l1.get(snake.getK()-1));

            } else if (operation=="right") {
                diffOfOld.add(l2.get(snake.getK()-1));
            }
        }

        diff.put("New", diffOfOld);
        diff.put("Old", diffOfNew);

        return diff;
    }


}

