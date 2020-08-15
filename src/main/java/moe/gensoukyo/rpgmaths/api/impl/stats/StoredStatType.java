package moe.gensoukyo.rpgmaths.api.impl.stats;

import moe.gensoukyo.rpgmaths.common.capabilities.StatStorageCap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * 存储在NBT里的统计数据
 * 根据注册名不同，存储的内容也不同
 * @author Chloe_koopa
 */
public class StoredStatType extends AbstractStatType {
    @CapabilityInject(StatStorageCap.Instance.class)
    private static Capability<StatStorageCap.Instance> STORAGE_CAP;

    @Override
    public double getBaseValue(ICapabilityProvider owner) {
        return owner.getCapability(STORAGE_CAP)
                .map(instance -> instance.getStat(this))
                .orElse(0d);
    }

    @Override
    public boolean setBaseValue(ICapabilityProvider owner, double value) {
        owner.getCapability(STORAGE_CAP).ifPresent(
                instance -> instance.setStat(this, value)
        );
        return true;
    }
}
