package moe.gensoukyo.rpgmaths.common.items;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.RpgMathUtil;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

/**
 * API测试用
 * @author Chloe_koopa
 */
public class SuperKillerItem extends SwordItem
{
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

    @ObjectHolder(RpgMathsMod.ID + ":attack")
    public static IStatType TEST_STAT;


    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (worldIn.isRemote()) { return super.onItemRightClick(worldIn, playerIn, handIn); }

        RpgMathsMod.getApi().getRpgData(playerIn).ifPresent(rpgData ->
                rpgData.getStats().ifPresent(iStatHandler ->
                        playerIn.sendMessage(new StringTextComponent(
                                String.valueOf(iStatHandler.getBaseValue(TEST_STAT)) )
                        )
                )
        );
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
