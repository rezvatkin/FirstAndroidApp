package com.example.firsttry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetJokes {
    int i; //количесто шуток

    public GetJokes(int i) {
        this.i = i;
    }

    public String getJokes(){
        StringBuffer responce = new StringBuffer(); //переменная для ответа сервера
        try{
            String urllString = "http://api.icndb.com/jokes/random/" + i; //склеиваем строку ждя запроса
            System.out.println(urllString);
            URL urlObject = new URL(urllString); //создаем объект
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection(); //создаем соединение
            connection.setRequestMethod("GET"); //выбираем тип запрома
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); //тут что то про данные которые мы можем принять
            System.out.println(1);
            int responseCode = connection.getResponseCode(); //записываем ответ сервера
            System.out.println(responseCode);
            if (responseCode == 404){
                System.out.println("Что то не то");
                throw new IllegalArgumentException();
            }
            System.out.println(2);
            // создаём поток, вычитываем все строки, и склеиваем в одну большую строку,
            //которую будем потом обрабатывать в других методах
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null){
                responce.append((inputLine));
            }
            in.close();

        }   catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responce.toString();
    }

    public String showJokes(String jokes) throws IOException {
        List<String> jokesList = new ArrayList<>();
        StringBuilder jokeOutput = new StringBuilder();
        JsonNode arrNode = new ObjectMapper().readTree(jokes).get("value");
        if(arrNode.isArray()){
            for (final JsonNode objNode : arrNode){
                jokesList.add(objNode.get("joke").toString());
                System.out.println(objNode.get("joke").toString());
            }
        }
        for (String line : jokesList){

            String oneliner = line;

            jokeOutput.append(oneliner + '\n');
        }
        return jokeOutput.toString();
    }
}
