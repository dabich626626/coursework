package repository;

import model.NotificationTask;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {
}
