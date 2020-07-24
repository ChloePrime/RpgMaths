package moe.gensoukyo.rpgmaths.api;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.function.Predicate;

public interface IRpgDataDispatcher
{
    /**
     * 为某个实体附加
     * @param whoHasData 判断谁拥有这个数据
     * @param rpgData 要附加的数据
     * @param <T> 宿主是何种类型
     */
    <T extends ICapabilityProvider>
    void addDataFor(Predicate<T> whoHasData, IRpgData rpgData);

    /**
     * 从该分配器获取RPG数据
     * @param entity 可能拥有数据的对象
     * @return RPG数据
     */
    IRpgData getData(ICapabilityProvider entity);
}
