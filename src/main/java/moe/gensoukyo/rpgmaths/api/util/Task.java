package moe.gensoukyo.rpgmaths.api.util;

import com.google.common.base.Preconditions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Chloe_koopa
 */
public class Task implements ITask {
    private final boolean autoUpdate;

    public Task() {
        this(true);
    }

    public Task(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;

        if (this.autoUpdate) {
            AUTO_UPDATE_INSTANCES.add(new WeakReference<>(this));
        }
    }

    @Override
    public ITaskItem delay(int sec, @Nonnull Consumer<TaskItem> callback) {
        Preconditions.checkNotNull(callback);
        return new TaskItem(sec, callback, 1);
    }

    @Override
    public ITaskItem repeat(int sec, @Nonnull Consumer<TaskItem> callback) {
        Preconditions.checkNotNull(callback);
        return new TaskItem(sec, callback, TaskItem.REPEAT);
    }

    @Override
    public ITaskItem countDown(int sec, int count, @Nonnull Consumer<TaskItem> callback) {
        Preconditions.checkNotNull(callback);
        return new TaskItem(sec, callback, count);
    }

    @Override
    public void clear() {
        items.clear();
    }

    private final List<TaskItem> items = new LinkedList<>();
    private static final List<WeakReference<Task>> AUTO_UPDATE_INSTANCES = new LinkedList<>();

    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event) {
        Iterator<WeakReference<Task>> ite = AUTO_UPDATE_INSTANCES.iterator();
        while (ite.hasNext()) {
            Task task = ite.next().get();
            if (task == null || (!task.autoUpdate)) {
                ite.remove();
                continue;
            }
            task.update();
        }
    }

    @Override
    public void update() {
        Iterator<TaskItem> i = items.iterator();
        while (i.hasNext()) {
            TaskItem item = i.next();
            if (item.isTerminated()) {
                i.remove();
                continue;
            }
            item.update();
        }
    }

    public class TaskItem implements ITaskItem {
        private final int initialCountdown;
        private final Consumer<TaskItem> callback;
        /**
         * -2持续
         * -1
         */
        private int count;
        private int curCountdown;
        private static final int REPEAT = -1;

        /**
         * @param count -1时持续
         */
        private TaskItem(int sec, Consumer<TaskItem> callback, int count) {
            this.initialCountdown = sec;
            this.callback = callback;
            this.count = count;

            this.curCountdown = this.initialCountdown;
            items.add(this);
        }

        private void update() {
            if ((this.curCountdown <= 0) && (!this.isTerminated())) {
                nextLoop();
            } else {
                --this.curCountdown;
            }
        }

        @Override
        public void stop() {
            this.count = 0;
        }

        private void nextLoop() {
            this.callback.accept(this);
            if (this.count == 0) {
                return;
            }
            this.curCountdown = this.initialCountdown;
            if (this.count > 0) {
                --this.count;
            }
        }

        private boolean isTerminated() {
            return this.count == 0;
        }
    }
}
