package moe.gensoukyo.rpgmaths.api;

import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatPredicate;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

/**
 * 各种可注册内容
 * @see moe.gensoukyo.rpgmaths.api.stats.IStatType
 * @see moe.gensoukyo.rpgmaths.api.stats.IStatPredicate
 * @see moe.gensoukyo.rpgmaths.api.damage.type.IDamageType
 * @see moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap
 * @author Chloe_koopa
 */
public interface IRpgMathsRegistries
{
    /**
     * 获取RPG数值的注册表
     * 用于填入DeferredRegister的构造函数
     * @return RPG数值的注册表
     */
    IForgeRegistry<IStatType> getStats();
    /**
     * 获取一项实体数值
     * @param id 需要查询的实体数值的id
     * @return id所表示的实体RPG数据类型，如果无该数值则返回Empty
     */
    Optional<IStatType> getStat(ResourceLocation id);

    /**
     * 获取伤害类型的注册表
     * 用于填入DeferredRegister的构造函数
     * @return 伤害类型的注册表
     */
    IForgeRegistry<IDamageType> getDamageTypes();
    /**
     * 获取id所表示的伤害类型
     * @param id 需要查询的伤害类型的id
     * @return id所表示的伤害类型，如果无该数值则返回Empty
     */
    Optional<IDamageType> getDamageType(ResourceLocation id);

    /**
     * 获取抗性表模板的注册表
     * 用于填入DeferredRegister的构造函数
     * @return 抗性表模板的注册表
     */
    IForgeRegistry<IResistanceMap> getResistanceMaps();
    /**
     * 获取id所表示的抗性表模板
     * @param id 需要查询的抗性表模板的id
     * @return id所表示的抗性表模板，如果无该数值则返回Empty
     */
    Optional<IResistanceMap> getResistanceMap(ResourceLocation id);

    /**
     * 获取属性生效条件的注册表
     * 用于填入DeferredRegister的构造函数
     * @return 属性生效条件的注册表
     */
    IForgeRegistry<IStatPredicate> getStatConditions();
    /**
     * 获取id所表示的属性生效条件
     * @param id 需要查询的属性生效条件的id
     * @return id所表示的属性生效条件，如果无该数值则返回Empty
     */
    Optional<IStatPredicate> getStatCondition(ResourceLocation id);
}
