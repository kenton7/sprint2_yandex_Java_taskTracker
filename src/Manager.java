import java.util.HashMap;
import java.util.Scanner;

public class Manager {

    private Scanner scanner = new Scanner(System.in);
    private int taskID = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

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
        System.out.println("0. Выход");
    }

    private EpicTask createEpic() {
        System.out.println("Введите имя эпика:");
        String epicName = scanner.nextLine();
        System.out.println("Введите описание эпика:");
        String epicDescription = scanner.nextLine();
        taskID++;
        EpicTask epicTask = new EpicTask(epicName, epicDescription, taskID);
        //epicTasks.put(epicTask.getTaskID(), epicTask);
        System.out.println("Эпик создан с ID: " + epicTask.getTaskID());
        return epicTask;
    }

    private Task createTask() {
        System.out.println("Введите имя задачи:");
        String taskName = scanner.nextLine();
        System.out.println("Введите описание задачи:");
        String taskDescription = scanner.nextLine();
        taskID++;
        Task task = new Task(taskName, taskDescription, taskID);
        return task;
    };

    private SubTask createSubTask() {
        System.out.println("Введите имя подзадачи:");
        String subTaskName = scanner.nextLine();
        System.out.println("Введите описание подзадачи:");
        String subTaskDescription = scanner.nextLine();
        System.out.println("Укажите номер Эпика:");
        taskID++;
        int epicID = scanner.nextInt();
        scanner.nextLine();
        SubTask subTask = new SubTask(subTaskName, subTaskDescription, epicID, taskID);
        return subTask;
    }

    private void getAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("На данный момент задач нет.");
        } else {
            tasks.forEach((id, task) -> {
                System.out.println("ID: " + id + ", Название: " + task.getTaskName());
            });
        }
    }

    private void getAllEpicTasks() {
        if (epicTasks.isEmpty()) {
            System.out.println("На данный момент эпики отсутствуют.");
        } else {
            epicTasks.forEach((id, epc) -> {
                System.out.println("ID: " + id + ". Название: " + epc.getTaskName());
            });
        }
    }

    private void getAllSubTasks() {
        if (subTasks.isEmpty()) {
            System.out.println("На данный момент подзадачи отсутствуют.");
        } else {
            subTasks.forEach((id, sub) -> {
                System.out.println("ID: " + id + ". Название: " + sub.getTaskName());
            });
        }
    }

    private void getSubTaskByEpicID(int epicID) {
        System.out.println("Подзадачи, связанные с данным эпиком:");
        subTasks.forEach((id, sub) -> {
            if (id == epicID) {
                System.out.println("ID подзадачи: " + sub.getTaskID() + ", название: " + sub.getTaskName());
            }
        });
    }

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
                    SubTask subTask = manager.createSubTask();
                    manager.subTasks.put(subTask.getEpicID(), subTask);
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
                    manager.getSubTaskByEpicID(epicID);
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
}
