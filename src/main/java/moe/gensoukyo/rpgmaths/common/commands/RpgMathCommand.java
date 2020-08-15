package moe.gensoukyo.rpgmaths.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.common.util.ForgeRegistryArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

/**
 * mod的主要命令
 * 可以设置/获取属性值
 * @author Chloe_koopa
 */
public class RpgMathCommand
{
    public static final String PERM_NODE = RpgMathsMod.ID + ".command";
    final static String ARGUMENT_STAT_NAME = "stat";
    final static String ARGUMENT_VALUE = "value";

    static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        //rpgmaths
        dispatcher.register(literal(RpgMathsMod.ID)
                //权限
                .requires(src -> hasPerm(src, PERM_NODE))
                //rpgmaths get
                .then(literal("get")
                        .then(argument(ARGUMENT_STAT_NAME,
                                ForgeRegistryArgumentType.of(RpgMathsMod.getApi().
                                        getRegisteries().getStats()
                                ))

                                .executes(GET)
                        )
                )
                //rpgmaths set
                .then(literal("set")
                        .then(argument(ARGUMENT_STAT_NAME,
                                ForgeRegistryArgumentType.of(
                                        RpgMathsMod.getApi().getRegisteries().getStats()))
                                .then(argument(ARGUMENT_VALUE, FloatArgumentType.floatArg())

                                        .executes(SET)
                                )
                        )
                )
        );
    }

    /**
     * 获取属性的命令
     */
    private static final Command<CommandSource> GET = context ->
    {
        IStatHandler handler = getStatHandler(context);
        IStatType statType = context.getArgument(ARGUMENT_STAT_NAME, IStatType.class);

        final double statValue = handler.getFinalValue(statType);

        context.getSource().sendFeedback(new StringTextComponent(String.valueOf(statValue)), true);
        return (int) statValue;
    };

    /**
     * 设置s属性的命令
     */
    private static final Command<CommandSource> SET = context ->
    {
        IStatHandler handler = getStatHandler(context);
        IStatType statType = context.getArgument(ARGUMENT_STAT_NAME, IStatType.class);
        final float value = context.getArgument(ARGUMENT_VALUE, Float.class);

        handler.setBaseValue(statType, value);
        return (int) value;
    };

    @Nonnull
    private static IStatHandler getStatHandler(CommandContext<CommandSource> context)
            throws CommandSyntaxException
    {
        Entity entity = context.getSource().getEntity();
        if (entity == null)
        {
            throw EntityArgument.ENTITY_NOT_FOUND.create();
        }
        AtomicReference<IStatHandler> result = new AtomicReference<>(null);
        RpgMathsMod.getApi().getRpgData(entity).ifPresent(
                rpgData -> result.set(rpgData.getStats())
        );
        Objects.requireNonNull(result.get());
        return result.get();
    }

    @SuppressWarnings("SameParameterValue")
    static boolean hasPerm(CommandSource src, String node)
    {
        Entity entity = src.getEntity();
        if (! (entity instanceof PlayerEntity)) { return true; }

        PlayerEntity player = (PlayerEntity) entity;
        return true;
        //return PermissionAPI.hasPermission(player, node);
    }
}
