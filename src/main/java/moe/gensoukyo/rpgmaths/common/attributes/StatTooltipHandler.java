package moe.gensoukyo.rpgmaths.common.attributes;

import moe.gensoukyo.rpgmaths.RpgMathsConfig;
import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.impl.stats.AttributeStatType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 排序并美化原版AttributeModifier的Tooltip显示
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class StatTooltipHandler {
    @Nullable
    static PlayerEntity curPlayer;

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (RpgMathsConfig.USE_CUSTOM_TOOLTIP.get()) {
            curPlayer = event.getPlayer();
            List<ITextComponent> tooltip = event.getToolTip();
            replaceVanilla(tooltip);
        }
    }

    private static void replaceVanilla(List<ITextComponent> tooltip) {
        try {
            //如果不提取哪一块是Attribute的Tooltip的话，就无法对tooltip排序
            for (int i = 0; i < tooltip.size(); i++) {
                ITextComponent cur = tooltip.get(i);
                if (isVanillaTooltipHeader(cur)) {
                    replaceSection(tooltip, i + 1);
                }
            }
        } catch (RuntimeException e) {
            tooltip.add(new StringTextComponent("errored... \n" + e));
        }
    }

    private static void replaceSection(List<ITextComponent> tooltip, final int lineNum) {
        List<LineInfo> statToReorder = new ArrayList<>(tooltip.size());
        int cursor = lineNum;
        while (true) {
            if (cursor >= tooltip.size()) {
                break;
            }
            ITextComponent cur = tooltip.get(cursor);
            if (!isVanillaTooltipEntry(cur)) {
                break;
            } else {
                //获取这一行代表的Stat
                LineInfo result = extractLine(tooltip.get(cursor));
                //如果这个attribute没有对应的stat，则保留原版值
                if (result == null) {
                    cursor++;
                } else
                //反之，删除这一行并添加到待重排序列表中
                {
                    tooltip.remove(cursor);
                    statToReorder.add(result);
                }
            }
        }
        tooltip.addAll(lineNum, reOrder(statToReorder));
    }

    /**
     * 例："在主手时："
     */
    private static final String VAN_TOOLTIP_HEADER = "item.modifiers.";
    private static final String VAN_POTION_TOOLTIP_HEADER = "potion.whenDrank";
    /**
     * 例："+5攻击伤害"
     */
    private static final String VAN_TOOLTIP_ENTRY_HEADER = "attribute.modifier.";

    private static boolean isVanillaTooltipHeader(ITextComponent textComponent) {
        if (textComponent instanceof TranslationTextComponent) {
            String key = ((TranslationTextComponent) textComponent).getKey();
            return (key.startsWith(VAN_TOOLTIP_HEADER) || key.startsWith(VAN_POTION_TOOLTIP_HEADER));
        }
        return false;
    }

    private static boolean isVanillaTooltipEntry(ITextComponent textComponent) {
        if (textComponent instanceof StringTextComponent) {
            StringTextComponent str = (StringTextComponent) textComponent;
            return " ".equals(str.getText());
        } else if (textComponent instanceof TranslationTextComponent) {
            TranslationTextComponent tr = (TranslationTextComponent) textComponent;
            return tr.getKey().startsWith(VAN_TOOLTIP_ENTRY_HEADER);
        }
        return false;
    }

    /**
     *
     */
    private static final String ATTR_NAME_PREFIX = "attribute.name.";
    /**
     * rpgmaths.attribute.modifier.operator.0
     * rpgmaths.attribute.modifier.operator.1
     * rpgmaths.attribute.modifier.operator.2
     */
    public static final String NEW_ATTR_KEY = RpgMathsMod.ID + ".attribute.modifier.";
    /**
     * rpgmaths.attribute.modifier.operator.add
     * rpgmaths.attribute.modifier.operator.subtract
     */
    public static final String NEW_ATTR_OP_KEY = NEW_ATTR_KEY + "operator.";

    /**
     * 从一个代表一行Attribute的本地化文本中提取 {@link AttributeStatType}
     */
    @Nullable
    private static LineInfo extractLine(ITextComponent input) {
        try {
            if (input instanceof TranslationTextComponent) {
                return extractLine0((TranslationTextComponent) input);
            } else if (input instanceof StringTextComponent) {
                return extractLine0((TranslationTextComponent) input.getSiblings().get(0));
            }
        } catch (RuntimeException e) {
            input.appendText(e.toString()).applyTextStyle(TextFormatting.DARK_RED);
        }
        return null;
    }

    private static final Pattern GET_OP_FROM_KEY = Pattern.compile(
            "attribute.modifier.(equals|plus|take).(\\d)"
    );
    private static final String SUBTRACT = "take";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static LineInfo extractLine0(TranslationTextComponent line) {
        //获取运算符id
        Matcher m = GET_OP_FROM_KEY.matcher(line.getKey());
        m.matches();
        int op = Integer.parseInt(m.group(2));
        boolean isNegative = SUBTRACT.equals(m.group(1));
        float val = Float.parseFloat((String) line.getFormatArgs()[0]);

        TranslationTextComponent attrKey = (TranslationTextComponent) line.getFormatArgs()[1];
        String attrName = attrKey.getKey().substring(ATTR_NAME_PREFIX.length());

        return new LineInfo(attrName, isNegative, op, val);
    }

    private static class LineInfo {
        LineInfo(String attrName, boolean isNegative, int op, float value) {
            this.op = op;
            this.isNegative = isNegative;
            this.attrName = attrName;
            this.value = value;
            this.stat = AttributeStatType.byAttributeName(this.attrName);
        }

        final String attrName;
        @Nullable
        final AttributeStatType stat;
        final boolean isNegative;
        final int op;
        float value;

        boolean countable() {
            return (this.stat == null) || this.stat.isCountable();
        }
    }


    private static List<ITextComponent> reOrder(List<LineInfo> inputs) {
        return inputs.stream().parallel()
                .sorted((l1, l2) -> {
                    //原版属性靠后
                    //stat == null说明这个LineInfo指示一个未封装的Attr
                    //故对应的排序结果靠后
                    if (l1.stat == null) {
                        return 1;
                    } else if (l2.stat == null) {
                        return -1;
                    }
                    return l1.stat.compareTo(l2.stat);
                })
                .map(l -> {
                            if (l.countable()) {
                                return new TranslationTextComponent(
                                NEW_ATTR_KEY + l.op,
                                //运算符名称
                                new TranslationTextComponent(NEW_ATTR_OP_KEY +
                                        (l.isNegative ? "subtract" : "add")),
                                //值
                                formatAttrValue(l),
                                //属性名称
                                (l.stat == null)
                                        ? new TranslationTextComponent((ATTR_NAME_PREFIX + l.attrName))
                                        .applyTextStyle(RpgMathsConfig.VANILLA_ATTRIBUTE_STYLE.get())
                                        : l.stat.getName()
                                );
                            } else {
                                assert l.stat != null;
                                return l.stat.getName();
                            }
                        })
                .collect(Collectors.toList());
    }

    private static String formatAttrValue(LineInfo info) {
        if (info.op == AttributeModifier.Operation.ADDITION.getId()) {
            return String.format("%-9.2f", info.value);
        } else {
            return String.format("%-8s", String.format("%.2f", info.value) + '%');
        }
    }
}