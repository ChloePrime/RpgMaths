package moe.gensoukyo.rpgmaths.common.stats;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.impl.stats.AttributeStatType;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.entity.SharedMonsterAttributes;
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
public class ModStats
{
    private static final DeferredRegister<IStatType> STATS = new DeferredRegister<>(
            RpgMathsMod.getApi().getRegisteries().getStats(), RpgMathsMod.ID);

    public static void init()
    {
        STATS.register("attack", () -> new AttributeStatType(SharedMonsterAttributes.ATTACK_DAMAGE));
        STATS.register("defense", () -> new AttributeStatType(SharedMonsterAttributes.ARMOR));
        STATS.register("art_attack", AttributeStatType::new);
        STATS.register("art_defense", AttributeStatType::new);

        STATS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    /**
     * 默认排序
     */
    @ObjectHolder(RpgMathsMod.ID)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class DefaultSorting
    {
        private DefaultSorting() {}

        @ObjectHolder("attack")
        public static IStatType ATK;
        @ObjectHolder("defense")
        public static IStatType DEF;
        @ObjectHolder("art_attack")
        public static IStatType ATS;
        @ObjectHolder("art_defense")
        public static IStatType ADF;
        @SubscribeEvent
        public static void onSetupAsync(FMLCommonSetupEvent event)
        {
            init();
        }

        private static void init()
        {
            ATK.setPriority(1);
            DEF.setPriority(0);
            ATS.moveAfter(ATK);
            ADF.moveAfter(DEF);
        }
    }
}
