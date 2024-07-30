public class SubTask extends Task {

    int epicId;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public SubTask(String title, String description, TaskStatus statusTask, int epicId) {
        super(title, description, statusTask);
        this.epicId = epicId;
    }


    @Override
    public String toString() {
        return "SubTask" +
                super.toString() +
                ", epicId=" + epicId +
                '}';
    }
}
