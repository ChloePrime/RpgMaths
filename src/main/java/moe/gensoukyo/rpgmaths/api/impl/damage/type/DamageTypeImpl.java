package moe.gensoukyo.rpgmaths.api.impl.damage.type;

import com.google.common.base.Preconditions;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.function.BiConsumer;

/**
 * 标准伤害类型
 * @author Chloe_koopa
 */
public class DamageTypeImpl extends ForgeRegistryEntry<IDamageType> implements IDamageType {
    protected final EnumMap<TypedStatCategory, IStatType>
            byCategory = new EnumMap<>(TypedStatCategory.class);
    @Nullable
    protected BiConsumer<ICapabilityProvider, Double> fixedEffect;
    protected BiConsumer<ICapabilityProvider, Double> randomEffect;

    public DamageTypeImpl(@Nullable IStatType atk, @Nullable IStatType def,
                          @Nullable IStatType trig, @Nullable IStatType aTrig) {
        this(atk, def, trig, aTrig, null, null);
    }

    public DamageTypeImpl(@Nullable IStatType atk, @Nullable IStatType def,
                          @Nullable IStatType trig, @Nullable IStatType aTrig,
                          @Nullable BiConsumer<ICapabilityProvider, Double> fixedEffect,
                          @Nullable BiConsumer<ICapabilityProvider, Double> randomEffect) {
        this.byCategory.put(TypedStatCategory.ATTACK, atk);
        this.byCategory.put(TypedStatCategory.DEFENSE, def);
        this.byCategory.put(TypedStatCategory.TRIGGER, trig);
        this.byCategory.put(TypedStatCategory.ANTI_TRIGGER, aTrig);
        this.fixedEffect = fixedEffect;
        this.randomEffect = randomEffect;
    }

    @Override
    public void onEffectTriggered(ICapabilityProvider entity, double damage, boolean isDefense) {
        if (this.fixedEffect != null) {
            this.fixedEffect.accept(entity, damage);
        }
    }

    @Override
    public void onEffectRandomlyTriggered(ICapabilityProvider entity, double damage, boolean isDefense) {
        if (this.fixedEffect != null) {
            this.randomEffect.accept(entity, damage);
        }
    }

    public static final String LANG_KEY_FORMAT = "%s.damage.type.%s";
    @Nullable
    private ITextComponent trKey;
    @Nonnull
    @Override
    public ITextComponent getName() {
        if (trKey == null) {
            ResourceLocation regName = this.getRegistryName();
            Preconditions.checkNotNull(regName);

            this.trKey = new TranslationTextComponent(
                    String.format(LANG_KEY_FORMAT,regName.getNamespace(), regName.getPath()));
        }
        return this.trKey;
    }

    @Override
    public IStatType getStat(TypedStatCategory category) {
        return this.byCategory.get(category);
    }

    @Override
    public void registerStat(TypedStatCategory category, IStatType generated) {
        if (byCategory.get(category) != null) {
            throw new IllegalStateException("Repeat stat for damage type " + getRegistryName());
        }
        byCategory.put(category, generated);
    }
}
