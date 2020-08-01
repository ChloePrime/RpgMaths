package moe.gensoukyo.rpgmaths.common.stats;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 负责把 {@link AttributeBackendStat} 新增加的 Attribute 附加到实体身上
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class AdditionalStatHandler
{
    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity) event.getEntity();
            AttributeBackendStat.STATS_WITH_CUSTOM_ATTRIBUTE.forEach(stat ->
                    entity.getAttributes().registerAttribute(stat.getBackend())
            );
        }
    }
}
