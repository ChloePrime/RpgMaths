package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Objects;

public interface IStat extends INBTSerializable<CompoundNBT>
{
    IStatType getType();
    IStatPredicate getCondition();
    float getValue();

    String TAG_TYPE = "t";
    String TAG_COND = "c";
    @Override
    default CompoundNBT serializeNBT()
    {
        CompoundNBT result = new CompoundNBT();
        result.putString(TAG_TYPE, Objects.requireNonNull(getType().getRegistryName()).toString());
        result.putString(TAG_COND, Objects.requireNonNull(getCondition().getRegistryName()).toString());
        return result;
    }
}
