package moe.gensoukyo.rpgmaths.common.items;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import net.minecraft.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Chloe_koopa
 */
public class ModItemRegistry {
    private static final DeferredRegister<Item> ITEMS =
            new DeferredRegister<>(ForgeRegistries.ITEMS, RpgMathsMod.ID);

    public static void init()
    {
        ITEMS.register("super_killer", SuperKillerItem::new);

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}