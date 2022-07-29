package me.king.avabot.classes;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

// This class will get for me all the elements I want from the command event context
// Its utility is just simplify my code. Now I don't need more to write any of this everytime i wanna to make a command
public class MessageReceivedContext {
    private String[] content;
    private String[] args;
    private String command;
    private Boolean prefixUsed;

    public MessageReceivedContext(MessageReceivedEvent event){
        String prefix = (String) AvaBot.JsonObj.get("PREFIX");
        content = event.getMessage().getContentRaw().trim().split(" ");
        args = Arrays.copyOfRange(content, 1, content.length);

        // Some commands will not have a prefix
        // "Content[0]" will be the first thing user will say, so there is where the command will be, with or without the prefix
        if (content[0].toLowerCase().startsWith(prefix)){
            command = content[0].substring(prefix.length());
            prefixUsed = true;
        } else {
            command = content[0];
            prefixUsed = false;
        }
    }

    public Boolean getPrefixUsed() {
        return prefixUsed;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public String[] getContent() {
        return content;
    }
}
