package com.company.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.security.Key;
import java.util.Arrays;

public class MarkupUtil {
    public static KeyboardButton button(String text){
        KeyboardButton button = new KeyboardButton();
        button.setText(text);
        return button;
    }

    public static KeyboardRow row(KeyboardButton... buttons){
        KeyboardRow row = new KeyboardRow();
        for (KeyboardButton b : buttons){
            row.add(b);
        }
        return row;
    }

    public static ReplyKeyboardMarkup markup(KeyboardRow... rows){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(Arrays.asList(rows));
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        return markup;
    }

    public static ReplyKeyboardMarkup mainMarkup(){
        KeyboardButton b1 = button("➕ Xarajat qo'shish");
        KeyboardButton b2 = button("📜 Xarajatlar ro'yxati");
        KeyboardButton b3 = button("🧮 Nomlar bo'yicha hisoblash");
        KeyboardButton b4 = button("🌦️ Ob-havoni bilish");

        KeyboardRow r1 = row(b1);
        KeyboardRow r2 = row(b2);
        KeyboardRow r3 = row(b3);
        KeyboardRow r4 = row(b4);

        ReplyKeyboardMarkup markup = markup(r1, r2, r3, r4);
        return markup;
    }

    public static ReplyKeyboardMarkup listMarkup(){
        KeyboardButton b1 = button("🔢 Qo'shish tartibi bo'yicha");
        KeyboardButton b2 = button("📆 Sana bo'yicha");
        KeyboardButton b3 = button("🈯 Excel faylini olish");
        KeyboardButton b4 = button("⬅️ Orqaga");

        KeyboardRow r1 = row(b1);
        KeyboardRow r2 = row(b2);
        KeyboardRow r3 = row(b3);
        KeyboardRow r4 = row(b4);

        ReplyKeyboardMarkup markup = markup(r1, r2, r3, r4);
        return markup;
    }

    public static ReplyKeyboardMarkup plusMarkup(){
        KeyboardButton b1 = button("👝 Mineral o'g'it");
        KeyboardButton b2 = button("🚜 Yer xaydash");
        KeyboardButton b3 = button("⛽ Sarflangan yoqilg'i");
        KeyboardButton b4 = button("👷‍♂️ Ishchilarga");
        KeyboardButton b5 = button("🌱 Urug' uchun");
        KeyboardButton b6 = button("🐛 Gerbitsid");
        KeyboardButton b7 = button("🦼 Texnika uchun");
        KeyboardButton b8 = button("➕ Boshqa xarajat");
        KeyboardButton b9 = button("🔙 Orqaga");

        KeyboardRow r1 = row(b1, b2);
        KeyboardRow r2 = row(b3, b4);
        KeyboardRow r3 = row(b5, b6);
        KeyboardRow r4 = row(b7, b8);
        KeyboardRow r5 = row(b9);

        ReplyKeyboardMarkup markup = markup(r1, r2, r3, r4, r5);
        markup.setInputFieldPlaceholder("Xarajat turini tanlang.");
        return markup;
    }

    public static ReplyKeyboardMarkup weatherMarkup(){
        KeyboardButton b1 = button("🌧️ Bugungi ob-havo");
        KeyboardButton b2 = button("☔ Bir haftalik ob-havo");
        KeyboardButton b3 = button("🗺️ Lokatsiya jo'natish");
        KeyboardButton b4 = button("⬅️ Orqaga");

        KeyboardRow r1 = row(b1);
        KeyboardRow r2 = row(b2);
        KeyboardRow r3 = row(b3);
        KeyboardRow r4 = row(b4);

        b3.setRequestLocation(true);

        ReplyKeyboardMarkup markup = markup(r1, r2, r3, r4);
        return markup;
    }
}
