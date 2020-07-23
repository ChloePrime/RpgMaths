package moe.gensoukyo.rpgmaths.api.damage.type;

public interface IMutableResistanceMap extends IResistanceMap {
    void setMultiplier(IDamageType type, double value);
}
