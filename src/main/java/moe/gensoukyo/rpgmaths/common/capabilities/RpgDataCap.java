package moe.gensoukyo.rpgmaths.common.capabilities;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.IRpgData;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.common.impl.stats.StatHandlerImpl;
import moe.gensoukyo.rpgmaths.common.util.StorageCapToNbt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
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
import java.util.Optional;

/**
 * 拥有RPG数据的能力
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class RpgDataCap
{
    public static final ResourceLocation NAME = new ResourceLocation(
            RpgMathsMod.ID,
            "rpg_data"
    );

    private static final IRpgData EMPTY_DATA = new IRpgData()
    {
        @Override
        public String toString() {
            return "Empty RPG Data";
        }

        @Override
        public boolean hasRpgData() {
            return false;
        }

        @Override
        public Optional<IResistanceMap> getResistance() {
            return Optional.empty();
        }

        @Override
        public Optional<IStatHandler> getStats() {
            return Optional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt)
        {

        }
    };

    @CapabilityInject(IRpgData.class)
    private static Capability<IRpgData> TOKEN;

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registering
    {
        @SubscribeEvent
        public static void onSetup(FMLCommonSetupEvent event)
        {
            CapabilityManager.INSTANCE.register(
                    IRpgData.class,
                    StorageCapToNbt.getInstance(),
                    () -> EMPTY_DATA
            );
        }
    }


    /**
     * RpgData接口的实现，
     * 同时也是附加到宿主上的对象
     * @author Chloe_koopa
     */
    protected static class RpgDataImpl implements IRpgData
    {
        private final ICapabilityProvider owner;
        private final IStatHandler statHandler;

        public RpgDataImpl(ICapabilityProvider owner)
        {
            this.owner = owner;
            this.statHandler = new StatHandlerImpl(this.owner);
        }

        @Override
        public String toString()
        {
            return "RPG Data For " + owner.toString();
        }

        @Override
        public boolean hasRpgData()
        {
            return true;
        }

        @Override
        public Optional<IResistanceMap> getResistance()
        {
            return Optional.empty();
        }

        @Override
        public Optional<IStatHandler> getStats()
        {
            return Optional.of(this.statHandler);
        }

        @Override
        public CompoundNBT serializeNBT()
        {
            return new CompoundNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt)
        {

        }
    }

    protected static class CapProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT>
    {
        @Nullable
        private IRpgData capInstance;
        private final ICapabilityProvider owner;

        public CapProvider(ICapabilityProvider owner)
        {
            this.owner = owner;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            return (cap == TOKEN)
                    ? LazyOptional.of(this::getOrCreate).cast()
                    : LazyOptional.empty();
        }

        @Nonnull
        private IRpgData getOrCreate()
        {
            if (this.capInstance == null)
            {
                this.capInstance = new RpgDataImpl(this.owner);
            }
            return this.capInstance;
        }

        @Override
        public CompoundNBT serializeNBT()
        {
            return getOrCreate().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            if (capInstance != null)
            {
                capInstance.deserializeNBT(nbt);
            }
        }
    }

    @SubscribeEvent
    public static void onCapAttachToEntity(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof LivingEntity)
        {
            attach(event);
        }
    }

    @SubscribeEvent
    public static void onCapAttachToItem(AttachCapabilitiesEvent<ItemStack> event)
    {
        attach(event);
    }

    private static <T extends ICapabilityProvider>
    void attach(AttachCapabilitiesEvent<T> event)
    {
        ICapabilityProvider o = event.getObject();
        if (o != null)
        {
            event.addCapability(NAME, new CapProvider(o));
        }
    }
}
