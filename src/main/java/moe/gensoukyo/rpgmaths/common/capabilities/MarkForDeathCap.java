package moe.gensoukyo.rpgmaths.common.capabilities;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.pipeline.VertexLighterSmoothAo;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 标记一个实体已经死亡，造成持续的极限伤害
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class MarkForDeathCap
{
    @CapabilityInject(Instance.class)
    private static Capability<Instance> TOKEN;

    public static final ResourceLocation NAME = new ResourceLocation(
            RpgMathsMod.ID,
            "death_mark"
    );

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registry
    {
        @SubscribeEvent
        @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
        public static void onSetup(FMLCommonSetupEvent event)
        {
            CapabilityManager.INSTANCE.register(
                    Instance.class,
                    new Capability.IStorage<Instance>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<Instance> capability,
                                             Instance instance,
                                             Direction side)
                        {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<Instance> capability,
                                            Instance instance,
                                            Direction side,
                                            INBT nbt) { }
                    },
                    Instance::new
            );
        }
    }

    public static class Instance
    {
        private boolean enabled = false;
        private DamageSource source;
        public boolean isEnabled()
        {
            return enabled;
        }

        public void enable(DamageSource source)
        {
            this.enabled = true;
            this.source = source;
        }

        public void disable()
        {
            this.enabled = false;
            this.source = null;
        }

        private Instance() {}
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
     * 执行即死标记的效果
     */
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        Entity victim = event.getEntity();
        victim.getCapability(TOKEN).ifPresent(instance -> {
            if (instance.isEnabled())
            {
                if (victim instanceof LivingEntity)
                {
                    checkAndDoDamage((LivingEntity) victim, instance);
                }
                else
                {
                    instance.disable();
                }
            }
        });
    }

    private static void checkAndDoDamage(LivingEntity victim, Instance capInstance)
    {
        if (victim.isInvulnerableTo(capInstance.source))
        {
            capInstance.disable();
        }
        else
        {
            victim.attackEntityFrom(capInstance.source, Float.MAX_VALUE);
        }
    }

    /**
     * 死亡时取消超级急死标记
     */
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        if (event.getEntity().getEntityWorld().isRemote) { return; }
        event.getEntityLiving().getCapability(TOKEN).ifPresent(Instance::disable);
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
