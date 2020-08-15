package moe.gensoukyo.rpgmaths;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;

/**
 * 配置文件
 * @author Chloe_koopa
 */
public class RpgMathsConfig
{
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final BooleanValue FIX_ATTRIBUTE;
    public static final BooleanValue USE_CUSTOM_TOOLTIP;
    public static final EnumValue<TextFormatting> VANILLA_ATTRIBUTE_STYLE;
    static
    {
        final Builder builder = new Builder();
        builder.comment("General settings").push("general");
        FIX_ATTRIBUTE = builder.comment("Remove Attribute Limits")
                .define("fix_attribute", true);
        USE_CUSTOM_TOOLTIP = builder.
                comment("Use Custom Tooltip for Attribute Modifiers")
                .define("use_custom_tooltip", true);
        VANILLA_ATTRIBUTE_STYLE = builder.
                comment("Style for vanilla attribute tooltip,",
                        "Note: Style for Rpg Stats are defined in tha Language File.")
                .defineEnum("van_attr_style", TextFormatting.DARK_GREEN);
        builder.pop();
        COMMON_CONFIG = builder.build();
    }
}
