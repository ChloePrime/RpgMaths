package moe.gensoukyo.rpgmaths;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

/**
 * 配置文件
 * @author Chloe_koopa
 */
public class RpgMathsConfig
{
    public static ForgeConfigSpec COMMON_CONFIG;
    public static BooleanValue FIX_ATTRIBUTE;
    public static BooleanValue USE_CUSTOM_TOOLTIP;
    static
    {
        final Builder builder = new Builder();
        builder.comment("General settings").push("general");
        FIX_ATTRIBUTE = builder.comment("Remove Attribute Limits")
                .define("fix_attribute", true);
        USE_CUSTOM_TOOLTIP = builder.
                comment("Use Custom Tooltip for Attribute Modifiers")
                .define("use_custom_tooltip", true);
        builder.pop();
        COMMON_CONFIG = builder.build();
    }
}
