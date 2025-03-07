package org.example.yordamchibotim.yordamchiBotNew.entite;


import org.jvnet.hk2.annotations.Service;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Service
public class MyAssistantTelegramBot extends TelegramLongPollingBot {
    private static long chatIdAdmin;
    private static final Map<Long, Long> adminReplyMap = new HashMap<>(); // Admin kimga javob yozayotganini saqlaydi

    /*
       public MyAssistantTelegramBot(TelegramBotsApi telegramBotsApi) {
        this.botServics=botServics;
    }  */

    @Override
    public String getBotUsername() {
        return "@soriybot";
    }

    public MyAssistantTelegramBot(TelegramBotsApi telegramBotsApi) throws TelegramApiException {
        super("6516572320:AAFeNKMObwr01bwEgmAHxdlkDtad5nEYlsI");
        telegramBotsApi.registerBot(this);
    }

    public void onUpdateReceived(Update update) {
        handleCallbackQuery(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            String firsname = update.getMessage().getFrom().getFirstName();
            String username = update.getMessage().getFrom().getUserName();
            if (username == null) username = "Yo'q";

            if (chatId == chatIdAdmin) {
                getMessageAdmin(text);
            }
            if (text.equals("Pas_12042002")) {
                chatIdAdmin = chatId;
                String textt = "Men siz uchun Hizmat qilishiman husandman ;) \n mendan foydalangan foydalanuvchilar xabarini sizga yuboraman!";
                sendMessage(textt, chatId);
            } else if (chatId != chatIdAdmin) setMessageAdmin(firsname,username, text, chatId);

        } else if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            // chatIdAdmin = chatId;
            String textt = "Iltimis text xabar yuboring!";
            sendMessage(textt, chatId);
        }
    }

    public void sendMessage(String text, long chatId) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(InlineKeyboardMarkup markup,String text, long chatId) {
        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        message.setReplyMarkup(markup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

   /* public void sedMessegAdmin(String firsName ,String userName, String text, long chatId) {
        // Admin chatiga xabar yuborish
        String messageToAdmin = "📩 Yangi xabar!\n\n" +
                "👤 Foydalanuvchi nomi: " + firsName + "\n" +
                "👤 Foydalanuvchi boglanish: " + userName + "\n" +
                "📝 Xabar: " + text + "\n\n" +
                "🔄 Ushbu xabarga javob berish uchun:\n" +
                "👉 ->" + chatId + ": javob xabari`<- shu ko'rinishda yuboring.";
        sendMessage(messageToAdmin, chatIdAdmin);
    }
*/
    /*
   // --> bu admindan habarni oladi va va userga jo'natadi---
    public void getMessageAdmin(String text) {
        String[] parts = text.split(":", 2);
        try {
            long userId = Long.parseLong(parts[0].trim());
            String responseMessage = parts[1].trim();

            // Javobni foydalanuvchiga yuborish
            sendMessage("🔔 Admin javobi:\n" + responseMessage, userId);
            sendMessage("✅ Javob yuborildi foydalanuvchiga: " + userId, chatIdAdmin);
        } catch (NumberFormatException e) {
            sendMessage("❌ Xato format! Iltimos, ' chatId: xabar '  Chat ID noto‘g‘ri.", chatIdAdmin);
        }
    }*/
    public void getMessageAdmin(String text) {
        if (adminReplyMap.containsKey(chatIdAdmin)) {
            long userChatId = adminReplyMap.get(chatIdAdmin);

            // Javobni foydalanuvchiga yuborish
            sendMessage("📩 Admin javobi:\n" + text, userChatId);
            sendMessage("✅ Xabar foydalanuvchiga yuborildi.", chatIdAdmin);

            adminReplyMap.remove(chatIdAdmin); // Admin javob berganidan keyin o‘chiramiz
        } else {
            sendMessage("⚠️ Avval foydalanuvchiga javob yozish tugmasini bosing!", chatIdAdmin);
        }

    }
        /*if (callbackData.startsWith("reply_")) {
            long userChatId = Long.parseLong(callbackData.split("_")[1]);

            // Adminga xabar jo‘natish (U endi foydalanuvchiga javob yozishi mumkin)
            sendMessage("✏️ Javob yozing va u foydalanuvchiga yuboriladi.", chatIdAdmin);
            adminReplyMap.put(chatIdAdmin, userChatId); // Adminga javob yozayotgan foydalanuvchini bog‘lash
        }*/


    /* public void getMessageAdmin(String text) {
    bu 1-Varyanti adminga habar yuborish uchun:<----
        String[] parts = text.split(":", 2);
        try {
            long userId = Long.parseLong(parts[0].trim());
            String responseMessage = parts[1].trim();

            // Javobni foydalanuvchiga yuborish
            sendMessage("🔔 Admin javobi:\n" + responseMessage, userId);
            sendMessage("✅ Javob yuborildi foydalanuvchiga: " + userId, chatIdAdmin);
        } catch (NumberFormatException e) {
            sendMessage("❌ Xato format! Iltimos, ' chatId: xabar '  Chat ID noto‘g‘ri.", chatIdAdmin);
        }
    }*/

    public void setMessageAdmin(String username,String firstName,String userMessage, long userChatId) {
        // Admin uchun xabar
        String messageToAdmin = "📩 Yangi xabar!\n\n" +
                "👤 Ism: " + firstName + "\n" +
                "📌 Username: @" + username + "\n" +
                "🆔 Foydalanuvchi ID: " + userChatId + "\n" +
                "📝 Xabar: " + userMessage;

        // Inline button yaratish (Foydalanuvchiga javob yozish uchun)
         InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        InlineKeyboardButton replyButton = new InlineKeyboardButton();
        replyButton.setText("✉️ Javob yozish");
        replyButton.setCallbackData("reply_" + userChatId); // Foydalanuvchi ID tugmaga biriktiriladi

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(replyButton);
        keyboard.add(row);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage(inlineKeyboardMarkup , messageToAdmin, chatIdAdmin);
    }
    public void handleCallbackQuery(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long adminChatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.startsWith("reply_")) {
                long userChatId = Long.parseLong(callbackData.split("_")[1]);

                // Adminga xabar jo‘natish (U endi foydalanuvchiga javob yozishi mumkin)
                sendMessage("✏️ Javob yozing va u foydalanuvchiga yuboriladi.", adminChatId);
                adminReplyMap.put(adminChatId, userChatId); // Adminga javob yozayotgan foydalanuvchini bog‘lash
            }
        }
    }

}


