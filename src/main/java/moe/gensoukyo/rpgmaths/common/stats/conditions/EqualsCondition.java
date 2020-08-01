package moe.gensoukyo.rpgmaths.common.stats.conditions;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * @author Chloe_koopa
 */
public class EqualsCondition extends AbstractCondition
{
    private final EquipmentSlotType target;
    public EqualsCondition(EquipmentSlotType target)
    {
        this.target = target;
    }
    @Override
    public boolean test(ICapabilityProvider iCapabilityProvider, EquipmentSlotType equipmentSlotType) {
        return equipmentSlotType == target;
    }
}
