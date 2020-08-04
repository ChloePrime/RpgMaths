package moe.gensoukyo.rpgmaths.api.impl;

import moe.gensoukyo.rpgmaths.api.IRpgData;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.IRpgMathsRegistries;
import moe.gensoukyo.rpgmaths.api.damage.IDamageFormula;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

/**
 * API的默认实现
 * @author Chloe_koopa
 */
public class RpgMathsApiImpl implements IRpgMathsApi
{
    public static RpgMathsApiImpl INSTANCE = new RpgMathsApiImpl();
    private RpgMathsApiImpl() {}

    private IDamageFormula formula;
    @Override public IDamageFormula getDamageFormula()
    {
        return formula;
    }
    @Override public void setDamageFormula(IDamageFormula formula)
    {
        this.formula = formula;
    }


    @Override
    public LazyOptional<IRpgData> getRpgData(ICapabilityProvider entity)
    {
        return entity.getCapability(RPG_DATA_CAP);
    }

    @Override
    public IRpgMathsRegistries getRegisteries()
    {
        return RpgMathsRegistriesImpl.INSTANCE;
    }

    @CapabilityInject(IRpgData.class)
    public static Capability<IRpgData> RPG_DATA_CAP;
}
