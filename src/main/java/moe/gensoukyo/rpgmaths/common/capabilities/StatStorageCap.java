package moe.gensoukyo.rpgmaths.common.capabilities;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.common.util.StorageCapToNbt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * RPG数值的存储
 * 存储一些“节外生枝”型的数值（例如暴击，闪避等）
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class StatStorageCap
{
    @CapabilityInject(Instance.class)
    private static Capability<Instance> TOKEN;

    public static final ResourceLocation NAME = new ResourceLocation(
            RpgMathsMod.ID,
            "stats"
    );

    /**
     * 监听mod总线事件并注册
     * @author Chloe_koopa
     */
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    protected static class Registering
    {
        @SubscribeEvent
        public static void onSetup(FMLCommonSetupEvent event)
        {
            CapabilityManager.INSTANCE.register(
                    Instance.class,
                    StorageCapToNbt.getInstance(),
                    Instance::new
            );
        }
    }

    /**
     * Capability实例
     */
    public static class Instance
            implements INBTSerializable<CompoundNBT>
    {
        private final Map<IStatType, Double> statMap = new IdentityHashMap<>();

        public Double getStat(IStatType stat)
        {
            return statMap.getOrDefault(stat, 0d);
        }
        public void setStat(IStatType stat, double value)
        {
            statMap.put(stat, value);
        }
        @Override
        public CompoundNBT serializeNBT()
        {
            CompoundNBT result = new CompoundNBT();
            statMap.forEach((entry, value) -> {
                ResourceLocation entryName = entry.getRegistryName();
                Objects.requireNonNull(entryName,
                        "null register name for entry " + entry.getClass().getCanonicalName()
                );
                result.putDouble(entryName.toString(), value);
            });
            return result;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt)
        {
            IRpgMathsApi api = RpgMathsMod.getApi();
            //把key从字符串转换成IStatEntry，然后和value一起塞入statMap
            nbt.keySet().forEach(key ->
                    api.getRegisteries().getStat(new ResourceLocation(key)).ifPresent(entry ->
                            statMap.put(entry, nbt.getDouble(key))
                    )
            );
        }

    }

    /**
     * 用来黏贴到Capability提供者身上
     * @author Chloe_koopa
     */
    protected static class Provider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
    {
        @Nullable
        private Instance capInstance;

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                                 @Nullable Direction side) {
            return (cap == TOKEN)
                    ? LazyOptional.of(this::getOrCreate).cast()
                    : LazyOptional.empty();
        }

        @Nonnull
        private Instance getOrCreate()
        {
            if (this.capInstance == null)
            {
                this.capInstance = new Instance();
            }
            return this.capInstance;
        }

        private Provider() {}

        @Override
        public CompoundNBT serializeNBT()
        {
            return getOrCreate().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt)
        {
            getOrCreate().deserializeNBT(nbt);
        }
    }

    /**
     * 附加能力到活体
     */
    @SubscribeEvent
    public static void onCapAttach(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof LivingEntity)
        {
            event.addCapability(NAME, new Provider());
        }
    }
}
