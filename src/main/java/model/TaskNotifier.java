package model;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TaskNotifier {
    private final static Logger logger = LoggerFactory.getLogger(TaskNotifier.class);

    private final TelegramBot bot;
    private final NotoficationRepository repository;

    public TaskNotifier(TelegramBot bot, NotoficationRepository repository) {
        this.bot = bot;
        this.repository = repository;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void notifyTask() {
        repository.findAllByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        .forEach(task -> {
            bot.execute(new SendMessage(task.getChatId(), task.getText()));
            repository.delete(task);
            logger.info("notification had been sent");
        });
    }
    }
}
