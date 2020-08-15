package moe.gensoukyo.rpgmaths.api.impl.data;

import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.impl.stats.FixedStatHandler;
import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

/**
 * 共享RPG数据
 * 这通常是给怪物使用的
 * @author Chloe_koopa
 */
public class SharedRpgData extends AbstractRpgData {
    protected IStatHandler statHandler;

    public SharedRpgData(IResistanceMap resistanceMap,
                         IDamageType[] defaultTypes,
                         IStatHandler statHandler) {
        super(resistanceMap, defaultTypes);
        this.statHandler = statHandler;
    }

    @Nonnull
    @Override
    public IStatHandler getStats() {
        return this.statHandler;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}