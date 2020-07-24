package moe.gensoukyo.rpgmaths.common.stats;

import moe.gensoukyo.rpgmaths.api.stats.AbstractStatType;
import moe.gensoukyo.rpgmaths.common.capabilities.StatStorageCap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 存储在NBT里的统计数据
 * 根据注册名不同，存储的内容也不同
 */
public class StoredStatType extends AbstractStatType {
    @CapabilityInject(StatStorageCap.Instance.class)
    private static Capability<StatStorageCap.Instance> STORAGE_CAP;

    @Override
    public float getBaseValue(CapabilityProvider<?> entity, CompoundNBT context)
    {
        //idea自动生成的，这个原子引用居然还能干这事，太草了
        AtomicReference<Float> result = new AtomicReference<>(0f);
        entity.getCapability(STORAGE_CAP).ifPresent(
                instance -> result.set(instance.getStat(this))
        );
        return result.get();
    }

    @Override
    public void setBaseValue(CapabilityProvider<?> entity, CompoundNBT context, float value)
    {
        entity.getCapability(STORAGE_CAP).ifPresent(
                instance -> instance.setStat(this, value)
        );
    }

    public static final String DESC_TEMPLATE = "%s:stat.%s";
    /**
     * 返回 (modid):stat.(属性名称)
     */
    @Override
    public String getDescription()
    {
        ResourceLocation regName = getRegistryName();
        if (regName == null) { return "" + null; }

        return String.format(DESC_TEMPLATE, regName.getNamespace(), regName.getPath());
    }
}
