package moe.gensoukyo.rpgmaths.api.damage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

/**
 * @author Chloe_koopa
 * 伤害公式
 */
@FunctionalInterface
public interface IDamageFormula {
    /**
     * 计算伤害
     *
     * @param attacker 攻击者
     * @param weapon   攻击者攻击所用道具（通常为武器）
     * @param victim   被攻击者
     * @return 伤害值
     */
    double calculateDamage(ICapabilityProvider attacker,
                           ItemStack weapon,
                           LivingEntity victim);

    /**
     * 计算伤害，方便版，等效于使用空气攻击
     *
     * @param attacker 攻击者
     * @param victim   被攻击者
     * @return 伤害值
     */
    default double calculateDamage(ICapabilityProvider attacker,
                                   LivingEntity victim) {
        final ItemStack weapon;
        if (attacker instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) attacker;
            weapon = living.getHeldItem(living.getActiveHand());
        } else {
            weapon = attacker.getCapability(Cap.TOKEN, Direction.UP)
                    .map(iItemHandler -> iItemHandler.getStackInSlot(0))
                    .orElse(ItemStack.EMPTY);
        }
        return calculateDamage(attacker, weapon, victim);
    }

    class Cap {
        @CapabilityInject(IItemHandler.class)
        public static Capability<IItemHandler> TOKEN;

        private Cap() {}
    }
}