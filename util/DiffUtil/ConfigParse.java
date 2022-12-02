package com.lenovo.sap.api.util.DiffUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lenovo.sap.api.util.DiffUtil.DiffUtil.findDiff;
import static com.lenovo.sap.api.util.DiffUtil.DiffUtil.printDiff;
import static com.lenovo.sap.api.util.DiffUtil.QuickSort.quickSort;

public class ConfigParse {
    public static ArrayList<LineWithNumber> parse(String filepath) {

        try {
            InputStream inputStream = new FileInputStream(filepath);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            ArrayList<LineWithNumber> lines = new ArrayList<>();

            Pattern pattern = Pattern.compile("^\\#");

            String line = null;
            int lineCount = 1;

            while ((line = br.readLine()) != null) {
                line=line.trim();
                Matcher matcher = pattern.matcher(line);

                if (!matcher.find()&&(!line.isBlank())){
                    LineWithNumber lineWithNumber = new LineWithNumber();
                    lineWithNumber.setLine(lineCount);
                    lineWithNumber.setContent(line);
                    lines.add(lineWithNumber);
                }
                lineCount++;
            }

            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, ArrayList<Integer>> diffConfig(String configPath1, String configPath2){
        HashMap<String, ArrayList<Integer>> diff = new HashMap<>(2);
        ArrayList<Integer> lineNums = new ArrayList<>();
        ArrayList<Integer> lineNums2 = new ArrayList<>();

        ArrayList<LineWithNumber> lines = quickSort(parse(configPath1));
        ArrayList<LineWithNumber> lines2 = quickSort(parse(configPath2));
        ArrayList<Snake> snakes = findDiff(lines, lines2);
        HashMap<String, ArrayList<LineWithNumber>> map = printDiff(snakes,lines,lines2);
        for(LineWithNumber l: map.get("Old")){
            lineNums.add(l.getLine());
        }
        for(LineWithNumber l: map.get("New")){
            lineNums2.add(l.getLine());
        }
        Collections.sort(lineNums);
        Collections.sort(lineNums2);
        diff.put("Old",lineNums);
        diff.put("New",lineNums2);
        return diff;
    }


    public static void main(String[] args) {
        String filePath1 = "D:\\Lenovo_internship\\test1\\lenovo-runtime-config.properties";
        String filePath2 = "D:\\Lenovo_internship\\test1\\lenovo-runtime-config1.txt";
        System.out.println(diffConfig(filePath1,filePath2).get("Old"));
    }



}
