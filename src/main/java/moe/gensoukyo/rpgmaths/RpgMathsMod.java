package moe.gensoukyo.rpgmaths;

import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.common.ModStats;
import moe.gensoukyo.rpgmaths.common.impl.RpgMathsApiImpl;
import moe.gensoukyo.rpgmaths.common.items.ItemRegistry;
import net.minecraftforge.fml.common.Mod;

/**
 * MOD主类
 * @author Chloe_koopa
 */
@Mod(RpgMathsMod.ID)
public class RpgMathsMod
{
    public static final String ID = "rpgmaths";
    public static IRpgMathsApi getApi()
    {
        return RpgMathsApiImpl.INSTANCE;
    }

    public RpgMathsMod()
    {
        ItemRegistry.init();
        ModStats.init();
        //getApi().setDamageFormula((attacker, weapon, victim) -> 1.0);
    }
}