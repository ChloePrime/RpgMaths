package moe.gensoukyo.rpgmaths.api.damage.type;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.IRpgMathsRegistries;
import moe.gensoukyo.rpgmaths.api.impl.damage.type.ResistanceMap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Chloe_koopa
 */
public class ResistanceMapFactory
{
    private ResistanceMapFactory() {}

    /**
     * 将一个NBT反序列化为抗性表
     * @param tag 一整串NBT
     * @return 如果tag为StringNBT且有对应的已注册抗性表，则返回注册表
     */
    public static IResistanceMap fromTag(@Nullable INBT tag)
    {
        //tag是字符串，说明引用已注册的抗性表
        if (tag instanceof StringNBT)
        {
            Optional<IResistanceMap> optionalResult = HANDLER.getResistanceMap(
                    new ResourceLocation(tag.getString())
            );
            if (optionalResult.isPresent())
            {
                return optionalResult.get();
            }
            else
            {
                RpgMathsMod.getLogger().warn("Unknown resistance map preset " + tag.getString());
            }
        }
        //独立的抗性表
        IResistanceMap result = new ResistanceMap();
        if (tag instanceof CompoundNBT)
        {
            result.deserializeNBT(tag);
        }
        return result;
    }

    private static final IRpgMathsRegistries HANDLER = RpgMathsMod.getApi().getRegisteries();
}
