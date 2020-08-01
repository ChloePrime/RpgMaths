package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.BiPredicate;

public interface IStatPredicate extends
        BiPredicate<ICapabilityProvider, EquipmentSlotType>,
        IForgeRegistryEntry<IStatPredicate>
{
    /**
     * 决定属性是否生效
     * @param iCapabilityProvider 数据拥有者
     * @param equipmentSlotType 装备栏种类
     * @return 属性是否生效
     */
    @Override
    boolean test(ICapabilityProvider iCapabilityProvider, EquipmentSlotType equipmentSlotType);

    /**
     * 获取条件名称
     * @return 条件名称，例如"当穿在身上时"
     */
    ITextComponent getName();
}
