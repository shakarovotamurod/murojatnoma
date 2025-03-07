/*
package org.example.yordamchibotim.yordamchiBotNew.entite.botServis;



import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.glassfish.grizzly.ProcessorExecutor.execute;


@Service
public class BotServis {
private static Admin admin;
    private MyAssistantTelegramBot myAssistantBot;

    public BotServis(MyAssistantTelegramBot myAssistantBot) {
        this.myAssistantBot = myAssistantBot;
    }

    public void handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            System.out.println(chatId);
            String text = update.getMessage().getText();
            System.out.println(text);
            if( text=="Pas_931666502")
                admin.edAdmin(chatId);
            sendMessage(text, chatId);
        } else if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String text = " iltimis tex xabay yozing :)";
            sendMessage(text, chatId);
        }
    }


    public void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        try {
            myAssistantBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }


}
*/
