package moe.gensoukyo.rpgmaths.api.stats;

import moe.gensoukyo.rpgmaths.api.damage.IDamageFormula;
import moe.gensoukyo.rpgmaths.api.impl.stats.AttributeStatType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * 默认的stat
 * @author Chloe_koopa
 */
public class ModStats {
    /**
     * 物理攻击力
     */
    public static final IStatType
            ATK = new AttributeStatType(SharedMonsterAttributes.ATTACK_DAMAGE),
    /**
     * 物理防御力
     */
    DEF = new AttributeStatType(SharedMonsterAttributes.ARMOR),
    /**
     * 法术攻击
     */
    ATS = new AttributeStatType(),
    /**
     * 法术防御
     */
    ADF = new AttributeStatType(),
    /**
     * 暴击率，100 = 1.0概率
     * @see IDamageFormula#calculateDamage(ICapabilityProvider, ItemStack, LivingEntity, double)
     */
    CRITICAL = new AttributeStatType(),
    /**
     * 全局触发几率，100 = 1.0概率
     * @see IDamageFormula#calculateDamage(ICapabilityProvider, ItemStack, LivingEntity, double)
     */
    TRIGGER_ALL = new AttributeStatType();
}
