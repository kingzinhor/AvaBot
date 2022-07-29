package me.king.avabot.functions;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Useful {

    // Verifies if the message's author is me
    public static Boolean isOwner(User user){
        return user.getId().equals("348664615175192577");
    }





    // Return an embed with a description and a default color if not specified
    public static MessageEmbed simpleEmbed(String text){
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription(text)
                .setColor(AvaBot.color);

        return embed.build();
    }
    public static MessageEmbed simpleEmbed(String text, Color color){
    EmbedBuilder embed = new EmbedBuilder()
            .setDescription(text)
            .setColor(color);

        return embed.build();
    }





    // Traditional mode of sending messages with JDA is pretty mad, so I made this function that only need the channel and the text/embed
    public static void sendMessage(MessageChannelUnion channel, String text){
        channel.sendMessage(text).queue();
    }
    public static void sendMessage(MessageChannelUnion channel, MessageEmbed embed){
        channel.sendMessage(new MessageBuilder().setEmbeds(embed).build()).queue();
    }





    // Checks if an array contains or not some value inside it
    public static Boolean arrayContains(String[] array, String key){
        for (String item : array) {
            if (item.equals(key)){ return true; }
        }
        return false;
    }
    public static Boolean arrayContains(int [] array, int key){
        for (int item : array){
            if (item == key){ return true; }
        }
        return false;
    }





    // This function make all the work of getting data from a RESTFUL API
    public static JSONObject getApiJsonObject(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String jsonString; // JSON code will be here

        try {
            HttpGet httpGet = new HttpGet(url);

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();

                    //Checking if the status code represents success
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            // This will be the code of the JSON response we will get
            jsonString = httpClient.execute(httpGet, responseHandler);
            JSONParser jsonParser = new JSONParser();

            return (JSONObject) jsonParser.parse(jsonString); // Converting the JSON string to a JSONObject

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
