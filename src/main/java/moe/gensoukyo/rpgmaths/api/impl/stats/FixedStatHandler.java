package moe.gensoukyo.rpgmaths.api.impl.stats;

import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * 固定的数值存储器
 * 使用一个Map来存储{@link IStatType}与{@link Double}之间的映射
 * @see IStatHandler
 * @author Chloe_koopa
 */
public class FixedStatHandler implements IStatHandler {
    private final Map<IStatType, Double> values = new IdentityHashMap<>();

    @Override
    public double getBaseValue(IStatType type) {
        return values.getOrDefault(type, 0d);
    }

    @Override
    public double getFinalValue(IStatType type) {
        return getBaseValue(type);
    }

    @Override
    public boolean setBaseValue(IStatType type, double value) {
        values.put(type, value);
        return true;
    }
}
