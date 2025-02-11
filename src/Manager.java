import java.util.HashMap;
import java.util.Scanner;

public class Manager {

    private final Scanner scanner = new Scanner(System.in);
    private int taskID = 0;
    private boolean isUpdating = false;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

    public static void main(String[] args) {
        boolean isRunning = true;
        Manager manager = new Manager();

        while (isRunning) {
            manager.showMenu();
            int input = manager.scanner.nextInt();
            manager.scanner.nextLine();

            switch (input) {
                case 1:
                    EpicTask epic = manager.createEpic();
                    manager.epicTasks.put(manager.taskID, epic);
                    break;
                case 2:
                    Task task = manager.createTask();
                    manager.tasks.put(task.getTaskID(), task);
                    break;
                case 3:
                    manager.createSubTask();
                    //manager.subTasks.put(subTask.getEpicID(), subTask);
                    break;
                case 4:
                    manager.getAllEpicTasks();
                    break;
                case 5:
                    manager.getAllTasks();
                    break;
                case 6:
                    manager.getAllSubTasks();
                    break;
                case 7:
                    System.out.println("Введите номер эпика:");
                    int epicID = manager.scanner.nextInt();
                    manager.findSubTaskByEpicID(epicID);
                    break;
                case 8:
                    System.out.println("Введите ID задачи:");
                    int taskID = manager.scanner.nextInt();
                    manager.findTaskByID(taskID);
                    break;
                case 9:
                    System.out.println("Введите ID подзадачи:");
                    int subTaskID = manager.scanner.nextInt();
                    manager.findSubTaskByID(subTaskID);
                    break;
                case 10:
                    System.out.println("Введите ID эпика:");
                    int epicTaskID = manager.scanner.nextInt();
                    manager.findEpicByID(epicTaskID);
                    break;
                case 11:
                    System.out.println("Вы точно хотите удалить все задачи?");
                    System.out.println("1. Да");
                    System.out.println("2. Нет");
                    int confirm = manager.scanner.nextInt();
                    manager.deleteAllTasks(confirm);
                    break;
                case 12:
                    System.out.println("Введите ID задачи:");
                    int id = manager.scanner.nextInt();
                    manager.deleteTaskByID(id);
                    break;
                case 13:
                    System.out.println("Введите ID задачи:");
                    int taskIDToUpdate = manager.scanner.nextInt();
                    Task updatedTask = manager.createTask();
                    manager.tasks.put(taskIDToUpdate, updatedTask);
                    manager.updateTask(updatedTask);
                    break;
                case 14:
                    manager.isUpdating = true;
                   // System.out.println("Введите ID подзадачи:");
                    //int subTaskIDToUpdate = manager.scanner.nextInt();
                    //SubTask someTask = manager.subTasks.get(subTaskIDToUpdate);
                    //SubTask updatedSubTask = manager.createSubTask();
                    manager.createSubTask();
                    //manager.subTasks.put(subTaskIDToUpdate, updatedSubTask);
                    //manager.updateSubTask(updatedSubTask);
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Введена неверная команда.");
            }
        }
        manager.scanner.close();
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public HashMap<Integer, EpicTask> getEpicTasks() {
        return epicTasks;
    }

    private void showMenu() {
        System.out.println("Что вы хотите сделать? Укажите номер команды.");
        System.out.println("1. Создать эпик");
        System.out.println("2. Создать задачу");
        System.out.println("3. Создать подзадачу");
        System.out.println("4. Получить список всех эпиков");
        System.out.println("5. Получить список всех задач");
        System.out.println("6. Получить список всех подзадач");
        System.out.println("7. Получить подзадачи конкретного эпика");
        System.out.println("8. Найти задачу");
        System.out.println("9. Найти подзадачу");
        System.out.println("10. Найти эпик");
        System.out.println("11. Удалить все задачи");
        System.out.println("12. Удалить задачу по ID");
        System.out.println("13. Обновить задачу");
        System.out.println("14. Обновить подзадачу");
        System.out.println("0. Выход");
    }

    private EpicTask createEpic() {
        System.out.println("Введите имя эпика:");
        String epicName = scanner.nextLine();
        System.out.println("Введите описание эпика:");
        String epicDescription = scanner.nextLine();
        taskID++;
        EpicTask epicTask = new EpicTask(epicName, epicDescription, taskID);
        epicTask.setEpicTasks(epicTask);
        System.out.println("Эпик создан с ID: " + epicTask.getTaskID());
        return epicTask;
    }

    private Task createTask() {
        System.out.println("Введите имя задачи:");
        String taskName = scanner.nextLine();
        System.out.println("Введите описание задачи:");
        String taskDescription = scanner.nextLine();
        taskID++;
        return new Task(taskName, taskDescription, taskID);
    };

    private void createSubTask() {
        System.out.println("Введите имя подзадачи:");
        String subTaskName = scanner.nextLine();
        System.out.println("Введите описание подзадачи:");
        String subTaskDescription = scanner.nextLine();

        if (isUpdating) {
            System.out.println("Введите ID подзадачи для обновления:");
            int subTaskID = scanner.nextInt();
            scanner.nextLine();

            SubTask subTaskToUpdate = subTasks.get(subTaskID);
            EpicTask epicTask = epicTasks.get(subTaskToUpdate.getEpicID());
            System.out.println(epicTask.toString());

            if (subTaskToUpdate != null) {
                subTaskToUpdate.setTaskName(subTaskName);
                subTaskToUpdate.setTaskDescription(subTaskDescription);
                subTaskToUpdate.setTaskStatus("IN_PROGRESS");
                subTasks.put(subTaskID, subTaskToUpdate);
                epicTask.setEpicSubtasks(subTaskToUpdate);
                epicTask.updateStatus();
                System.out.println("Подзадача обновлена.");
            } else {
                System.out.println("Подзадача с таким ID не найдена.");
            }
            isUpdating = false;
            return;
        }

        System.out.println("Укажите номер Эпика:");
        int epicID = scanner.nextInt();
        scanner.nextLine();

        taskID++;
        SubTask subTask = new SubTask(subTaskName, subTaskDescription, epicID, taskID);
        subTasks.put(subTask.getTaskID(), subTask);
        EpicTask epicTask = epicTasks.get(epicID);
        epicTask.setEpicSubtasks(subTask);
        System.out.println("Подзадача создана с ID: " + subTask.getTaskID() + ", описание: " + subTask.getTaskDescription());
    }


    private void getAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("На данный момент задач нет.");
        } else {
            tasks.forEach((id, task) -> {
                System.out.println("ID: " + id + ", Название: " + task.getTaskName() + ", описание: " + task.getTaskDescription() +
                        ", статус задачи: " + task.getTaskStatus());
            });
        }
    }

    private void getAllEpicTasks() {
        if (epicTasks.isEmpty()) {
            System.out.println("На данный момент эпики отсутствуют.");
        } else {
            for (EpicTask epicTask : epicTasks.values()) {
                System.out.println("ID: " + epicTask.getTaskID() + ". Название: " + epicTask.getTaskName() +
                        ", описание: " + epicTask.getTaskDescription() + ", статус: " + epicTask.getTaskStatus());
            }
        }
    }

    private void getAllSubTasks() {
        if (subTasks.isEmpty()) {
            System.out.println("На данный момент подзадачи отсутствуют.");
        } else {

            for (SubTask subTask : subTasks.values()) {
                System.out.println("ID: " + subTask.getTaskID() + ". Название: " + subTask.getTaskName() + ", описание: " +
                        subTask.getTaskDescription() + ", статус: " + subTask.getTaskStatus());
            }
        }
    }

    private void findSubTaskByEpicID(int epicID) {
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getTaskID() == epicID) {
                System.out.println("Подзадачи, связанные с данным эпиком:");
                System.out.println("ID подзадачи: " + subTask.getTaskID() + ", название: " + subTask.getTaskName() +
                        ", описание: " + subTask.getTaskDescription() +
                        ", статус: " + subTask.getTaskStatus());
            } else {
                System.out.println("Подзадач, связанных с эпиком " + epicID + " не найдено.");
            }
        }
    }

    private void findSubTaskByID(int taskID) {
        if (subTasks.containsKey(taskID)) {
            System.out.println("Найдена подзадача:");
            System.out.println("ID: " + subTasks.get(taskID).getTaskID() + ", название: " + subTasks.get(taskID).getTaskName() +
                    ", описание: " + subTasks.get(taskID).getTaskDescription() + ", принадлежит эпику: " +
                    subTasks.get(taskID).getEpicID() + ", статус: " + subTasks.get(taskID).getTaskStatus());
        }
    }

    private void findTaskByID(int taskID) {
        if (tasks.containsKey(taskID)) {
            System.out.println("Найдена задача:");
            System.out.println("ID: " + tasks.get(taskID).getTaskID() + ", название: " + tasks.get(taskID).getTaskName() +
                    ", описание: " + tasks.get(taskID).getTaskDescription() +
                    ", статус: " + tasks.get(taskID).getTaskStatus());
        } else {
            System.out.println("Задачи с данным ID не найдено.");
        }
    }

    private void deleteAllTasks(int userConfirm) {
        if (userConfirm == 1) {
            tasks.clear();
            subTasks.clear();
            epicTasks.clear();
        }
    }

    private void deleteTaskByID(int taskID) {
        if (tasks.containsKey(taskID)) {
            tasks.remove(taskID);
        } else if (subTasks.containsKey(taskID)) {
            subTasks.remove(taskID);
        } else if (epicTasks.containsKey(taskID)) {
            epicTasks.remove(taskID);
        } else {
            System.out.println("Произошла ошибка при удалении задачи с ID: " + taskID);
        }
    }

    private void findEpicByID(int epicID) {
        if (epicTasks.containsKey(epicID)) {
            System.out.println("Найден эпик:");
            System.out.println("ID: " + epicTasks.get(epicID).getTaskID() + ", название: " + epicTasks.get(epicID).getTaskName() +
                    ", описание: " + epicTasks.get(epicID).getTaskDescription() +
                    ", статус: " + epicTasks.get(epicID).getTaskStatus());
        } else {
            System.out.println("Эпика с таким ID " + epicID + " не найдено.");
        }
    }

    private void updateEpic(EpicTask epicTask) {
        epicTasks.put(epicTask.getTaskID(), epicTask);
        epicTask.setEpicTasks(epicTask);
    }

    private void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getTaskID(), subTask);
    }

    private void updateTask(Task task) {
        tasks.put(task.getTaskID(), task);
        epicTasks.get(task.getTaskID()).setTaskStatus("IN_PROGRESS");

    }
}
