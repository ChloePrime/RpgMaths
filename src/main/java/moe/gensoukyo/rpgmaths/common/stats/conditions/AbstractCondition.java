package moe.gensoukyo.rpgmaths.common.stats.conditions;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.stats.IStatPredicate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Objects;

public abstract class AbstractCondition
        extends ForgeRegistryEntry<IStatPredicate>
        implements IStatPredicate
{
    public static final String TR_KEY_PATTERN = RpgMathsMod.ID + ".stat.condition.%s.%s";
    private ITextComponent cache;
    @Override
    public ITextComponent getName()
    {
        Objects.requireNonNull(getRegistryName());
        ResourceLocation regName = getRegistryName();
        if (cache == null)
        {
            cache = new TranslationTextComponent(
                    String.format(TR_KEY_PATTERN, regName.getNamespace(), regName.getPath())
            );
        }
        return cache;
    }
}
