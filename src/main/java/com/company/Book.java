package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Book {
    private Document document;
    public Book (){
        connect();
    }

    //подключаемся к странице
    private void connect(){
        try{
            document = Jsoup.connect("https://www.surgebook.com/Jane_Miltrom/book/dark").get();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //получаем название обложки
    public String getTitle(){
        return document.title();
    }
    //смотрим сколько лайков
    //связываемся с сайтом и там прямо из html достаем все нужное
    //иногда есть id иногда просто имя класса, по идее надо учитывать все
    public String getLikes(){
        Element element = document.getElementById("likes");
        return element.text();
    }

    //читаем описание книги
    public String getDescription(){
        Element element = document.getElementById("description");
        return element.text();
    }

    //вытаскиваем жанры
    public String getGeners(){
        Elements elements = document.getElementsByClass("genres d-block");
        return ((org.jsoup.select.Elements) elements).text();
    }

    //последние комментарии
    public String getCommentList(){
        Elements elements = document.getElementsByClass("comment_mv1_item");
        String coment = elements.text();
        coment = coment.replaceAll("Ответить", "\n\n");
        coment = coment.replaceAll("Нравится", "");
        coment = coment.replaceAll("\\d{4}-\\d{2}-\\d{2}", "");
        coment = coment.replaceAll("\\d{2}:\\d{2}:\\d{2}", "");
        return coment;
    }

    //обложка книги
    public String getImg(){
        Elements elements = document.getElementsByClass("cover-book");
        String url = elements.attr("style");
        url = url.replace("background-image: url('", "");
        url = url.replace("'):", "");
        return url;
    }

    //имя автора
    public String getAuthorName(){
        Elements elements = document.getElementsByClass("text-decoration-none column-author-name bold max-w-140 text-overflow-ellipsis");
        return elements.text();
    }
}
