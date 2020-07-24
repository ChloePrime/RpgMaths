package moe.gensoukyo.rpgmaths.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.stats.IStatHandler;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import moe.gensoukyo.rpgmaths.common.util.ForgeRegistryArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.command.Commands.*;

public class RpgMathCommand
{
    public static final String PERM_NODE = RpgMathsMod.ID + ".command";
    final static String ARGUMENT_STAT_NAME = "stat";
    final static String ARGUMENT_VALUE = "value";

    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        //rpgmaths
        dispatcher.register(literal("rpgmaths")
                //权限
                .requires(src -> hasPerm(src, PERM_NODE))
                //rpgmaths get
                .then(literal("get")
                        .then(argument(ARGUMENT_STAT_NAME,
                                ForgeRegistryArgumentType.of(RpgMathsMod.getApi().getStatRegistry())
                                )
                                .then(argument(ARGUMENT_VALUE, FloatArgumentType.floatArg()))
                                .executes(GET)
                        )
                )
                //rpgmaths set
                .then(literal("set")
                        .then(argument(ARGUMENT_STAT_NAME,
                                ForgeRegistryArgumentType.of(RpgMathsMod.getApi().getStatRegistry())
                                )
                                .then(argument(ARGUMENT_VALUE, FloatArgumentType.floatArg()))
                                .executes(SET)
                        )
                )
        );
    }

    private static final Command<CommandSource> GET = context ->
    {
        IStatHandler handler = getStatHandler(context);
        IStatType statType = context.getArgument(ARGUMENT_STAT_NAME, IStatType.class);

        String result;
        context.getSource().sendFeedback(new StringTextComponent(
                (handler == null) ? "" + null : handler.getStat(statType)
        ));
    };

    private static final Command<CommandSource> SET = context ->
    {
        return entity.getCapability()
    }

    private static IStatHandler getStatHandler(CommandContext<CommandSource> context)
    {
        Entity entity = context.getSource().getEntity();
        if (entity == null) { return null; }
        AtomicReference<IStatHandler> result = new AtomicReference<>(null);
        RpgMathsMod.getApi().getRpgData(entity).ifPresent(
                rpgData -> result.set(rpgData.getStats().orElse(null))
        );
        return result.get();
    }

    static boolean hasPerm(CommandSource src, String node)
    {
        Entity entity = src.getEntity();
        if (! (entity instanceof PlayerEntity)) { return true; }

        PlayerEntity player = (PlayerEntity) entity;
        return PermissionAPI.hasPermission(player, node);
    }
}
