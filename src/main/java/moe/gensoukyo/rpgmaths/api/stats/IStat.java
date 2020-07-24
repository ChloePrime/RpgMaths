package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Optional;

/**
 * 某个实体身上的某项统计数据
 * @implNote 除了标准实现以外请不要继承
 * @implSpec 该类需要包装 {@link IStatType}
 * 和 {@link net.minecraftforge.common.capabilities.CapabilityProvider} 之间的关系
 * @author Chloe_koopa
 */
@Deprecated
public interface IStat extends INBTSerializable<CompoundNBT>
{
    /**
     * 返回该实体的这项基础数值
     * @return 不包括装备加成的数据
     */
    double getBaseValue();
    /**
     * 返回该实体的这项最终数值     * @return 包括装备加成的数据
     */
    double getFinalValue();

    Optional<INBT> getContext(String key);

    void setContext(String key, CompoundNBT context);
}
