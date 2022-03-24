package com.company;

import com.company.repository.RepositoryDB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Manager {
    public static LinkedList<Info> infoList = new LinkedList<>();

    public Info createInfo(){
        Info info = new Info();
        infoList.add(info);
        return info;
    }

    public Info getCurrent(){
        return infoList.getLast();
    }

    public String getStringList(Long userId){
        Double sum = 0d;
        int i = 1;
        String s = "";
        for (Info info : RepositoryDB.getInfoListById(userId)){
            try {
                if (userId.equals(info.getUserId())) {
                    s += (i++) + ". " + info.toString() + "\n";
                    sum += info.getAmount();
                }
            } catch (RuntimeException e){
                i--;
            }
        }
        s += "- - - - - - - - - - - - - - - - - - \n";
        s += "<b>Jami</b>: <i>" + DTOS(sum) + " </i>so'm\n";
        return s;
    }

    public String getStringListByDate(Long userId){
        LinkedList<Info> newList = new LinkedList<>(RepositoryDB.getInfoListById(userId));
        newList.sort(new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        Double sum = 0d;
        String s = "";
        LocalDate date = newList.getFirst().getDate().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        s += "\n<b>" + date.format(formatter) + "</b>\n";

        for (Info info : newList){
            try {

                if (userId.equals(info.getUserId())) {
                    if (!date.isEqual(info.getDate().toLocalDate())){
                        date = info.getDate().toLocalDate();
                        s += "- - - - - - - - - - - - - - - - - - \n";
                        s += "<b>Jami</b>: <i>" + DTOS(sum) + " </i>so'm\n";
                        s += "\n<b>" + date.format(formatter) + "</b>\n";
                        sum = 0d;
                    }
                    s += info.toString() + "\n";
                    sum += info.getAmount();
                }

            } catch (RuntimeException e){

            }
        }
        s += "- - - - - - - - - - - - - - - - - - \n";
        s += "<b>Jami</b>: <i>" + DTOS(sum) + " </i>so'm\n";
        return s;
    }

    public static String DTOS(Double num){  //132.155
        String result = "";
        String p1 = String.valueOf(num.longValue()); //132
        if (num - num.longValue() == 0)
            return p1;

        String p2 = String.valueOf(num - num.longValue());  //0.155
        p2 = p2.substring(1); //.155
        result = p1 + p2;

        return result;
    }

    public static List<Info> getUsersInfo(Long userId){
        List<Info> list = new LinkedList<>();
        for (Info i : infoList){
            if (i.getUserId().equals(userId)){
                list.add(i);
            }
        }
        return list;
    }
}
