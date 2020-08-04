package moe.gensoukyo.rpgmaths.api.util;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;

/**
 * 一个标准排序器
 * 用于记录某些东西排序的
 * @author Chloe_koopa
 */
public class Order implements Ordered<Order>
{
    protected int pMain;
    protected int pSub;

    @Nonnull
    @Override
    public Order getOrder()
    {
        return this;
    }

    @Override
    public void setPriority(int priority)
    {
        this.pMain = priority;
        this.pSub = 0;
    }

    @Override
    public void moveBefore(@Nonnull Order that)
    {
        this.makeSame(that);
        this.pSub = that.pSub + 1;
    }

    @Override
    public void moveAfter(@Nonnull Order that)
    {
        this.makeSame(that);
        this.pSub = that.pSub - 1;
    }

    private void makeSame(Order that)
    {
        this.pMain = that.pMain;
    }

    /**
     * 优先级越大越靠前
     */
    @Override
    public int compareTo(@Nonnull Order that)
    {
        Preconditions.checkNotNull(that);
        int main = Integer.compare(this.pMain, that.pMain);
        if (main != 0)
        {
            return -main;
        }
        return -Integer.compare(this.pSub, that.pSub);
    }

    @Override
    public String toString()
    {
        return "Order Main: " + pMain + " Sub: " + pSub;
    }
}
