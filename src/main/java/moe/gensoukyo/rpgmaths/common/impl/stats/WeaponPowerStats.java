package moe.gensoukyo.rpgmaths.common.impl.stats;

import moe.gensoukyo.rpgmaths.api.stats.AbstractStatType;
import moe.gensoukyo.rpgmaths.common.stats.StoredStatType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.CapabilityProvider;

/**
 * POW属性
 * @author Chloe_koopa
 */
public class WeaponPowerStats extends StoredStatType {

    @Override
    public float getBaseValue(CapabilityProvider<?> entity, CompoundNBT context) {
        return 0;
    }

    @Override
    public float getFinalValue(CapabilityProvider<?> owner, CompoundNBT context) {
        return getBaseValue(owner, context);
    }

    @Override
    public String getDescription() {
        return "";
    }
}
