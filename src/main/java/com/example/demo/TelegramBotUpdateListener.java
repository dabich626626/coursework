package com.example.demo;

import com.pengrad.telegrambot.UpdatesListener;
import jakarta.annotation.PostConstruct;
import model.NotificationTask;
import org.springframework.stereotype.Service;
import repository.NotificationRepository;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdateListener implements UpdatesListener {

    private  static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");
    private static final Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)")

private final TelegramBot bot;

    public TelegramBotUpdateListener(NotificationRepository repository) {
        this.repository = repository;
    }

    private final NotificationRepository repository;

public TelegramBotUpdateListener(TelegramBot bot){
    this.bot = bot;

    @PostConstruct
            public void init(){
        bot.setUpdatesListener(this);
    }
}

    @Override
    public int process(List<Update> updates){
        for (Update update : updates){
            var text = update.messege().text();
            var chatId = update.messege().chat().id();
            if ("/start".equals(text)){
                bot.execute( new SendMessege(chatId, "Добро пожаловать!"));
                //
            } else{
                //01.01.2024 Дз.
                var matcher = PATTERN.matcher(text);
                if(matcher.matches()){
                    LocalDateTime dateTime = parseTime(matcher.group(1));
                    if(dateTime = null){
                        bot.execute(new SendMessage(chatId, "Формат даты указан неверно."))
                    continue;
                    }


                    LocalDateTime.parse(dateTime, DATE_TIME_PATTERN);

                    NotificationTask task = new NotificationTask();
                    task.setChatId(chatId);
                    task.setText();
                    task.setDateTime();
                    NotificationTask saved = repository.save(task);
                    bot.execute(new SendMessage(chatId, "задача запланирована"));
                    logger.info("Notification task saved: {}", saved);
                }
            }
        }

        return UpdateListener.CONFIRMED_UPDATES_ALL;
    }

    private LocalDateTime parseTime(String text){
    try {
        return LocalDateTime.parse(text, DATE_TIME_PATTERN);
    } catch (DateTimeParseException e){
        logger.error("Cannot parse date and time: {}", text);
    }
    return null;
    }
}
