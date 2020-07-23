package moe.gensoukyo.rpgmaths.common.impl.stats;

import com.google.common.base.Preconditions;
import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.stats.IStat;
import moe.gensoukyo.rpgmaths.api.stats.IStatEntry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityProvider;

import javax.annotation.Nullable;
import java.util.Optional;

public class StatImpl implements IStat
{
    protected IStatEntry type;
    protected final CapabilityProvider<?> owner;
    @Nullable
    protected CompoundNBT context;

    public StatImpl(IStatEntry type, CapabilityProvider<?> owner)
    {
        this.type = type;
        this.owner = owner;
    }

    @Override
    public double getBaseValue()
    {
        return type.getBaseValue(owner, this.context);
    }

    @Override
    public double getFinalValue()
    {
        return type.getFinalValue(owner, this.context);
    }

    @Override
    public Optional<INBT> getContext(String key)
    {
        if (this.context == null)
        {
            return Optional.empty();
        }
        return Optional.ofNullable(context.get(key));
    }

    @Override
    public void setContext(String key, CompoundNBT context)
    {
        if (this.context == null)
        {
            this.context = new CompoundNBT();
        }
        context.put(key, context);
    }

    public static final String TAG_STAT_TYPE = "id";
    public static final String TAG_CONTEXT = "id";
    @Override
    public CompoundNBT serializeNBT()
    {
        Preconditions.checkNotNull(this.type.getRegistryName());

        final CompoundNBT result = new CompoundNBT();
        result.putString(TAG_STAT_TYPE, this.type.getRegistryName().toString());

        if(context != null)
        {
            result.put(TAG_CONTEXT, this.context);
        }
        return result;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        //id
        String typeLoc = nbt.getString(TAG_STAT_TYPE);
        Preconditions.checkNotNull(typeLoc);
        RpgMathsMod.getApi().getStat(new ResourceLocation(typeLoc)).orElseThrow(
                () -> new NullPointerException(
                        "the tag to deserialize doesn't have id")
        );
        //上下文
        this.context = nbt.getCompound(TAG_CONTEXT);
    }
}