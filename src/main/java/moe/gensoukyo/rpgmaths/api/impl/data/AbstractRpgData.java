package moe.gensoukyo.rpgmaths.api.impl.data;

import com.google.common.base.Preconditions;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.data.IRpgData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Chloe_koopa
 */
public abstract class AbstractRpgData implements IRpgData {
    @Nullable
    protected IResistanceMap resistanceMap;
    protected IDamageType[] defaultDamageTypes;

    protected AbstractRpgData(@Nullable IResistanceMap resMapIn,
                              @Nonnull IDamageType[] defaultDamageTypeIn) {
        Preconditions.checkNotNull(defaultDamageTypeIn);

        this.resistanceMap = resMapIn;
        this.defaultDamageTypes = defaultDamageTypeIn;
    }

    @Override
    public boolean hasRpgData() {
        return true;
    }

    @Nonnull
    @Override
    public IResistanceMap getResistance() {
        return (this.resistanceMap == null) ? IResistanceMap.DEFAULT : this.resistanceMap;
    }

    @Nonnull
    @Override
    public IDamageType[] getDefaultDamageTypes() {
        return this.defaultDamageTypes;
    }
}