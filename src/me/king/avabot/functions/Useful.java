package me.king.avabot.functions;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
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

public class Useful {

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
