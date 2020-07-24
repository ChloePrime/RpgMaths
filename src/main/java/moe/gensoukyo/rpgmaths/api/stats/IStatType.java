package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * 一项属性
 * @author Chloe_koopa
 */
public interface IStatType extends IForgeRegistryEntry<IStatType>
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
     * 设置某个属性的基础值
     * @apiNote 不保证对所有值有效
     * @param entity 数据的拥有者
     * @param context 该stat的一些上下文
     * @param value 要将这个属性设置为的值（基础值）
     */
    void setBaseValue(CapabilityProvider<?> entity,
                      CompoundNBT context,
                      float value);

    /**
     * 返回该实体的这项最终数值
     * @implSpec 如果entity为活体，那么需要包括装备和手上的道具的属性
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