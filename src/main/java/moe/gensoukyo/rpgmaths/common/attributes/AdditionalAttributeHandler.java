package moe.gensoukyo.rpgmaths.common.attributes;

import moe.gensoukyo.rpgmaths.api.impl.stats.AttributeStatType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static moe.gensoukyo.rpgmaths.api.impl.stats.AttributeStatType.INSTANCES;
import static moe.gensoukyo.rpgmaths.api.impl.stats.AttributeStatType.STATS_WITH_CUSTOM_ATTRIBUTE;

/**
 * 负责把 {@link AttributeStatType} 新增加的 Attribute 附加到实体身上
 * @see AttributeStatType
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class AdditionalAttributeHandler
{
    /**
     * <a href = "https://github.com/SleepyTrousers/EnderIO/blob/master/enderio-base/src/main/java/crazypants/enderio/base/handler/darksteel/PlayerAOEAttributeHandler.java">
     *     see this
     * </a>
     */
    @SubscribeEvent
    public static void onEntityNew(EntityEvent.EntityConstructing event)
    {
        attachAttr(event);
    }

    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinWorldEvent event)
    {
        attachAttr(event);
    }

    private static void attachAttr(EntityEvent event)
    {
        if (event.getEntity() instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity) event.getEntity();
            //防止重复注册
            //noinspection ConstantConditions
            if (entity.getAttribute(getMark().getBackend()) != null)
            {
                return;
            }
            STATS_WITH_CUSTOM_ATTRIBUTE.forEach(stat ->
                    entity.getAttributes().registerAttribute(stat.getBackend())
            );
        }
    }

    private static AttributeStatType getMark()
    {
        if (MARK == null)
        {
            MARK = STATS_WITH_CUSTOM_ATTRIBUTE.iterator().next();
        }
        return MARK;
    }
    private static AttributeStatType MARK;

    //解决死亡后Attribute消失的问题

    @SubscribeEvent
    public static void onPlayerDied(LivingDeathEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            INSTANCES.forEach(stat -> stat.storeToCap(event.getEntity()));
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            INSTANCES.forEach(stat -> stat.recoverFromCap(event.getPlayer(), event.getOriginal()));
        }
    }

}
