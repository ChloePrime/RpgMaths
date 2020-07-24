package moe.gensoukyo.rpgmaths.common.impl;

import moe.gensoukyo.rpgmaths.api.IRpgData;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.damage.IDamageFormula;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.common.ModRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

/**
 * @author Chloe_koopa
 */
public class RpgMathsApiImpl implements IRpgMathsApi
{
    public static RpgMathsApiImpl INSTANCE = new RpgMathsApiImpl();
    private RpgMathsApiImpl() {}

    @Override
    public IDamageFormula getDamageFormula()
    {
        return formula;
    }

    @Override
    public void setDamageFormula(IDamageFormula formula)
    {
        this.formula = formula;
    }

    private IDamageFormula formula;

    @Override
    public LazyOptional<IRpgData> getRpgData(CapabilityProvider<?> entity)
    {
        return entity.getCapability(RPG_DATA_CAP);
    }

    @CapabilityInject(IRpgData.class)
    public static Capability<IRpgData> RPG_DATA_CAP;

    @Override
    public IForgeRegistry<IStatType> getStatRegistry()
    {
        return ModRegistries.getStatRegistry();
    }

    @Override
    public Optional<IStatType> getStat(ResourceLocation id)
    {
        return Optional.ofNullable(ModRegistries.getStatRegistry().getValue(id));
    }


    @Override
    public IForgeRegistry<IDamageType> getDamageTypeRegistry()
    {
        return ModRegistries.getDamageTypeRegistry();
    }

    @Override
    public Optional<IDamageType> getDamageType(ResourceLocation id)
    {
        return Optional.ofNullable(
                ModRegistries.getDamageTypeRegistry().getValue(id)
        );
    }

    @Override
    public IForgeRegistry<IResistanceMap> getResistanceMapRegistry()
    {
        return ModRegistries.getResistanceMapsRegistry();
    }

    @Override
    public Optional<IResistanceMap> getResistanceMap(ResourceLocation id)
    {
        return Optional.ofNullable(
                ModRegistries.getResistanceMapsRegistry().getValue(id)
        );
    }

}
