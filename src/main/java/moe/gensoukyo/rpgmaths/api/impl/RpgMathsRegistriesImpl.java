package moe.gensoukyo.rpgmaths.api.impl;

import moe.gensoukyo.rpgmaths.api.IRpgMathsRegistries;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.common.ModRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

/**
 * @author Chloe_koopa
 */
public class RpgMathsRegistriesImpl implements IRpgMathsRegistries
{
    static final RpgMathsRegistriesImpl INSTANCE = new RpgMathsRegistriesImpl();
    private RpgMathsRegistriesImpl() {}
    @Override
    public final IForgeRegistry<IStatType> getStats()
    {
        return ModRegistries.getStatRegistry();
    }

    @Override
    public final Optional<IStatType> getStat(ResourceLocation id)
    {
        return Optional.ofNullable(this.getStats().getValue(id));
    }

    @Override
    public final IForgeRegistry<IDamageType> getDamageTypes()
    {
        return ModRegistries.getDamageTypeRegistry();
    }

    @Override
    public final Optional<IDamageType> getDamageType(ResourceLocation id)
    {
        return Optional.ofNullable(this.getDamageTypes().getValue(id));
    }

    @Override
    public final IForgeRegistry<IResistanceMap> getResistanceMaps()
    {
        return ModRegistries.getResistanceMapsRegistry();
    }

    @Override
    public final Optional<IResistanceMap> getResistanceMap(ResourceLocation id)
    {
        return Optional.ofNullable(this.getResistanceMaps().getValue(id));
    }
}
