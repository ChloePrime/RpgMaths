package moe.gensoukyo.rpgmaths.api.impl.data;

import moe.gensoukyo.rpgmaths.api.data.IRpgData;
import moe.gensoukyo.rpgmaths.api.data.IRpgDataDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Chloe_koopa
 */
public class RpgDataDispatcherImpl implements IRpgDataDispatcher {
    private RpgDataDispatcherImpl() {
    }

    private static final RpgDataDispatcherImpl INSTANCE = new RpgDataDispatcherImpl();

    public static RpgDataDispatcherImpl get() {
        return INSTANCE;
    }

    private final Map<ICapabilityProvider, IRpgData> cache = new LinkedHashMap<>();
    private final Map<Predicate<ICapabilityProvider>, IRpgData> loaders = new LinkedHashMap<>();

    @Override
    public void addDataFor(Predicate<ICapabilityProvider> whoHasData, IRpgData rpgData) {
        loaders.put(whoHasData, rpgData);
    }

    @Override
    public Optional<IRpgData> getData(ICapabilityProvider entity) {
        if (entity instanceof PlayerEntity) {
            return Optional.of(new IsolatedRpgData(entity));
        }
        if (cache.containsKey(entity)) {
            return Optional.ofNullable(cache.get(entity));
        } else {
            IRpgData result = null;
            //玩家永远拥有RPG数据
            for (Map.Entry<Predicate<ICapabilityProvider>, IRpgData>
                    entry : loaders.entrySet()) {
                if (entry.getKey().test(entity)) {
                    result = entry.getValue();
                    break;
                }
            }
            cache.put(entity, result);
            return Optional.ofNullable(result);
        }
    }
}