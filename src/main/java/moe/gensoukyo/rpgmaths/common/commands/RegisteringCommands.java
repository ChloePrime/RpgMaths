package moe.gensoukyo.rpgmaths.common.commands;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

/**
 * 注册命令到服务器
 * @author Chloe_koopa
 */
@Mod.EventBusSubscriber
public class RegisteringCommands
{
    @SubscribeEvent
    public static void onServerStart(FMLServerStartingEvent event)
    {
        RpgMathCommand.register(event.getCommandDispatcher());
    }
}
