package ru.nklsfnv.nklsfnvbot.telegram;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.nklsfnv.nklsfnvbot.service.MessageGate;

public class BotMainClass extends TelegramLongPollingBot {

    private final String botToken;
    private final String botName;
    private final MessageGate messageGate;

    public BotMainClass(String botToken, String botName, MessageGate messageGate) {
        this.botToken = botToken;
        this.botName = botName;
        this.messageGate = messageGate;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                execute(messageGate.resolveMessage(update.getMessage()));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
