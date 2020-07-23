package moe.gensoukyo.rpgmaths.common.capabilities;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.stats.IStatEntry;
import moe.gensoukyo.rpgmaths.common.capabilities.util.StorageToNbt;
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
import java.util.Optional;

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

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registry
    {
        @SubscribeEvent
        public static void onSetup(FMLCommonSetupEvent event)
        {
            CapabilityManager.INSTANCE.register(
                    Instance.class,
                    StorageToNbt.getInstance(),
                    Instance::new
            );
        }
    }

    public static class Instance implements INBTSerializable<CompoundNBT>
    {
        private final Map<IStatEntry, Float> statMap = new IdentityHashMap<>();

        @Override
        public CompoundNBT serializeNBT()
        {
            CompoundNBT result = new CompoundNBT();
            statMap.forEach((entry, value) -> {
                ResourceLocation entryName = entry.getRegistryName();
                Objects.requireNonNull(entryName,
                        "null register name for entry " + entry.getClass().getCanonicalName()
                );
                result.putFloat(entryName.toString(), value);
            });
            return result;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt)
        {
            IRpgMathsApi api = RpgMathsMod.getApi();
            //把key从字符串转换成IStatEntry，然后和value一起塞入statMap
            nbt.keySet().forEach(key ->
                    api.getStat(new ResourceLocation(key)).ifPresent(entry ->
                            statMap.put(entry, nbt.getFloat(key))
                    )
            );
        }

        public float getStat(IStatEntry stat) {
            return statMap.getOrDefault(stat, 0f);
        }

    }

    public static class Provider implements ICapabilityProvider
    {
        private Instance capInstance;

        @Nonnull
        @Override
        @SuppressWarnings("unchecked")
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                                 @Nullable Direction side) {
            return cap == TOKEN ? LazyOptional.of(() ->
                    (T) this.getOrCreate()) : LazyOptional.empty();
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
