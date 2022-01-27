package com.company;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//Bot наследник телеграмовского класса
public class Bot extends TelegramLongPollingBot {
    //выполняется при получении ботом сообщения
    @Override
    public void onUpdateReceived(Update update){
        update.getUpdateId();//обновление информации о пользователе
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        // SendMessage - класс для отправки сообщений
        // setChatId - выставляет Id человека, который написал боту
        // update.getMessage().getChatId() - Id того же человека
        if (update.getMessage().getText().equals("Привет")) {
            sendMessage.setText("Привет дружище");
            try{
                execute(sendMessage); //отправка сообщения
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    };
    //получаем имя бота
    @Override
    public String getBotUsername(){
        return "@training_cool_bot";
    };
    //получаем токен бота
    @Override
    public String getBotToken(){
        return "5291261515:AAGCzxkQEsvqrtv6s9HixF0XKcwcrmIhxF0";
    };
}
