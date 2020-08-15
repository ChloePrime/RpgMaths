package moe.gensoukyo.rpgmaths.api.damage.type;

import moe.gensoukyo.rpgmaths.api.impl.damage.type.DamageTypeImpl;
import moe.gensoukyo.rpgmaths.api.stats.ModStats;

/**
 * @author Chloe_koopa
 */
public class ModDamageTypes {
    public static final IDamageType
            PHYSICS = new DamageTypeImpl(ModStats.ATK, ModStats.DEF, null, null),
            MAGIC = new DamageTypeImpl(ModStats.ATS, ModStats.ADF, null, null);

    public static final IDamageType[] MOD_TYPES = {PHYSICS, MAGIC};

    private ModDamageTypes() {}
}
