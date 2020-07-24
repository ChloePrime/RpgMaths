package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * 每个数据拥有者的人物属性数据
 * @implNote ❌不要继承❌
 * @author Chloe_koopa
 */
public interface IStatHandler extends INBTSerializable<CompoundNBT>
{
    /**
     * 获取某一项数据
     * @param stat 属性类型
     * @return 该handler宿主实体的某项数据
     */
    float getStat(IStatType stat);
}