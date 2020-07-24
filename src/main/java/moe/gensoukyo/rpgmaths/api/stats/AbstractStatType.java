package moe.gensoukyo.rpgmaths.api.stats;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 添加了针对FinalValue的默认处理
 * @author Chloe_koopa
 */
public abstract class AbstractStatType
        extends ForgeRegistryEntry<IStatType>
        implements IStatType
{
    @CapabilityInject(IItemHandler.class)
    private static Capability<IItemHandler> ITEM_HANDLER;

    private static final Direction DIR_GET_ARMOR = Direction.EAST;

    /**
     * 本体数值+装甲内的数值+主手物品的数值
     * @param owner 数据的拥有者
     * @param context 该stat的一些上下文
     * @return 生物本体+装备栏内+主手物品的数据值
     */
    @Override
    public float getFinalValue(CapabilityProvider<?> owner, CompoundNBT context)
    {
        AtomicReference<Float> result = new AtomicReference<>(getBaseValue(owner, context));
        //添加生物的主手物品和装备的数据
        if (owner instanceof LivingEntity)
        {
            LivingEntity livingOwner = (LivingEntity) owner;
            //装备
            livingOwner.getCapability(ITEM_HANDLER, DIR_GET_ARMOR).ifPresent(iItemHandler -> {
                for (int i = 0; i < iItemHandler.getSlots(); i++)
                {
                    //lambda表达式特性
                    int finalI = i;
                    result.updateAndGet(v -> v + getFinalValue(iItemHandler.getStackInSlot(finalI), context));
                }
            });
            //主手
            result.updateAndGet(v -> v + getFinalValue(
                    livingOwner.getHeldItem(livingOwner.getActiveHand()),
                    context
            ));
        }
        return result.get();
    }
}