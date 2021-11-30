package mc.apptoeat.com.utils.shortcuts;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SyncTaskScheduler implements Runnable {

    private long currentTick;
    private final Plugin plugin;
    private List<ATGTask> tasks;
    private static int ATGTaskInstances;

    public SyncTaskScheduler(Plugin plugin) {
        this.plugin = plugin;
        this.tasks = new ArrayList<>();
    }

    @Override
    public void run() {
        for(ATGTask ATGTask : tasks) {
            if(currentTick % ATGTask.interval == 0) {
                ATGTask.task.run();
            }
        }
        currentTick++;
    }

    public int addRepeatingTask(Runnable task, int interval) {
        ATGTask ATGTask = new ATGTask(task, interval);
        tasks.add(ATGTask);
        return ATGTask.id;
    }

    public void cancelTask(int id) {
        for(int i = 0; i < tasks.size(); i++) {
            ATGTask task = tasks.get(i);
            if(id == task.id) {
                tasks.remove(i);
                break;
            }
        }
    }

    private class ATGTask {

        private Runnable task;
        private int interval;
        private int id;

        private ATGTask(Runnable task, int interval) {
            this.task = task;
            this.interval = interval;
            this.id = ATGTaskInstances;
            ATGTaskInstances++;
        }
    }
}
