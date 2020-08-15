package moe.gensoukyo.rpgmaths;

import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.impl.RpgMathsApiImpl;
import moe.gensoukyo.rpgmaths.api.impl.damage.formula.LinearDamageFormula;
import moe.gensoukyo.rpgmaths.common.items.ModItemRegistry;
import moe.gensoukyo.rpgmaths.common.registry.ContentRegistering;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * MOD主类
 *
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

    private static Logger logger;

    public static Logger getLogger()
    {
        return logger;
    }

    public RpgMathsMod()
    {
        logger = LogManager.getLogger(ID);
        //添加内容
        ModItemRegistry.init();
        ContentRegistering.init();
        //配置文件
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RpgMathsConfig.COMMON_CONFIG);
        getApi().setDamageFormula(new LinearDamageFormula(1.0, 1.0));
    }
}