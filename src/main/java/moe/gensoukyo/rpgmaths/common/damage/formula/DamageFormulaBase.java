package moe.gensoukyo.rpgmaths.common.damage.formula;

import com.google.common.util.concurrent.Atomics;
import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.damage.IDamageFormula;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.ObjectHolder;

import java.util.concurrent.atomic.AtomicReference;

@ObjectHolder(RpgMathsMod.ID)
public abstract class DamageFormulaBase implements IDamageFormula
{
    @ObjectHolder("attack")
    protected static IStatType ATK;
    @ObjectHolder("defense")
    protected static IStatType DEF;

    protected static final IRpgMathsApi API = RpgMathsMod.getApi();

    protected float getFinalAttack(ICapabilityProvider attacker)
    {
        return getFinalValue(attacker, ATK);
    }

    protected float getFinalDefense(ICapabilityProvider attacker)
    {
        return getFinalValue(attacker, DEF);
    }

    protected float getFinalValue(ICapabilityProvider attacker, IStatType type)
    {
        AtomicReference<Float> value = Atomics.newReference(0f);
        API.getRpgData(attacker).ifPresent(iRpgData ->
                iRpgData.getStats().ifPresent(iStatHandler ->
                        value.set(iStatHandler.getFinalValue(type))
                )
        );
        return value.get();
    }
}
