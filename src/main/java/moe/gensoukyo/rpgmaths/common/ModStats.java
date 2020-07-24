package moe.gensoukyo.rpgmaths.common;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.common.stats.StoredStatType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

public class ModStats {
    private static final DeferredRegister<IStatType> STATS = new DeferredRegister<>(
            RpgMathsMod.getApi().getStatRegistry(), RpgMathsMod.ID);

    public static void init()
    {
        STATS.register("attack", StoredStatType::new);
        STATS.register("defense", StoredStatType::new);
        STATS.register("art_attack", StoredStatType::new);
        STATS.register("art_defense", StoredStatType::new);

        STATS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
