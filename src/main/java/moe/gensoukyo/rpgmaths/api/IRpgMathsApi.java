package moe.gensoukyo.rpgmaths.api;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.damage.IDamageFormula;
import moe.gensoukyo.rpgmaths.api.data.IRpgData;
import moe.gensoukyo.rpgmaths.api.data.IRpgDataDispatcher;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

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
    LazyOptional<IRpgData> getRpgData(ICapabilityProvider entity);

    /**
     * 获取RPG数据分配器
     * @return RPG数据分配器
     */
    IRpgDataDispatcher getDataDispatcher();

    /**
     * 获取可注册内容管理器
     * @return 可注册内容管理器
     */
    IRpgMathsRegistries getRegisteries();
}
