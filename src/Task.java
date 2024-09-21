public class Task {
    private String title;
    private String description;
    private Integer id;
    private TaskStatus statusTask;

    public Task(String title, String description, Integer id, TaskStatus statusTask) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.statusTask = statusTask;
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

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(TaskStatus statusTask) {
        this.statusTask = statusTask;
    }

    public int getEpicId() {
        return 0;
    }

    public String getType() {return TaskTypes.TASK.name();}

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ",description='" + description + '\'' +
                ",id='" + id + '\'' +
                ", statusSingleTask=" + statusTask +
                '}';
    }
}
