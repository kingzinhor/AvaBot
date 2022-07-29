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
import java.lang.reflect.Array;
import java.util.Arrays;

public class Experimental extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        Context context = new Context(event);

        // Verifying if the message's author is me
        if (context.message.getAuthor().getId().equals("348664615175192577")){

            // Making a command and testing before turn it a real command
            if (context.command.equals("test")){
            }

            // This command is for see how the information is coming when using one
            if (context.command.equals("selfcommand")){
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .addField("Prefix used?", context.prefixUsed.toString(), false)
                        .addField("Command", context.command, false)
                        .addField("Args", String.join(", ", context.args), false)
                        .setColor(AvaBot.color);

                context.channel.sendMessage(new MessageBuilder().setEmbeds(embedBuilder.build()).build()).queue();
            }
        } else {
            if (!context.author.isBot()){
                context.channel.sendMessage(new MessageBuilder(Useful.simpleEmbed(":hand_splayed: Only the owner can use this command!")).build()).queue();
            }
        }
    }
}

