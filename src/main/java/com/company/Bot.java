package com.company;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

//Bot наследник телеграмовского класса
public class Bot extends TelegramLongPollingBot {
    Book book = new Book();
    private long chat_id;
    //выполняется при получении ботом сообщения
    @Override
    public void onUpdateReceived(Update update){
        update.getUpdateId();//обновление информации о пользователе
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        // SendMessage - класс для отправки сообщений
        // setChatId - выставляет Id человека, который написал боту
        // update.getMessage().getChatId() - Id того же человека
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(input(update.getMessage().getText()));
        try{
            execute(sendMessage); //отправка сообщения
        }catch (TelegramApiException e){
            e.printStackTrace();
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

    public String input(String msg){
        if(msg.contains("Hi") | msg.contains("Hello") | msg.contains("Привет")){
            return "Привет земляне!)";
        }
        if(msg.contains("Информация о книге")){
            return getInfoBook();
        }
        return msg;
    }

    public String getInfoBook(){
        SendPhoto sendPhotoRequest = new SendPhoto();

        try (InputStream in = new URL(book.getImg()).openStream()){
            Files.copy(in, Paths.get("~/Документы/prj_2022_mono_course/git_repositories/srgBook"));
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(new File("~/Документы/prj_2022_mono_course/git_repositories/srgBook"));
            execute(sendPhotoRequest);
            Files.delete(Paths.get("~/Документы/prj_2022_mono_course/git_repositories/srgBook"));
        } catch (IOException ex){
            System.out.println("File not found");
        } catch (TelegramApiException e){
            e.printStackTrace();
        }

        String info = book.getTitle()
                + "\n автор " + book.getAuthorName()
                + "\n жанр " + book.getGeners()
                + "\n\n описание \n" + book.getDescription()
                + "\n\n Количество лайков " + book.getLikes()
                + "\n\n последние комментарии \n" + book.getCommentList();
            return info;
    }
}
