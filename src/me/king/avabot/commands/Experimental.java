package me.king.avabot.commands;

import me.king.avabot.functions.*;
import me.king.avabot.classes.*;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Experimental extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        MessageReceivedContext context = new MessageReceivedContext(event);

        // Making a command and testing before turn it a real command
        String[] textCommandAliases = {
                "test"
        };
        if (Useful.arrayContains(textCommandAliases, context.getCommand()) /*&& Useful.isOwner(event.getAuthor())*/){
            event.getChannel().sendTyping().queue();

            String sentence = String.join("", context.getArgs()).replaceAll("\\s+","").toLowerCase();

            if (sentence.length() <= 0){
                event.getChannel().sendMessage("No sentence given!").queue();
            }
            else {
                try{
                    Useful.sendMessage(event.getChannel(), "`" + sentence + "` =/≅ `" + Calculator.calc(sentence, event) + "`");
                }catch (Exception e){
                    Useful.sendMessage(event.getChannel(), "Invalid sentece!");
                    Useful.sendMessage(event.getChannel(), e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // This command is for see how the information is coming when using one
        String[] selfCommandAliases = {
                "self",
                "selfcommand",
                "autotest"
        };
        if (Useful.arrayContains(selfCommandAliases, context.getCommand()) && Useful.isOwner(event.getAuthor())){
            event.getChannel().sendTyping().queue();
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .addField("Prefix used?", context.getPrefixUsed().toString(), false)
                    .addField("Command", context.getCommand(), false)
                    .addField("Args", String.join(", ", context.getArgs()), false)
                    .setColor(AvaBot.color);

            Useful.sendMessage(event.getChannel(), embedBuilder.build());
        }
    }
}

