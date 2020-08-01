package moe.gensoukyo.rpgmaths.common.stats;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 将一个Attribute作为实现方式的RPG属性
 * @see IAttribute
 * @author Chloe_koopa
 */
public class StatWithAttributeCompatibility
        extends StoredStatType
{
    @Nonnull
    private final IAttribute attribute;

    /**
     * 指定一个attribute作为兼容值
     * @param attribute 提供兼容的Attribute
     */
    public StatWithAttributeCompatibility(@Nonnull IAttribute attribute)
    {
        this.attribute = attribute;
    }

    protected static final String ATTR_NAME_PATTERN = RpgMathsMod.ID + ".stat.%s";

    @Override
    public float getBaseValue(ICapabilityProvider owner)
    {
        float append = 0f;
        if (owner instanceof LivingEntity)
        {
            LivingEntity living = (LivingEntity) owner;
            append += (float) living.getAttribute(this.attribute).getBaseValue();
        }
        return append + super.getBaseValue(owner);
    }

    @Override
    public float getFinalValue(ICapabilityProvider owner)
    {
        float append = 0f;
        if (owner instanceof LivingEntity)
        {
            LivingEntity living = (LivingEntity) owner;
            append += (float) living.getAttribute(this.getAttribute()).getValue();
        }
        return append + super.getFinalValue(owner);
    }

    @Override
    public ITextComponent getDescription() {
        return null;
    }

    /**
     * 该对象基于引用判断相等
     */
    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
