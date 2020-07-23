package moe.gensoukyo.rpgmaths.common.impl.damage.type;

import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IMutableResistanceMap;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * 标准抗性表
 * 支持parent是为了支持让一个攻击带有多种属性
 * 例如一个属性可能有物理/魔法的区分，以及地水火风四元素的区分
 * 这个时候，物理/魔法就作为parent
 */
public class StandardResistanceMap
        extends AbstractResistanceMap
        implements IMutableResistanceMap
{
    public IResistanceMap parent;
    public StandardResistanceMap(@Nullable IResistanceMap parent)
    {
        this.parent = parent;
    }

    public StandardResistanceMap()
    {
        this(null);
    }
    protected Map<IDamageType, Double> values;

    @Override
    public double getMultiplier(final IDamageType[] types)
    {
        double result = 1.0;
        for (IDamageType type : types) {
            result *= values.getOrDefault(type, 1.0);
        }
        if (parent != null)
        {
            result *= parent.getMultiplier(types);
        }
        return result;
    }

    @Override
    public void setMultiplier(IDamageType type, double value)
    {
        values.put(type, value);
    }

}
