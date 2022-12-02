package com.lenovo.sap.api.util.DiffUtil;

import java.util.ArrayList;

public class LineWithNumber implements Cloneable{

    int line;
    String content;
    String name;
    ArrayList<String> contentByLines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getContentByLines() {
        return contentByLines;
    }

    public void setContentByLines(ArrayList<String> contentByLines) {
        this.contentByLines = contentByLines;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LineWithNumber clone() throws CloneNotSupportedException{
        LineWithNumber l = (LineWithNumber) super.clone();
        return l;
    }
}

