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
            event.getChannel().sendTyping().queue();
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("Github repository", "https://www.github.com/kingzinhor/AvaBot", "https://cdn.discordapp.com/attachments/1000364153522901004/1002558982868717608/unknown.png")
                    .setDescription("Hi! You can access my public Github repository by clicking up there, or accessing this link: https://www.github.com/kingzinhor/AvaBot")
                    .setFooter("By King", "https://media.discordapp.net/attachments/1000364153522901004/1002558433075151048/2b104ee7173622c6bd5a8652c45f8bc6.png")
                    .setColor(AvaBot.color)
                    .build();

            Useful.sendMessage(event.getChannel(), embed);
        }

        String[] helCommandAliases = {
                "help",
                "commands",
                "info"
        };
        if (Useful.arrayContains(helCommandAliases, context.getCommand()) && context.getPrefixUsed()){
            event.getChannel().sendTyping().queue();
            MessageEmbed embed = new EmbedBuilder()
                    .setAuthor("Help", null, "https://cdn.discordapp.com/attachments/1000364153522901004/1002718441108688917/unknown.png")
                    .setDescription("Hi, im Ava! I'm a Java discord bot made by <@348664615175192577> for learning purpose. You can see some of my commands down bellow:")
                    .addField("Information",
                            "• `Help` - shows my commands and basic information." +
                                "\n• `Github` - Shows my public Github repository.",
                            false)
                    .addField("Basics",
                            "• `Ping` - show latency in ms",
                            false)
                    .addField("Services",
                            "• `locate` - give a CEP, and it will show you the localization",
                            false)
                    .addField("Experimentals",
                            "• `test` - that's an exclusive admin command. It's used to do what its name says, testing" +
                                "\n• `self` - another exclusive admin command. Used to inform how the bot is recognizing the information. Useful for tests",
                            false)
                    .setColor(AvaBot.color)
                    .build();

            Useful.sendMessage(event.getChannel(), embed);

        }
    }
}
