package moe.gensoukyo.rpgmaths.common.capabilities;

import moe.gensoukyo.rpgmaths.api.IRpgData;
import moe.gensoukyo.rpgmaths.api.damage.type.IResistanceMap;
import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.common.capabilities.util.StorageToNbt;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RpgDataCap
{
    @CapabilityInject(IRpgData.class)
    protected static Capability<IRpgData> TOKEN;

    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event)
    {
        CapabilityManager.INSTANCE.register(
                IRpgData.class,
                StorageToNbt.getInstance(),
                () -> EMPTY
        );
    }
    private static final IRpgData EMPTY = new IRpgData() {
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
        public void deserializeNBT(CompoundNBT nbt) {

        }
    };
}
