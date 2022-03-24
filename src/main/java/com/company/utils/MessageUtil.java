package com.company.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class MessageUtil {

    public static SendMessage sendMessage(Message message, String text, ReplyKeyboardMarkup markup){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(markup);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    public static SendMessage sendMessage(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

}
