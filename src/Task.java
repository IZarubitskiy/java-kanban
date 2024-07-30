public class Task {
    private String title;
    private String description;
    private TaskStatus statusTask;

    public Task(String title, String description, TaskStatus statusTask) {
        this.title = title;
        this.description = description;
        this.statusTask = statusTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ",description='" + description + '\'' +
                ", statusSingleTask=" + statusTask +
                '}';
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

    public TaskStatus getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(TaskStatus statusTask) {
        this.statusTask = statusTask;
    }
}
