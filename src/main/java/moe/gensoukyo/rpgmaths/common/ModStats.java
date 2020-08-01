package moe.gensoukyo.rpgmaths.common;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.common.stats.StatWithAttributeCompatibility;
import moe.gensoukyo.rpgmaths.common.stats.StoredStatType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

/**
 * 注册玩家属性
 * @author Chloe_koopa
 */
public class ModStats {
    private static final DeferredRegister<IStatType> STATS = new DeferredRegister<>(
            RpgMathsMod.getApi().getRegisteries().getStats(), RpgMathsMod.ID);

    public static void init()
    {
        STATS.register("attack", () -> new StatWithAttributeCompatibility(SharedMonsterAttributes.ATTACK_DAMAGE));
        STATS.register("defense", () -> new StatWithAttributeCompatibility(SharedMonsterAttributes.ARMOR));
        STATS.register("art_attack", StoredStatType::new);
        STATS.register("art_defense", StoredStatType::new);

        STATS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
