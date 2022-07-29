package me.king.avabot.commands;

import me.king.avabot.functions.*;
import me.king.avabot.classes.*;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Services extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){

        Context context = new Context(event);

        if (!(context.author.isBot()) && context.prefixUsed){ // Preventing bots from using the command

            if (context.command.equals("locate")){

                if (!(context.args.length > 0)){ // Forcing the submission of arguments
                    context.channel.sendMessage(new MessageBuilder().setEmbeds(Useful.simpleEmbed(
                        ":face_with_monocle: I can see no CEP given here...", Color.red)).build()).queue();
                }
                else {

                    // Creating the API request URL
                    String cepUrl = String.format("https://brasilapi.com.br/api/cep/v1/%s", context.args[0]);

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

                        context.channel.sendMessage(new MessageBuilder().setEmbeds(embedBuilder.build()).build()).queue();

                    } catch (IOException e) {
                        context.channel.sendMessage(new MessageBuilder(Useful.simpleEmbed("Something goes wrong! Verify you write the CEP correctly.", Color.red)).build()).queue();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
