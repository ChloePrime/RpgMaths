package moe.gensoukyo.rpgmaths.api.damage.type;

/**
 * @author Chloe_koopa
 */
public interface IMutableResistanceMap extends IResistanceMap {
    IMutableResistanceMap setMultiplier(IDamageType type, double value);
}
