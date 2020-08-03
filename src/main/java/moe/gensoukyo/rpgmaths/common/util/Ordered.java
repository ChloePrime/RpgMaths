package moe.gensoukyo.rpgmaths.common.util;

import javax.annotation.Nonnull;

/**
 * @author Chloe_koopa
 * @param <I> 具体实现的类型s
 */
public interface Ordered<I extends Ordered<I>> extends Comparable<I>
{
    /**
     * 获取标准排序器
     * @return 标准排序器 {@link Order}
     */
    Order getOrder();

    /**
     * 设置主要优先级
     * @param priority 主要优先级
     */
    default void setPriority(int priority)
    {
        getOrder().setPriority(priority);
    }

    /**
     * 将这个对象放到另一个对象前面
     * @apiNote 会将this的主要优先级设置为和that相同
     * @param that 另一个对象
     */
    default void moveBefore(I that)
    {
        getOrder().moveBefore(that.getOrder());
    }


    /**
     * 将这个对象放到另一个对象后面
     * @apiNote 会将this的主要优先级设置为和that相同
     * @param that 另一个对象
     */
    default void moveAfter(I that)
    {
        getOrder().moveAfter(that.getOrder());
    }

    /**
     * {@inheritDoc}
     * @param that 需要比较的另一个对象
     * @return 见 {@link Comparable#compareTo(Object)}
     */
    @Override
    default int compareTo(@Nonnull I that)
    {
        return getOrder().compareTo(that.getOrder());
    }
}
