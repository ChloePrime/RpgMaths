package moe.gensoukyo.rpgmaths.api;

import moe.gensoukyo.rpgmaths.common.capabilities.MarkForDeathCap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * 封装好的一些功能
 * 主要包括直接触发内置buff的方法
 * @author Chloe_koopa
 */
public class RpgMathUtil
{
    /**
     * 击杀一个实体，
     * 被击杀实体将被标记，受到持续伤害。
     * @param victim 被击杀的实体
     * @param source 伤害来源
     */
    public static void superKill(LivingEntity victim, DamageSource source)
    {
        if (victim.getEntityWorld().isRemote()) { return; }
        victim.getCapability(DEATH_MARK_CAP).ifPresent(
                instance -> instance.enable(source)
        );
    }

    @CapabilityInject(MarkForDeathCap.Instance.class)
    private static Capability<MarkForDeathCap.Instance> DEATH_MARK_CAP;
}
