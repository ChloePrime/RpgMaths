package moe.gensoukyo.rpgmaths.api.impl.stats;

import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * StatHandler的实现
 * 与实体绑定
 * @author Chloe_koopa
 * @see IStatHandler
 */
public class StatHandlerImpl implements IStatHandler {
    protected ICapabilityProvider owner;

    public StatHandlerImpl(ICapabilityProvider owner) {
        this.owner = owner;
    }

    @Override
    public double getBaseValue(IStatType type) {
        return type.getBaseValue(owner);
    }

    @Override
    public double getFinalValue(IStatType type) {
        return type.getFinalValue(owner);
    }

    @Override
    public boolean setBaseValue(IStatType type, double value) {
        return type.setBaseValue(owner, value);
    }
}
