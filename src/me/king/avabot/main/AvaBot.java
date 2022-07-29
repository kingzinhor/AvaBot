package me.king.avabot.main;

import me.king.avabot.commands.Basicos;
import me.king.avabot.commands.Experimental;
import me.king.avabot.commands.Help;
import me.king.avabot.commands.Services;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.EnumSet;

public class AvaBot {

    public static JDA jda;
    public static String botToken;
    public static JSONObject JsonObj;
    public static Color color;

    public static void main(String[] args) throws LoginException {

        JSONParser jsonParser = new JSONParser();

        try{
            JsonObj = (JSONObject) jsonParser.parse(new FileReader("config.json"));

            // "COLOR" is a list with 3 int values inside it
            JSONArray colorRGBJSONArray =((JSONArray) JsonObj.get("COLOR"));
            int[] colorRGBArray = { // Putting each value in this int array
                    Integer.parseInt(colorRGBJSONArray.get(0).toString()),
                    Integer.parseInt(colorRGBJSONArray.get(1).toString()),
                    Integer.parseInt(colorRGBJSONArray.get(2).toString())};

            color = new Color(colorRGBArray[0], colorRGBArray[1], colorRGBArray[2]) ;
            botToken = (String) JsonObj.get("TOKEN");

        }catch (IOException | ParseException e){
            e.printStackTrace();
        }

        jda = JDABuilder.create(botToken,
                EnumSet.allOf(GatewayIntent.class))
                .build();

        jda.addEventListener(new Experimental());
        jda.addEventListener(new Services());
        jda.addEventListener(new Help());
        jda.addEventListener(new Basicos());
    }
}
