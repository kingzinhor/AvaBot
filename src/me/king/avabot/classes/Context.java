package me.king.avabot.classes;

import me.king.avabot.main.AvaBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.Console;
import java.util.Arrays;

// This class will get for me all the elements I want from the command event context
// Its utility is just simplify my code. Now I don't need more to write any of this everytime i wanna to make a command
public class Context {

    public Message message;
    public MessageChannelUnion channel;
    public String[] content;
    public String[] args;
    public String command;
    public Boolean prefixUsed;
    public User author;

    public Context(MessageReceivedEvent event){
        String prefix = (String) AvaBot.JsonObj.get("PREFIX");

        message = event.getMessage();
        channel = event.getChannel();
        content = message.getContentRaw().trim().split(" ");
        args = Arrays.copyOfRange(content, 1, content.length);

        // Some commands will not have a prefix
        // "Content[0]" will be the first thing user will say, so there is where the command will be
        if (content[0].toLowerCase().startsWith(prefix)){
            command = content[0].substring(prefix.length());
            prefixUsed = true;
        } else {
            command = content[0];
            prefixUsed = false;
        }
        author = message.getAuthor();
    }
}
