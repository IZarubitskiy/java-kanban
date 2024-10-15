package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private String title;
    private String description;
    private final Integer id;
    private TaskStatus statusTask;
    private LocalDateTime startTime;
    private Duration duration;

    public Task(String title, String description, Integer id, TaskStatus statusTask, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.statusTask = statusTask;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    /*public void setId(int id) {
        this.id = id;
    }*/

    public TaskStatus getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(TaskStatus statusTask) {
        this.statusTask = statusTask;
    }

    public int getEpicId() {
        return 0;
    }

    public String getType() {
        return TaskTypes.TASK.name();
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    @Override
    public String toString() {
        return "\n" +
                "tasks.Task{" +
                "title='" + title + '\'' +
                ",description='" + description + '\'' +
                ",id='" + id + '\'' +
                ", statusSingleTask=" + statusTask +
                ", startTime=" + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")) +
                ", duration=" + duration +
                '}';
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }
}
