package moe.gensoukyo.rpgmaths.common.registry;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.Constants;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.ModDamageTypes;
import moe.gensoukyo.rpgmaths.api.impl.damage.type.DamageTypeStatGenerator;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.api.stats.ModStats;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ObjectHolder;

/**
 * 注册玩家属性
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContentRegistering {
    private static final DeferredRegister<IStatType> STATS = new DeferredRegister<>(
            RpgMathsMod.getApi().getRegisteries().getStats(), RpgMathsMod.ID);

    private static final DeferredRegister<IDamageType> DAMAGE_TYPES = new DeferredRegister<>(
            RpgMathsMod.getApi().getRegisteries().getDamageTypes(), RpgMathsMod.ID);

    public static void init() {
        STATS.register(Constants.STAT_ATK, () -> ModStats.ATK);
        STATS.register(Constants.STAT_DEF, () -> ModStats.DEF);
        STATS.register(Constants.STAT_ATS, () -> ModStats.ATS);
        STATS.register(Constants.STAT_ADF, () -> ModStats.ADF);
        STATS.register(Constants.STAT_CRITICAL, () -> ModStats.CRITICAL);
        STATS.register(Constants.STAT_TRIGGER_ALL, () -> ModStats.TRIGGER_ALL);

        DAMAGE_TYPES.register(Constants.TYPE_PHYSICS, () -> ModDamageTypes.PHYSICS);
        DAMAGE_TYPES.register(Constants.TYPE_ARTS, () -> ModDamageTypes.MAGIC);


        STATS.register(FMLJavaModLoadingContext.get().getModEventBus());
        DAMAGE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<IStatType> event) {
        DamageTypeStatGenerator.genAndRegister(event.getRegistry(), ModDamageTypes.PHYSICS);
        DamageTypeStatGenerator.genAndRegister(event.getRegistry(), ModDamageTypes.MAGIC);
    }

    /**
     * 默认排序
     */
    @ObjectHolder(RpgMathsMod.ID)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class DefaultSorting {
        private DefaultSorting() {
        }

        @ObjectHolder("attack")
        public static IStatType ATK;
        @ObjectHolder("defense")
        public static IStatType DEF;
        @ObjectHolder("art_attack")
        public static IStatType ATS;
        @ObjectHolder("art_defense")
        public static IStatType ADF;

        @SubscribeEvent
        public static void onSetupAsync(FMLCommonSetupEvent event) {
            init();
        }

        private static void init() {
            ATK.setPriority(1);
            DEF.setPriority(0);
            ATS.moveAfter(ATK);
            ADF.moveAfter(DEF);
        }
    }
}
