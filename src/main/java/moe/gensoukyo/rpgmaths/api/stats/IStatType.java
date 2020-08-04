package moe.gensoukyo.rpgmaths.api.stats;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.util.Ordered;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * 一项属性
 * @author Chloe_koopa
 */
public interface IStatType extends IForgeRegistryEntry<IStatType>, Ordered<IStatType>
{
    /**
     * rpgmaths.stat.(namespace).(path)
     */
    String I18N_KEY_PATTERN = RpgMathsMod.ID + ".stat.%s";
    /**
     * 返回该实体的这项基础数值
     * @param owner 数据的拥有者
     * @return 不包括装备加成的数据
     */
    float getBaseValue(ICapabilityProvider owner);

    /**
     * 设置某个属性的基础值
     * @apiNote 不保证对所有值有效
     * @param owner 数据的拥有者
     * @param value 要将这个属性设置为的值（基础值）
     * @return 设置返回值是否有效
     */
    boolean setBaseValue(ICapabilityProvider owner,
                         float value);

    /**
     * 返回该实体的这项最终数值
     * @implSpec 如果entity为活体，那么需要包括装备和手上的道具的属性
     * @param entity 数据的拥有者
     * @return 包括装备加成的数据
     */
    float getFinalValue(ICapabilityProvider entity);

    /**
     * 获取描述
     * @apiNote 返回翻译密钥
     * @return 关于该项属性的描述
     */
    ITextComponent getDescription();

    /**
     * 获取属性的本地化密钥
     * @return 属性的名字
     */
    ITextComponent getName();
}