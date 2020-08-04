package moe.gensoukyo.rpgmaths.api.impl.stats;

import moe.gensoukyo.rpgmaths.api.stats.AbstractStatType;
import moe.gensoukyo.rpgmaths.common.capabilities.StatStorageCap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 存储在NBT里的统计数据
 * 根据注册名不同，存储的内容也不同
 * @author Chloe_koopa
 */
public class StoredStatType extends AbstractStatType {
    @CapabilityInject(StatStorageCap.Instance.class)
    private static Capability<StatStorageCap.Instance> STORAGE_CAP;

    @Override
    public float getBaseValue(ICapabilityProvider owner)
    {
        //idea自动生成的，这个原子引用居然还能干这事，太草了
        AtomicReference<Float> result = new AtomicReference<>(0f);
        owner.getCapability(STORAGE_CAP).ifPresent(
                instance -> result.set(instance.getStat(this))
        );
        return result.get();
    }

    @Override
    public boolean setBaseValue(ICapabilityProvider owner, float value)
    {
        owner.getCapability(STORAGE_CAP).ifPresent(
                instance -> instance.setStat(this, value)
        );
        return true;
    }
}
