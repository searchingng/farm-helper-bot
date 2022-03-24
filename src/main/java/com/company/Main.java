package com.company;

import com.company.repository.Repository;
import com.company.repository.RepositoryDB;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TelegramApiException {

//        try {
//            Repository.load();
//            RepositoryDB.saveAll();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new FarmBot());
    }
}
