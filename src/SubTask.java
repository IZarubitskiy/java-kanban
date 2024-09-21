public class SubTask extends Task {

    int epicId;

    public SubTask(String title, String description, Integer id, TaskStatus statusTask, int epicId) {
        super(title, description, id, statusTask);
        this.epicId = epicId;
    }
    @Override
    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }


    @Override
    public String getType() {
        return TaskTypes.SUBTASK.name();
    }

    @Override
    public String toString() {
        return "SubTask" +
                super.toString() +
                ", epicId=" + epicId +
                '}';
    }
}
