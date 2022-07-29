package me.king.avabot.commands;

import me.king.avabot.classes.MessageReceivedContext;
import me.king.avabot.functions.Useful;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Basics extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){

        MessageReceivedContext context = new MessageReceivedContext(event);

        String[] pingCommandAliases = {
                "ping",
                "latency",
                "ms"
        };
        if (Useful.arrayContains(pingCommandAliases, context.getCommand()) && context.getPrefixUsed()){
            event.getChannel().sendTyping().queue();
            Useful.sendMessage(event.getChannel(), Useful.simpleEmbed(":stopwatch: Latency: **" + event.getJDA().getGatewayPing() + "**ms"));
        }
    }
}
