package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nklsfnv.nklsfnvbot.utils.AppUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class MessageGate {

    private final TelegramUserService telegramUserService;

    public SendMessage resolveMessage(Message inMessage) {
        String chatId = inMessage.getChatId().toString();
        return SendMessage.builder()
                    .chatId(chatId)
                    .text(getAnswer(inMessage))
                    .build();
    }

    public String getAnswer(Message inMessage) {
        log.info(
                "user [{} {} {}] sent message : \n [{}]",
                inMessage.getChat().getFirstName(),
                inMessage.getChat().getLastName(),
                inMessage.getChat().getUserName(),
                inMessage.getText()
        );
        String text = inMessage.getText();
        telegramUserService.saveUserFromTelegram(
                AppUtils.getFullName(inMessage.getChat().getFirstName(), inMessage.getChat().getLastName()),
                inMessage.getChat().getUserName()
        );
        return text;
    }

}
