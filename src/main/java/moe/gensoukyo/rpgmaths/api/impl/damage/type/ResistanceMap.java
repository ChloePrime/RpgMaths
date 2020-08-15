package moe.gensoukyo.rpgmaths.api.impl.damage.type;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IMutableResistanceMap;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.damage.type.ResistanceMapFactory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 标准抗性表
 * 支持parent是为了支持让一个攻击带有多种属性
 * 例如一个属性可能有物理/魔法的区分，以及地水火风四元素的区分
 * 这个时候，物理/魔法就作为parent
 * @author Chloe_koopa
 */
public class ResistanceMap extends AbstractResistanceMap implements IMutableResistanceMap {
    public static final String TAG_VALUES = "v";
    public static final String TAG_PARENT = "p";

    protected Map<IDamageType, Double> values = new LinkedHashMap<>();
    protected IResistanceMap parent;


    public ResistanceMap(@Nullable IResistanceMap parent) {
        this.parent = parent;
    }

    public ResistanceMap() {
        this(null);
    }

    @Override
    public double getMultiplier(final IDamageType[] types) {
        double result = 1.0;
        for (IDamageType type : types) {
            result *= values.getOrDefault(type, 1.0);
        }
        if (parent != null) {
            result *= parent.getMultiplier(types);
        }
        return result;
    }

    @Override
    public ResistanceMap setMultiplier(IDamageType type, double value) {
        values.put(type, value);
        return this;
    }

    @Override
    public String toString() {
        return "ResistanceMap{" +
                "parent=" + parent +
                ", values=" + values +
                '}';
    }

    @Override
    public INBT serializeNBT() {
        final CompoundNBT result = new CompoundNBT();

        if (this.getRegistryName() != null) {
            return StringNBT.valueOf(this.getRegistryName().toString());
        }

        if (this.parent.getRegistryName() != null) {
            result.putString(TAG_PARENT, this.parent.getRegistryName().toString());
        }

        final CompoundNBT valuesTag = new CompoundNBT();
        if (values.isEmpty()) {
            return null;
        }
        values.forEach((iDamageType, value) -> valuesTag.putDouble(
                Objects.requireNonNull(iDamageType.getRegistryName()).toString(), value)
        );

        result.put(TAG_VALUES, result);
        return valuesTag;
    }

    /**
     * 无法反序列化为已注册的抗性表
     * 建议使用 {@link ResistanceMapFactory#fromTag(INBT)}
     */
    @Override
    public void deserializeNBT(INBT nbt) {
        if (!(nbt instanceof CompoundNBT)) {
            return;
        }
        CompoundNBT compound = (CompoundNBT) nbt;
        if (compound.contains(TAG_PARENT)) {
            this.parent = ResistanceMapFactory.fromTag(compound.get(TAG_PARENT));
        }
        if (compound.contains(TAG_VALUES)) {
            deserializeValues(compound.getCompound(TAG_VALUES));
        }
    }

    private void deserializeValues(CompoundNBT values) {
        values.keySet().forEach(key ->
        {
            if (!values.contains(key)) {
                return;
            }
            API.getRegisteries().getDamageType(new ResourceLocation(key)).ifPresent(
                    iDamageType -> this.values.put(iDamageType, values.getDouble(key))
            );
        });
    }

    private static final IRpgMathsApi API = RpgMathsMod.getApi();
}