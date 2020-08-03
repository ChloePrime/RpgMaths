package moe.gensoukyo.rpgmaths;

import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.common.stats.ModStats;
import moe.gensoukyo.rpgmaths.common.impl.RpgMathsApiImpl;
import moe.gensoukyo.rpgmaths.common.items.ModItemRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

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
        //添加内容
        ModItemRegistry.init();
        ModStats.init();
        //配置文件
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RpgMathsConfig.COMMON_CONFIG);
        //getApi().setDamageFormula((attacker, weapon, victim) -> 1.0);
    }
}