package moe.gensoukyo.rpgmaths.common.impl.stats;

import moe.gensoukyo.rpgmaths.common.stats.StoredStatType;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * POW属性
 * @author Chloe_koopa
 */
public class WeaponPowerStats extends StoredStatType {

    @Override
    public float getBaseValue(ICapabilityProvider entity) {
        return 0;
    }

    @Override
    public float getFinalValue(CapabilityProvider<?> owner) {
        return getBaseValue(owner);
    }

    @Override
    public String getDescription() {
        return "";
    }
}
