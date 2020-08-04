package moe.gensoukyo.rpgmaths.api.damage.type;

public interface IMutableResistanceMap extends IResistanceMap {
    IMutableResistanceMap setMultiplier(IDamageType type, double value);
}
