package moe.gensoukyo.rpgmaths.api.util;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author Chloe_koopa
 */
public interface ITask {
    ITaskItem delay(int sec, @Nonnull Consumer<Task.TaskItem> callback);

    ITaskItem repeat(int sec, @Nonnull Consumer<Task.TaskItem> callback);

    ITaskItem countDown(int sec, int count, @Nonnull Consumer<Task.TaskItem> callback);

    void clear();
    void update();

    interface ITaskItem {
        void stop();
    }
}
