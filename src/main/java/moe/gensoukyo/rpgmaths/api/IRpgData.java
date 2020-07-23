package moe.gensoukyo.rpgmaths.api;

import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Optional;

/**
 * RPG数据管理器
 * @author Chloe_koopa
 */
public interface IRpgData extends INBTSerializable<CompoundNBT>
{
    /**
     * 控制该宿主是否有RPG数据
     * @return 该宿主是否有RPG数据
     */
    boolean hasRpgData();
    /**
     * 获取抗性列表
     * @return 抗性列表，如果宿主没有RPG数据，那么返回empty
     */
    Optional<IResistanceMap> getResistance();
    /**
     * 获取属性列表
     * @return 属性列表，如果宿主没有RPG数据，那么返回empty
     */
    Optional<IStatHandler> getStats();
}