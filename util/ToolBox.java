package com.lenovo.sap.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author yuhao5
 * @date 3/23/22
 **/
public class ToolBox {
    public static String generateNumber(String prefix,int num){
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        builder.append(LocalDateTime.now().format(formatter));
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            builder.append(random.nextInt(9));
        }
        return builder.toString();
    }
}
