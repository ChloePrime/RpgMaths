package moe.gensoukyo.rpgmaths.common;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * mod所用的注册表
 * @author Chloe_koopa
 */
public class ModRegistries
{
    //RPG数据

    private static final ResourceLocation STATS_REG_NAME =
            new ResourceLocation(RpgMathsMod.ID, "stats");
    private static final IForgeRegistry<IStatType> STAT_REGISTRY = new RegistryBuilder<IStatType>()
            .setName(STATS_REG_NAME)
            .setType(IStatType.class)
            .create();
    public static IForgeRegistry<IStatType> getStatRegistry()
    {
        return STAT_REGISTRY;
    }

    //伤害类型

    private static final ResourceLocation DAMAGE_TYPE_REG_NAME =
            new ResourceLocation(RpgMathsMod.ID, "damage_type");
    private static final IForgeRegistry<IDamageType> DAMAGE_TYPE_REGISTRY = new RegistryBuilder<IDamageType>()
            .setName(DAMAGE_TYPE_REG_NAME)
            .setType(IDamageType.class)
            .allowModification()
            .create();
    public static IForgeRegistry<IDamageType> getDamageTypeRegistry()
    {
        return DAMAGE_TYPE_REGISTRY;
    }

    //抗性表预设

    private static final ResourceLocation RESISTANCE_REG_NAME =
            new ResourceLocation(RpgMathsMod.ID, "resistance_template");
    private static final IForgeRegistry<IResistanceMap> RESISTANCE_MAPS_REGISTRY = new RegistryBuilder<IResistanceMap>()
            .setName(RESISTANCE_REG_NAME)
            .setType(IResistanceMap.class)
            .set(key -> IResistanceMap.DEFAULT)
            .set((key, isNetwork) -> IResistanceMap.DEFAULT)
            .allowModification()
            .create();

    public static IForgeRegistry<IResistanceMap> getResistanceMapsRegistry()
    {
        return RESISTANCE_MAPS_REGISTRY;
    }
}