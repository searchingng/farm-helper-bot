package com.company.repository;

import com.company.Info;
import com.company.Manager;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Repository {
    public static void append(Info info) throws IOException {
        File file = new File("resources/list.txt");
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter writer = new PrintWriter(fileWriter);

        String line = infoToString(info);
        writer.println(line);

        writer.flush();
        writer.close();
    }

    public static void update() throws IOException {
        File file = new File("resources/list.txt");

        if (!file.exists()){
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter writer = new PrintWriter(fileWriter);

        String line;
        for (Info i : Manager.infoList){
            line = infoToString(i);
            writer.println(line);
        }

        writer.flush();
        writer.close();
    }

    public static void load() throws IOException {
        File file = new File("resources/list.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        String line = reader.readLine();
        while(line != null) {
            Manager.infoList.add(stringToInfo(line));
            line = reader.readLine();
        }

        reader.close();
    }

    public static String infoToString(Info info){
        String s = "";
        s = String.join("#", info.getUserId().toString(), info.getName(),
                info.getQuantity().toString(), info.getUnit(), info.getAmount().toString(),
                info.getStringDate());
        return s;
    }

    public static Info stringToInfo(String line){
        String[] words = line.split("#");
        Info info = new Info();
        info.setUserId(Long.parseLong(words[0]));
        info.setName(words[1]);
        info.setQuantity(Double.parseDouble(words[2]));
        info.setUnit(words[3]);
        info.setAmount(Double.parseDouble(words[4]));
        info.setStringDate(words[5]);
        return info;
    }
}
