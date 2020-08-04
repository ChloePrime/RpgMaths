package moe.gensoukyo.rpgmaths.api.stats;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.util.Order;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 添加了针对FinalValue的默认处理
 * @author Chloe_koopa
 */
public abstract class AbstractStatType
        extends ForgeRegistryEntry<IStatType>
        implements IStatType
{
    protected Order order = new Order();

    @Nonnull
    @Override
    public Order getOrder()
    {
        return this.order;
    }

    @CapabilityInject(IItemHandler.class)
    private static Capability<IItemHandler> ITEM_HANDLER;

    private static final Direction DIR_GET_ARMOR = Direction.EAST;

    /**
     * 本体数值+装甲内的数值+主手物品的数值
     * @param owner 数据的拥有者
     * @return 生物本体+装备栏内+主手物品的数据值
     */
    @Override
    public float getFinalValue(ICapabilityProvider owner)
    {
        AtomicReference<Float> result = new AtomicReference<>(getBaseValue(owner));
        //添加生物的主手物品和装备的数据
        //装备
        owner.getCapability(ITEM_HANDLER, DIR_GET_ARMOR).ifPresent(iItemHandler -> {
            for (int i = 0; i < iItemHandler.getSlots(); i++)
            {
                //lambda表达式特性
                int finalI = i;
                result.updateAndGet(v -> v + getFinalValue(iItemHandler.getStackInSlot(finalI)));
            }
        });
        if (owner instanceof LivingEntity)
        {
            LivingEntity livingOwner = (LivingEntity) owner;
            //主手
            result.updateAndGet(v -> v + getFinalValue(
                    livingOwner.getHeldItem(livingOwner.getActiveHand())
            ));
        }
        return result.get();
    }


    private ITextComponent cachedTrKey;
    @Nonnull
    @Override
    public ITextComponent getName()
    {
        Objects.requireNonNull(this.getRegistryName(), "Stat Name Got Before Named");
        if (this.cachedTrKey == null)
        {
            Objects.requireNonNull(getRegistryName());
            this.cachedTrKey = new TranslationTextComponent(
                    String.format(I18N_KEY_PATTERN, getRegistryName()
                            .toString().replace(":", ".")
                    )
            );
        }
        return this.cachedTrKey;
    }

    public static final String DESC_KEY_PATTERN = "%s." + RpgMathsMod.ID + ".stat.%s";

    @Override
    public ITextComponent getDescription()
    {
        return getDescKey();
    }

    private ITextComponent cachedDescKey;
    private ITextComponent getDescKey()
    {
        Objects.requireNonNull(this.getRegistryName(), "Stat Description Got Before Named");
        ResourceLocation regName = this.getRegistryName();
        if (this.cachedDescKey == null)
        {
            this.cachedDescKey = new TranslationTextComponent(
                    String.format(DESC_KEY_PATTERN, regName.getNamespace(), regName.getPath())
            );
        }
        return this.cachedDescKey;
    }
}