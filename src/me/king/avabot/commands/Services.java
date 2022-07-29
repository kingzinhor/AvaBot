package me.king.avabot.commands;

import me.king.avabot.functions.*;
import me.king.avabot.classes.*;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;

public class Services extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){

        MessageReceivedContext context = new MessageReceivedContext(event);

        if (!(event.getAuthor().isBot()) && context.getPrefixUsed()){ // Preventing bots from using the command

            if (context.getCommand().equals("locate")){

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
        }
    }
}
