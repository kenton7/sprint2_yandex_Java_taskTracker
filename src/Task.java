import java.util.HashMap;

public class Task {
    private String taskName;
    private String taskDescription;
    private String taskStatus = "NEW";
    private HashMap<Integer, SubTask> subtasks = new HashMap<>();
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

    public void setSubtasks(HashMap<Integer, SubTask> subtasks) {
        this.subtasks.putAll(subtasks);
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks.putAll(tasks);
    }

    public HashMap<Integer, SubTask> getSubtasks() {
        return subtasks;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", subtasks=" + subtasks +
                ", tasks=" + tasks +
                ", taskID=" + taskID +
                '}';
    }
}

class SubTask extends Task {
    private HashMap<Integer, EpicTask> epics = new HashMap<>();
    private int epicID;
    private String taskDescription;
    private String taskStatus = "NEW";

    public SubTask(String taskName, String taskDescription, int epicID, int taskID) {
        super(taskName, taskDescription, taskID);
        this.epicID = epicID;
        this.taskDescription = taskDescription;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpics(EpicTask epic) {
        this.epics.put(epicID, epic);
    }

    public EpicTask getEpics(int epicID) {
        return epics.get(epicID);
    }

    @Override
    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public String getTaskStatus() {
        return taskStatus;
    }

    @Override
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}

class EpicTask extends Task {
    private HashMap<Integer, SubTask> epicSubtasks = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

    public EpicTask(String taskName, String taskDescription, int taskID) {
        super(taskName, taskDescription, taskID);
    }

    @Override
    public String getTaskStatus() {
        updateStatus();
        return super.getTaskStatus();
    }

    void setEpicTasks(EpicTask epicTask) {
        this.epicTasks.put(epicTask.getTaskID(), epicTask);
    }

    public void setEpicSubtasks(SubTask subTask) {
        epicSubtasks.put(subTask.getTaskID(), subTask);
    }

    public void updateStatus() {
        if (epicSubtasks.isEmpty()) {
            setTaskStatus("NEW");
            return;
        }

        //-----
        boolean allNewTasks = true;
        boolean allDoneTasks = true;

        for (SubTask subtask : epicSubtasks.values()) {
            String taskStatus = subtask.getTaskStatus();
            System.out.println(taskStatus);
            if (!taskStatus.equals("NEW")) {
                allNewTasks = false;
            }
            if (!taskStatus.equals("DONE")) {
                allDoneTasks = false;
            }
        }

        if (allNewTasks) {
            setTaskStatus("NEW");
        } else if (allDoneTasks) {
            setTaskStatus("DONE");
        } else {
            setTaskStatus("IN_PROGRESS");
        }
    }
}
