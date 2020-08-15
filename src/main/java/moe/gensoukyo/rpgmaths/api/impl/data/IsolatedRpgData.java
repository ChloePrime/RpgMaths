package moe.gensoukyo.rpgmaths.api.impl.data;

import com.google.common.base.Preconditions;
import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.Constants;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.damage.type.ResistanceMapFactory;
import moe.gensoukyo.rpgmaths.api.impl.stats.StatHandlerImpl;
import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * 分离的RPG数据
 * @author Chloe_koopa
 */
public class IsolatedRpgData extends AbstractRpgData {
    @Nullable
    protected final ICapabilityProvider owner;
    private final IStatHandler statHandler;

    public IsolatedRpgData(@Nullable ICapabilityProvider owner) {
        this(null, new IDamageType[0], owner);
    }

    public IsolatedRpgData(@Nullable IResistanceMap resMapIn,
                           @Nonnull IDamageType[] defaultDamageTypeIn,
                           @Nullable ICapabilityProvider owner) {
        super(resMapIn, defaultDamageTypeIn);
        this.owner = owner;
        this.statHandler = new StatHandlerImpl(this.owner);
    }

    @Nonnull
    @Override
    public IStatHandler getStats() {
        return this.statHandler;
    }

    public static final String TAG_RES_MAP = "rm";
    public static final String TAG_DEFAULT_DTYPE = "dt";
    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT result = new CompoundNBT();
        if (this.resistanceMap != null) {
            result.put(TAG_RES_MAP, this.resistanceMap.serializeNBT());
        }
        if (this.defaultDamageTypes != null) {
            ListNBT storedTypes = new ListNBT();
            Arrays.stream(this.defaultDamageTypes)
                    .map(damageType -> {
                        Preconditions.checkNotNull(damageType.getRegistryName());
                        return StringNBT.valueOf(damageType.getRegistryName().toString());
                    })
                    .forEach(storedTypes::add);
            result.put(TAG_DEFAULT_DTYPE, storedTypes);
        }
        return result;
    }

    @Override
    public void deserializeNBT(CompoundNBT everything) {
        if (everything.contains(TAG_RES_MAP)) {
            final INBT resMapVal = everything.get(TAG_RES_MAP);
            this.resistanceMap = ResistanceMapFactory.fromTag(resMapVal);
        }
        if (everything.contains(TAG_DEFAULT_DTYPE)) {
            INBT nbt = everything.get(TAG_DEFAULT_DTYPE);
            Preconditions.checkState(nbt instanceof ListNBT);

            ListNBT storedDamageTypes = (ListNBT) nbt;
            try {
                loadDamageTypes(storedDamageTypes);
            } catch (IllegalArgumentException e) {
                RpgMathsMod.getLogger().warn(e);
                this.defaultDamageTypes = Constants.DEFAULT_DAMAGE_TYPES;
            }
        }
    }

    /**
     * @throws IllegalArgumentException 遇到未知的上海类型时抛出
     */
    private void loadDamageTypes(ListNBT storedDamageTypes) {
        this.defaultDamageTypes = new IDamageType[storedDamageTypes.size()];
        for (int i = 0; i < storedDamageTypes.size(); i++) {
            int finalI = i;
            this.defaultDamageTypes[i] = RpgMathsMod.getApi().getRegisteries()
                    .getDamageType(new ResourceLocation(storedDamageTypes.getString(i)))
                    .orElseThrow(() ->
                            new IllegalArgumentException("Illegal Damage Type Name " +
                                    storedDamageTypes.getString(finalI))
                    );
        }
    }
}
