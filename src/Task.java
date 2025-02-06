import java.util.ArrayList;
import java.util.HashMap;

public class Task {
    private String taskName;
    private String taskDescription;
    private String taskStatus = "NEW";
    private HashMap<Integer, SubTask> subtasks;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int taskID;

    public Task(String taskName, String taskDescription, int taskID) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public HashMap<Integer, SubTask> getSubtasks() {
        return subtasks;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

//    public void addTask(Task task) {
//        tasks.put(task.getTaskID(), task);
//    }
}

class SubTask extends Task {
    HashMap<Integer, EpicTask> epics = new HashMap<>();
    private int epicID;

    public SubTask(String taskName, String taskDescription, int epicID, int taskID) {
        super(taskName, taskDescription, taskID);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }
}

class EpicTask extends Task {
    HashMap<Integer, ArrayList<SubTask>> epicSubtasks = new HashMap<>();

    public EpicTask(String taskName, String taskDescription, int taskID) {
        super(taskName, taskDescription, taskID);
    }


}
