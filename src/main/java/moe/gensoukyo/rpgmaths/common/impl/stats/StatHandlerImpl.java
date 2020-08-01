package moe.gensoukyo.rpgmaths.common.impl.stats;

import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * StatHandler的实现
 * @author Chloe_koopa
 * @see IStatHandler
 */
public class StatHandlerImpl implements IStatHandler
{
    protected ICapabilityProvider owner;
    public StatHandlerImpl(ICapabilityProvider owner)
    {
        this.owner = owner;
    }

    @Override
    public float getBaseValue(IStatType type)
    {
        return type.getBaseValue(owner);
    }

    @Override
    public float getFinalValue(IStatType type)
    {
        return type.getFinalValue(owner);
    }

    @Override
    public boolean setBaseValue(IStatType type, float value)
    {
        return type.setBaseValue(owner, value);
    }
}
