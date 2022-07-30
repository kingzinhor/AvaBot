package me.king.avabot.commands;

import me.king.avabot.functions.*;
import me.king.avabot.classes.*;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.codec.binary.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;

public class Services extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){

        MessageReceivedContext context = new MessageReceivedContext(event);

        if (!(event.getAuthor().isBot()) && context.getPrefixUsed()){ // Preventing bots from using the command

            // This command will show you the address of the given CEP
            String[] locateCommandAliases = {
                    "locate",
                    "find",
                    "cep"
            };
            if (Useful.arrayContains(locateCommandAliases, context.getCommand()) && context.getPrefixUsed()){
                event.getChannel().sendTyping().queue();

                if (!(context.getArgs().length > 0)){ // Forcing the submission of arguments
                    Useful.sendMessage(event.getChannel(), Useful.simpleEmbed(":face_with_monocle: I can see no CEP given here...", Color.red));
                }
                else {

                    // Creating the API request URL
                    String cepUrl = String.format("https://brasilapi.com.br/api/cep/v1/%s", context.getArgs()[0]);

                    try {

                        // Turning the JSON in an object;
                        JSONObject cepJsonObject = Useful.getApiJsonObject(cepUrl);

                        String cep = (String) cepJsonObject.get("cep");
                        String state = (String) cepJsonObject.get("state");
                        String city = (String) cepJsonObject.get("city");
                        String neighborhood = (String) cepJsonObject.get("neighborhood");
                        String street = (String) cepJsonObject.get("street");

                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setAuthor(street, "https://ruas-brasil.openalfa.com", "https://cdn.discordapp.com/attachments/1000364153522901004/1002492417674584084/unknown.png")
                                .addField("__Neighborhood__", neighborhood, false)
                                .addField("__City__", city, false)
                                .addField("__State__", state, false)
                                .setFooter("Cep: " + cep)
                                .setColor(AvaBot.color);

                        Useful.sendMessage(event.getChannel(), embedBuilder.build());

                    } catch (IOException e) {
                        Useful.sendMessage(event.getChannel(), Useful.simpleEmbed(":thermometer_face: Something went wrong! Verify you wrote the CEP correctly.", Color.red));
                        throw new RuntimeException(e);
                    }
                }
            }

            String[] weatherCommandAliases = {
                    "weather",
                    "climate",
                    "time"
            };
            if (Useful.arrayContains(weatherCommandAliases, context.getCommand()) && context.getPrefixUsed()){
                event.getChannel().sendTyping().queue();

                if (!(context.getArgs().length > 0)){ // Forcing the submission of arguments
                    Useful.sendMessage(event.getChannel(), Useful.simpleEmbed(":face_with_monocle: I can see no CEP given here...", Color.red));
                } else {
                    // Creating the API request URL
                    String weatherUrl = String.format("https://api.hgbrasil.com/weather?key=%s&city_name=%s",
                            AvaBot.JsonObj.get("HGBRASIL_API_KEY").toString(),
                            String.join("%20", context.getArgs()));

                    try {

                        // Turning the JSON in an object;
                        JSONObject weatherJsonObject = Useful.getApiJsonObject(weatherUrl);

                        JSONObject results = (JSONObject) weatherJsonObject.get("results");

                        String temp = results.get("temp").toString();
                        String time = results.get("time").toString();
                        String description = results.get("description").toString();
                        String currently = results.get("currently").toString();
                        String city = results.get("city").toString();
                        String humidity = results.get("humidity").toString();
                        String wind_speedy = results.get("wind_speedy").toString();

                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setAuthor(city, "https://ruas-brasil.openalfa.com", "https://cdn.discordapp.com/attachments/1000364153522901004/1002735924263796746/unknown.png")
                                .setDescription(description)
                                .addField("__Temperature__", temp, false)
                                .addField("__Time__", Useful.capitalize(currently) + " " + time, false)
                                .addField("__Humidity__", humidity, false)
                                .addField("__Wind__", wind_speedy, false)
                                .setFooter("Api: HG API")
                                .setColor(AvaBot.color);

                        Useful.sendMessage(event.getChannel(), embedBuilder.build());

                    } catch (IOException e) {
                        Useful.sendMessage(event.getChannel(), Useful.simpleEmbed(":thermometer_face: Something went wrong! Verify you wrote the CEP correctly.", Color.red));
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
