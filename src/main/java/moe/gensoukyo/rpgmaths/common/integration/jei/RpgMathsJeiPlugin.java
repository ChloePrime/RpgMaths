package moe.gensoukyo.rpgmaths.common.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import moe.gensoukyo.rpgmaths.RpgMathsMod;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class RpgMathsJeiPlugin implements IModPlugin
{
    @Override
    @Nonnull
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(RpgMathsMod.ID, ModIds.JEI_ID);
    }
}
