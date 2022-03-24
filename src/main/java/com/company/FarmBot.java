package com.company;

import com.company.repository.Repository;
import com.company.repository.RepositoryDB;
import com.company.utils.CheckingUtil;
import com.company.utils.ExcelUtil;
import com.company.utils.MarkupUtil;
import com.company.utils.MessageUtil;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class FarmBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Useful_Calculation_bot";
    }

    @Override
    public String getBotToken() {
        return "5076236211:AAE4RTlpHsM_2lTMn0fRQs49iboRJU8qEzw";
    }

    String old;
    Manager manager = new Manager();

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            System.out.println(message.getFrom().getFirstName() + " - " + text);
            SendMessage sendMessage = new SendMessage();

            if (message.hasLocation()){
                Location loc = message.getLocation();
                WeatherManager.setLATITUDE(loc.getLatitude());
                WeatherManager.setLONGITUDE(loc.getLongitude());
                String res = "Lokatsiya sozlandi.";
                sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.weatherMarkup());
                send(sendMessage);
            }

            switch (text) {
                case "/start":
                    String answer = "Assalomu aleykum <b>" + message.getFrom().getUserName() + "</b>, " +
                            "botga xush kelibsiz. Bu bot fermerlarga mo'ljallangan bo'lib, xarajat ishlarini " +
                            "hisoblashda 📝 yordam beradi.";
                    sendMessage = getSendMessage(message, answer, MarkupUtil.mainMarkup());
                    break;

                case "➕ Xarajat qo'shish":
                    sendMessage = getSendMessage(message, "✔️ Xarajat turini tanlang.", MarkupUtil.plusMarkup());
                    break;

                case "🔙 Orqaga":
                    sendMessage = getSendMessage(message, "<b><i>Asosiy Menu</i></b>", MarkupUtil.mainMarkup());
                    break;

                case "⬅️ Orqaga":
                    sendMessage = getSendMessage(message, "<b><i>Asosiy Menu</i></b>", MarkupUtil.mainMarkup());
                    break;

                case "➕ Boshqa xarajat":
                    old = "✏️ Xarajat nomini kiriting.";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "📜 Xarajatlar ro'yxati":
                    String res = "Ro'yxat turini tanlang.";
                            //"<b>Xarajatlar ro'yxati</b>\n" + manager.getStringList(message.getChatId());
                    sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.listMarkup());
                    break;

                case "🔢 Qo'shish tartibi bo'yicha":
                    res = "<b>Xarajatlar ro'yxati</b>\n" + manager.getStringList(message.getChatId());
                    sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.listMarkup());
                    break;

                case "📆 Sana bo'yicha":
                    res = "<b>Xarajatlar ro'yxati</b>\n" + manager.getStringListByDate(message.getChatId());
                    sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.listMarkup());
                    break;

                case "👝 Mineral o'g'it":
                    old = "✏️ O'g'it nomini kiriting.";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "⛽ Sarflangan yoqilg'i":
                    old = "Yoqilg'i nomini kiriting";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "👷‍♂️ Ishchilarga":
                    old = "Xarajat nomini kiriting";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "🌱 Urug' uchun":
                    old = "Urug' nomini kiriting";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "🐛 Gerbitsid":
                    old = "Gerbitsid nomini kiriting";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "🦼 Texnika uchun":
                    old = "Texnika nomini kiriting";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "🚜 Yer xaydash":
                    old = "✏️ Yer haydash uchun sarflangan pulni kiriting.";
                    sendMessage = MessageUtil.sendMessage(message, old);
                    break;

                case "🧮 Nomlar bo'yicha hisoblash":
                    res = "Qaysi Mahsulot turi bo'yicha hisoblasin.";
                    sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.plusMarkup());
                    break;

                case "🌦️ Ob-havoni bilish":
                    res = "👆 Menyudan birini tanlang";
                    sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.weatherMarkup());
                    break;

                case "🌧️ Bugungi ob-havo":
                    res = WeatherManager.getWeatherByIndex(WeatherManager.LATITUDE, WeatherManager.LONGITUDE);
                    sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.weatherMarkup());
                    break;

                case "☔ Bir haftalik ob-havo":
                    res = WeatherManager.getForecastWeekly(WeatherManager.LATITUDE, WeatherManager.LONGITUDE);
                    sendMessage = MessageUtil.sendMessage(message, res, MarkupUtil.weatherMarkup());
                    break;

                case "🈯 Excel faylini olish":
                    File file = ExcelUtil.creatingXLSX(message.getChatId());
                    SendDocument sendDocument = new SendDocument();
                    sendDocument.setChatId(message.getChatId().toString());
                    InputFile inputFile = new InputFile();
                    inputFile.setMedia(file);
                    sendDocument.setDocument(inputFile);
                    sendDocument.setCaption("Xarajatlar ro'yxati fayli (XLSX)");
                    try {
                        execute(sendDocument);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    return;

                default:
                    switch (old) {
                        case "✏️ Yer haydash uchun sarflangan pulni kiriting.":
                            Info info;
                            if (!CheckingUtil.isNumber(text)){
                                old = "✏️ Pulni qaytadan kiriting";
                                sendMessage = MessageUtil.sendMessage(message, old);
                            } else {
                                info = manager.createInfo();
                                info.setName("Yer haydash");
                                info.setQuantity(0d);
                                old = "✔ Xarajat muvaffaqiyatli qo'shildi!";
                                sendMessage = MessageUtil.sendMessage(message, old);
                                finishInfo(message);
                            }
                            break;

                        case "✏️ Pulni qaytadan kiriting":
                            if (!CheckingUtil.isNumber(text)){
                                sendMessage = MessageUtil.sendMessage(message, old);
                            } else {
                                info = manager.createInfo();
                                info.setName("Yer haydash");
                                info.setQuantity(0d);
                                info.setUnit("");
                                old = "✔ Xarajat muvaffaqiyatli qo'shildi!";
                                sendMessage = MessageUtil.sendMessage(message, old);
                                finishInfo(message);
                            }
                            break;

                        case "✏️ O'g'it nomini kiriting.":
                            info = manager.createInfo();
                            info.setName("Mineral o'g'it(<i>" + text + "</i>)");
                            old = "Miqdorni kiriting. [Masalan: 10 litr]";
                            sendMessage = MessageUtil.sendMessage(message, old);
                            break;

                        case "Urug' nomini kiriting":
                            info = manager.createInfo();
                            info.setName("Urug'(<i>" + text + "</i>)");
                            old = "Miqdorni kiriting. [Masalan: 10 litr]";
                            sendMessage = MessageUtil.sendMessage(message, old);
                            break;

                        case "Texnika nomini kiriting":
                            info = manager.createInfo();
                            info.setName("Texnika uchun(<i>" + text + "</i>)");
                            old = "Miqdorni kiriting. [Masalan: 10 litr]";
                            sendMessage = MessageUtil.sendMessage(message, old);
                            break;

                        case "Gerbitsid nomini kiriting":
                            info = manager.createInfo();
                            info.setName("Gerbitsid(<i>" + text + "</i>)");
                            old = "Miqdorni kiriting. [Masalan: 10 litr]";
                            sendMessage = MessageUtil.sendMessage(message, old);
                            break;

                        case "Yoqilg'i nomini kiriting":
                            info = manager.createInfo();
                            info.setName("Yoqlg'i(<i>" + text + "</i>)");
                            old = "Miqdorni kiriting. [Masalan: 10 litr]";
                            sendMessage = MessageUtil.sendMessage(message, old);
                            break;

                        case "Xarajat nomini kiriting":
                            info = manager.createInfo();
                            info.setName("Ishchilar uchun (<i>" + text + "</i>)");
                            old = "Miqdorni kiriting. [Masalan: 10 litr]";
                            sendMessage = MessageUtil.sendMessage(message, old);
                            break;

                        case "✏️ Xarajat nomini kiriting.":
                            info = manager.createInfo();
                            info.setName(text);
                            old = "Miqdorni kiriting. [Masalan: 10 litr]";
                            sendMessage = MessageUtil.sendMessage(message, old);
                            break;
                        case "Miqdorni kiriting. [Masalan: 10 litr]":
                            String[] div = text.split(" ");
                            if (!CheckingUtil.isNumber(div[0])){
                                old = "Miqdorni qaytadan kiriting.";
                                sendMessage = MessageUtil.sendMessage(message, old);
                            } else {
                                manager.getCurrent().setQuantity(Double.parseDouble(div[0]));
                                manager.getCurrent().setUnit(div[1]);
                                old = "Xarajat summasini kiriting.";
                                sendMessage = MessageUtil.sendMessage(message, old);
                            }
                            break;

                        case "Miqdorni qaytadan kiriting.":
                            div = text.split(" ");
                            if (!CheckingUtil.isNumber(div[0])){
                                old = "Miqdorni qaytadan kiriting.";
                                sendMessage = MessageUtil.sendMessage(message, old);
                            } else {
                                manager.getCurrent().setQuantity(Double.parseDouble(div[0]));
                                manager.getCurrent().setUnit(div[1]);
                                old = "Xarajat summasini kiriting.";
                                sendMessage = MessageUtil.sendMessage(message, old);
                            }
                            break;

                        case "Xarajat summasini kiriting.":
                            if (!CheckingUtil.isNumber(text)){
                                old = "Summani qaytadan kiriting.";
                                sendMessage = MessageUtil.sendMessage(message, old);
                            } else {
                                old = "✔ Xarajat muvaffaqiyatli qo'shildi!";
                                sendMessage = MessageUtil.sendMessage(message, old);
                                finishInfo(message);
                            }
                            break;

                        case "Summani qaytadan kiriting.":
                            if (!CheckingUtil.isNumber(text)){
                                sendMessage = MessageUtil.sendMessage(message, old);
                            } else {
                                old = "✔ Xarajat muvaffaqiyatli qo'shildi!";
                                sendMessage = MessageUtil.sendMessage(message, old);
                                finishInfo(message);
                            }
                            break;
                    }
                break;
            }

            send(sendMessage);
            return;

        }
    }

    public void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void finishInfo(Message message){
        String text = message.getText();
        manager.getCurrent().setAmount(Double.parseDouble(text));
        manager.getCurrent().setDate(LocalDateTime.now());
        manager.getCurrent().setUserId(message.getChatId());
        System.out.println(manager.getCurrent().toString());
        try {
            Repository.append(manager.getCurrent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        RepositoryDB.save(manager.getCurrent());
    }

    public static SendMessage getSendMessage(Message message, String text, ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(markup);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }
}
