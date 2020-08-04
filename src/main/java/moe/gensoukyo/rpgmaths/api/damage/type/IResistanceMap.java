package moe.gensoukyo.rpgmaths.api.damage.type;

import moe.gensoukyo.rpgmaths.api.impl.damage.type.AbstractResistanceMap;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * 攻击抗性
 * 计算某个抗性拥有者对抗性的倍率
 * @author Chloe_koopa
 */
public interface IResistanceMap extends IForgeRegistryEntry<IResistanceMap>
{
    /**
     * 获取抗性数值
     * 由于攻击可能有多种属性（物理/魔法，地水火风等），所以为数组
     * @param types 属性值
     * @return 倍率
     */
    double getMultiplier(IDamageType[] types);

    IResistanceMap DEFAULT = new AbstractResistanceMap()
    {
        @Override
        public double getMultiplier(IDamageType[] types)
        {
            return 1.0;
        }
    };

}
