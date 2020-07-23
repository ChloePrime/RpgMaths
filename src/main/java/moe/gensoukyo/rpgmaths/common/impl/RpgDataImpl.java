package moe.gensoukyo.rpgmaths.common.impl;

import moe.gensoukyo.rpgmaths.api.IRpgData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * TODO 移除abstract并填写实现
 */
public abstract class RpgDataImpl implements IRpgData
{
    @CapabilityInject(IRpgData.class)
    public static Capability<IRpgData> CAP_TOKEN;
}
