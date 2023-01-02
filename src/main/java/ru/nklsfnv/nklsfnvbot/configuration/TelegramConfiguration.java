package ru.nklsfnv.nklsfnvbot.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.nklsfnv.nklsfnvbot.service.MessageGate;
import ru.nklsfnv.nklsfnvbot.telegram.BotMainClass;

@Configuration
public class TelegramConfiguration {

    @Value("${ru.nklsfnv.bot.name}")
    private String botName;

    @Value("${ru.nklsfnv.bot.token}")
    private String botToken;

    @Autowired
    private MessageGate messageGate;

    @PostConstruct
    public void runBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new BotMainClass(botToken, botName, messageGate));
    }

}
