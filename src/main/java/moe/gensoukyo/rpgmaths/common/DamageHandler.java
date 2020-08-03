package moe.gensoukyo.rpgmaths.common;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.IRpgData;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.common.impl.RpgMathsApiImpl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class DamageHandler
{
    @CapabilityInject(IRpgData.class)
    public static Capability<IRpgData> RPG_CAP;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDamageEvent(LivingHurtEvent event)
    {
        if (event.getEntity().getEntityWorld().isRemote()) { return; }
        if (RpgMathsMod.getApi().getDamageFormula() == null) { return; }
        //判断是否都有RPG数据
        ICapabilityProvider attacker = event.getSource().getTrueSource();
        if (attacker == null) { return; }

        IRpgMathsApi api = RpgMathsMod.getApi();

        LivingEntity victim = event.getEntityLiving();
        boolean canRpg = (api.getRpgData(attacker).isPresent())
                && (victim.getCapability(RPG_CAP).isPresent());
        if (!canRpg) { return; }

        event.getSource().setDamageIsAbsolute();
        event.setAmount(
                (float) RpgMathsMod.getApi().getDamageFormula().calculateDamage(
                        attacker, victim
                )
        );
    }
}