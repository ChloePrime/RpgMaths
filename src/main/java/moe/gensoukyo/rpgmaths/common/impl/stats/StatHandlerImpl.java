package moe.gensoukyo.rpgmaths.common.impl.stats;

import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class StatHandlerImpl implements IStatHandler
{
    protected ICapabilityProvider owner;
    public StatHandlerImpl(ICapabilityProvider owner)
    {
        this.owner = owner;
    }

    @Override
    public float getStat(IStatType type)
    {
        return type.getBaseValue(owner);
    }

    @Override
    public boolean setStat(IStatType type, float value)
    {
        return type.setBaseValue(owner, value);
    }
}
