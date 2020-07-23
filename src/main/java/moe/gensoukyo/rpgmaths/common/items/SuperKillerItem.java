package moe.gensoukyo.rpgmaths.common.items;

import moe.gensoukyo.rpgmaths.api.RpgMathUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

/**
 * API测试用
 * @author Chloe_koopa
 */
public class SuperKillerItem extends SwordItem {
    public SuperKillerItem()
    {
        super(ItemTier.DIAMOND, 0, -2.4f,
                new Properties().group(ItemGroup.COMBAT));
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack,
                             @Nonnull LivingEntity target,
                             @Nonnull LivingEntity attacker)
    {
        RpgMathUtil.superKill(target, DamageSource.GENERIC);
        return super.hitEntity(stack, target, attacker);
    }
}
