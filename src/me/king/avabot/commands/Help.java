package me.king.avabot.commands;

import me.king.avabot.classes.MessageReceivedContext;
import me.king.avabot.functions.Useful;
import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Help extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        MessageReceivedContext context = new MessageReceivedContext(event);

        String[] gihubCommandAliases = {
                "github",
                "git",
                "repository"
        };
        if (Useful.arrayContains(gihubCommandAliases, context.getCommand())){
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("Github repository", "https://www.github.com/kingzinhor/AvaBot", "https://cdn.discordapp.com/attachments/1000364153522901004/1002558982868717608/unknown.png")
                    .setDescription("Hi! You can access my public Github repository by clicking up there, or accessing this link: https://www.github.com/kingzinhor/AvaBot")
                    .setFooter("By King", "https://media.discordapp.net/attachments/1000364153522901004/1002558433075151048/2b104ee7173622c6bd5a8652c45f8bc6.png")
                    .setColor(AvaBot.color)
                    .build();

            Useful.sendMessage(event.getChannel(), embed);
        }
    }
}
