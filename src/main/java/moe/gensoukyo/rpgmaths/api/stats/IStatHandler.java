package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * 每个数据拥有者的人物属性数据
 * @implNote ❌不要继承❌
 * @author Chloe_koopa
 */
public interface IStatHandler
{
    /**
     * 获取某一项数据
     * @param stat 属性类型
     * @return 该handler宿主实体的某项数据
     */
    float getStat(IStatType stat);

    /**
     * 设置某一项的数据
     * @param type 数据种类
     * @param value 该数据要设置成的值
     * @return 设置数据是否有效，通常这取决于
     */
     boolean setStat(IStatType type, float value);

     default boolean addTo(IStatType type, float value)
     {
         return setStat(type, getStat(type) + value);
     }
}