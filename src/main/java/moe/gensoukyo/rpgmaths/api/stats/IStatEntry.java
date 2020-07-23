package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * 一项属性
 * @author Chloe_koopa
 */
public interface IStatEntry extends IForgeRegistryEntry<IStatEntry>
{
    /**
     * 返回该实体的这项基础数值
     * @param entity 数据的拥有者
     * @param context 该stat的一些上下文
     * @return 不包括装备加成的数据
     */
    float getBaseValue(CapabilityProvider<?> entity,
                       CompoundNBT context);
    /**
     * 返回该实体的这项最终数值
     * @param entity 数据的拥有者
     * @param context 该stat的一些上下文
     * @return 包括装备加成的数据
     */
    float getFinalValue(CapabilityProvider<?> entity, CompoundNBT context);

    /**
     * 获取描述
     * @apiNote 返回翻译密钥
     * @return 关于该项属性的描述
     */
    String getDescription();
}