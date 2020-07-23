package moe.gensoukyo.rpgmaths.api.damage.type;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * 伤害类型
 * @author Chloe_koopa
 */
public interface IDamageType extends IForgeRegistryEntry<IDamageType>
{
    /**
     * 该类伤害每次被施加都会触发的效果
     * @param victim 受害者
     * @param damage 伤害值
     */
    default void onEffectTriggered(LivingEntity victim, double damage) {};
    /**
     * 该类伤害被随机触发施加的效果
     * @param victim 受害者
     * @param damage 伤害值
     */
    default void onEffectRandomlyTriggered(LivingEntity victim, double damage) {};
}
