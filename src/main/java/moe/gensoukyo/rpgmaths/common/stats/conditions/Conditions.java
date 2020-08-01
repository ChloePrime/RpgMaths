package moe.gensoukyo.rpgmaths.common.stats.conditions;

import com.google.common.collect.ImmutableSet;
import moe.gensoukyo.rpgmaths.api.stats.IStatPredicate;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.Set;

public class Conditions
{
    private static final Set<EquipmentSlotType> ARMOR_SLOTS =
            new ImmutableSet.Builder<EquipmentSlotType>()
                    .add(EquipmentSlotType.HEAD)
                    .add(EquipmentSlotType.CHEST)
                    .add(EquipmentSlotType.LEGS)
                    .add(EquipmentSlotType.FEET)
                    .build();

    public static final IStatPredicate ARMOR = new AbstractCondition()
    {
        @Override
        public boolean test(ICapabilityProvider iCapabilityProvider,
                            EquipmentSlotType equipmentSlotType)
        {
            return ARMOR_SLOTS.contains(equipmentSlotType);
        }
    };

    public static final IStatPredicate MAIN_HAND = new EqualsCondition(EquipmentSlotType.MAINHAND);
    public static final IStatPredicate OFFHAND = new EqualsCondition(EquipmentSlotType.OFFHAND);
    public static final IStatPredicate ALWAYS
}
