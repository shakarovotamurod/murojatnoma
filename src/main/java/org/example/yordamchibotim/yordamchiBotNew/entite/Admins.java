package org.example.yordamchibotim.yordamchiBotNew.entite;

import java.util.List;

public class Admins {
    private static Long chatIdAdmin ;

    public static Long getChatIdAdmin() {
        return  chatIdAdmin;
    }

    public void edAdmin(long chatId) {
        chatIdAdmin= chatId;
    }
}
