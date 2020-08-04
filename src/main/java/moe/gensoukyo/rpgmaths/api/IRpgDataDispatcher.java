package moe.gensoukyo.rpgmaths.api;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.function.Predicate;

public interface IRpgDataDispatcher
{
    /**
     * 为某个实体附加
     * @param whoHasData 判断谁拥有这个数据
     * @param rpgData 要附加的数据
     */
    void addDataFor(Predicate<ICapabilityProvider> whoHasData, IRpgData rpgData);

    default void addMobData(Predicate<Entity> whoHasData, IRpgData rpgData)
    {
        addDataFor(iCapabilityProvider -> {
            if (iCapabilityProvider instanceof Entity) {
                Entity entity = (Entity) iCapabilityProvider;
                return whoHasData.test(entity);
            }
            return false;
        }, rpgData);
    }

    default void addItemData(Predicate<ItemStack> whoHasData, IRpgData rpgData)
    {
        addDataFor(iCapabilityProvider ->
        {
            if (iCapabilityProvider instanceof ItemStack) {
                ItemStack stack = (ItemStack) iCapabilityProvider;
                return whoHasData.test(stack);
            }
            return false;
        }, rpgData);
    }

    /**
     * 从该分配器获取RPG数据
     * @param entity 可能拥有数据的对象
     * @return RPG数据
     */
    IRpgData getData(ICapabilityProvider entity);
}
