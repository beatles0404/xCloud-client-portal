package com.lenovo.sap.api.util.DiffUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
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

public class XMLParse {

    public static ArrayList<String> innerXml(Node node) {

        ArrayList<String> innerArray = new ArrayList<>();
        DOMImplementationLS lsImpl = (DOMImplementationLS) node.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
        LSSerializer lsSerializer = lsImpl.createLSSerializer();
        NodeList childNodes = node.getChildNodes();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < childNodes.getLength(); i++) {
            innerArray.add(lsSerializer.writeToString(childNodes.item(i)));
        }
        return innerArray;
    }

    private static ArrayList<LineWithNumber> parseElementByName(String xmlPath) throws IOException, ParserConfigurationException, SAXException, CloneNotSupportedException {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(xmlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
//
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        ArrayList<LineWithNumber> lines = new ArrayList<>();

        Pattern pattern1 = Pattern.compile("name=\"([^\"]*)\"");
        Pattern pattern2 = Pattern.compile("/>$");

        Pattern annotation = Pattern.compile("^<!");

        String line = null;
        int lineCount = 1;
        boolean nameFound = false;
        String content = new String();
        String name = new String();
        ArrayList<String> contentByLines = new ArrayList<>();
        LineWithNumber lineWithNumber = new LineWithNumber();
        boolean flag = false;

        while ((line = br.readLine()) != null) {
            line=line.trim();
            Matcher matcher1 = pattern1.matcher(line);
            Matcher matcher2 = pattern2.matcher(line);


            if (matcher2.find()&&(nameFound)) {

//                flag = true;
//                content+=line;
                contentByLines.add(line);
//                lineWithNumber.setContent(content);
                lineWithNumber.setContentByLines((ArrayList)contentByLines.clone());
                LineWithNumber l = lineWithNumber.clone();
                lines.add(l);
                content = "";
                lineWithNumber = new LineWithNumber();
                nameFound = false;
                contentByLines.clear();

            } else if (nameFound) {

//                content+=line;
                contentByLines.add(line);

            } else if (matcher1.find()&&(!line.isBlank())) {

                name = matcher1.group(0);
                contentByLines.add(line);
                lineWithNumber.setContent(name);
                lineWithNumber.setLine(lineCount);
                nameFound = true;

            }


            lineCount++;
        }

//
        if(inputStream != null){
            inputStream.close();
        }
        return lines;

    }
    public static ArrayList<LineWithNumber> concate(ArrayList<LineWithNumber> lines){
        ArrayList<LineWithNumber> newLines = new ArrayList<>();
        for(LineWithNumber l: lines){

            ArrayList<String> oldLines = l.getContentByLines();
            for(int i=0;i<oldLines.size();i++){

                LineWithNumber newLine = new LineWithNumber();
                int pos = l.getLine()+i;
                newLine.setLine(pos);
                newLine.setContent(oldLines.get(i));
                newLines.add(newLine);
            }

        }
        return newLines;
    }

    public static HashMap<String, ArrayList<Integer>> diffXML(String xmlPath1, String xmlPath2) throws IOException, ParserConfigurationException, SAXException, CloneNotSupportedException {

        HashMap<String, ArrayList<Integer>> diff = new HashMap<>(2);
        ArrayList<Integer> lineNums = new ArrayList<>();
        ArrayList<Integer> lineNums2 = new ArrayList<>();

        ArrayList<LineWithNumber> lines = quickSort(parseElementByName(xmlPath1));
        ArrayList<LineWithNumber> lines2 = quickSort(parseElementByName(xmlPath2));
        ArrayList<Snake> snakes = findDiff(lines, lines2);
        ArrayList<LineWithNumber> oldName = printDiff(snakes,lines,lines2).get("Old");
        ArrayList<LineWithNumber> newName = printDiff(snakes,lines,lines2).get("New");
        for(LineWithNumber l: oldName){
            for(int i=0;i<l.getContentByLines().size();i++){
                lineNums.add(l.getLine()+i);
            }
        }
        for(LineWithNumber l: oldName){
            for(int i=0;i<l.getContentByLines().size();i++){
                lineNums2.add(l.getLine()+i);
            }
        }
        lines.removeAll(oldName);
        lines2.removeAll(newName);
        ArrayList<Snake> snakes2 = findDiff(concate(lines),concate(lines2) );
        HashMap<String, ArrayList<LineWithNumber>> map = printDiff(snakes2,concate(lines),concate(lines2));
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


    public static void main(String[] args) throws Exception {

        String xmlPath = "D:\\Lenovo_internship\\test1\\site.xconf";
        String xmlPath2 = "D:\\Lenovo_internship\\test1\\site-prd.xconf";
        System.out.println(diffXML(xmlPath,xmlPath2).get("Old"));
    }


}
