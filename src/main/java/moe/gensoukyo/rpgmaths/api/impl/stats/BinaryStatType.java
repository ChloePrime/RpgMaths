package moe.gensoukyo.rpgmaths.api.impl.stats;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * 二态属性
 * @author Chloe_koopa
 */
public class BinaryStatType extends AttributeStatType {
    @Override
    public boolean hasStat(ICapabilityProvider owner) {
        return getFinalValue(owner) > 1e-8f;
    }

    @Override
    public boolean isCountable() {
        return false;
    }
}