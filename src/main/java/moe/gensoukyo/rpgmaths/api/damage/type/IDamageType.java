package moe.gensoukyo.rpgmaths.api.damage.type;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

/**
 * 伤害类型
 * @author Chloe_koopa
 */
@ObjectHolder(RpgMathsMod.ID)
public interface IDamageType extends IForgeRegistryEntry<IDamageType>
{
    enum TypedStatCategory implements IStringSerializable {
        /* 攻击力 */
        ATTACK("attack"),
        /* 防御力 */
        DEFENSE("defense"),
        /* 触发几率 */
        TRIGGER("trigger"),
        /* 触发抵抗 */
        ANTI_TRIGGER("n_trigger");

        private final String name;
        TypedStatCategory(String name) {
            this.name = name;
        }
        @Nonnull @Override
        public String getName() {
            return name;
        }
    }

    /**
     * 获取这个伤害类型对应的进攻效果的数值
     * @param category 这个数值属于一种元素的哪一类
     * @return 影响这个攻击类型攻击力的RPG数值，在属性生成后永远不会是null
     */
    IStatType getStat(TypedStatCategory category);

    /**
     * 为伤害的某一类属性登记注册后的属性，仅用于自动生成并注册属性时使用
     * @throws IllegalStateException 如果这项属性已存在
     * @param category 这个数值属于一种元素的哪一类数值
     * @param generated 自动生成的属性
     */
    void registerStat(TypedStatCategory category, IStatType generated);

    /**
     * 该类伤害每次被施加都会触发的效果
     * @param entity 受害者
     * @param damage 伤害值
     * @param isDefense 触发这次效果的是防御方（true）还是攻击方（false）
     */
    default void onEffectTriggered(ICapabilityProvider entity, double damage, boolean isDefense) {}

    /**
     * 该类伤害被随机触发施加的效果
     * @param entity 受害者
     * @param damage 伤害值
     * @param isDefense 触发这次效果的是防御方（true）还是攻击方（false）
     */
    default void onEffectRandomlyTriggered(ICapabilityProvider entity, double damage, boolean isDefense) {}

    /**
     * 获取名称
     * @return 名称
     */
    ITextComponent getName();
}
