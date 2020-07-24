package moe.gensoukyo.rpgmaths.api;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.damage.IDamageFormula;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

/**
 * @see RpgMathsMod#getApi()
 * @author Chloe_koopa
 */
public interface IRpgMathsApi {
    /**
     * 获取当前伤害公式
     * @return 当前伤害公式
     */
    IDamageFormula getDamageFormula();

    /**
     * 设置伤害公式
     * @param damageFormula 伤害公式
     */
    void setDamageFormula(IDamageFormula damageFormula);

    /**
     * 获取某个实体的RPG数据
     * @param entity 目标实体，可能是一些其他东西
     * @return 实体对应的RPG数据管理器
     */
    LazyOptional<IRpgData> getRpgData(CapabilityProvider<?> entity);

    /**
     * 获取RPG数值的注册表
     * 用于填入DeferredRegister的构造函数
     * @return RPG数值的注册表
     */
    IForgeRegistry<IStatType> getStatRegistry();

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
    IForgeRegistry<IDamageType> getDamageTypeRegistry();

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
    IForgeRegistry<IResistanceMap> getResistanceMapRegistry();

    /**
     * 获取id所表示的抗性表模板
     * @param id 需要查询的抗性表模板的id
     * @return id所表示的抗性表模板，如果无该数值则返回Empty
     */
    Optional<IResistanceMap> getResistanceMap(ResourceLocation id);
}
