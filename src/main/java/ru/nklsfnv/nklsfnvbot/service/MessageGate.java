package ru.nklsfnv.nklsfnvbot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class MessageGate {

    public SendMessage resolveMessage(Message inMessage) {
        String chatId = inMessage.getChatId().toString();
        return SendMessage.builder()
                    .chatId(chatId)
                    .text(getAnswer(inMessage))
                    .build();
    }

    public String getAnswer(Message inMessage) {
        return "hello, i am a little stupid";
    }

}
