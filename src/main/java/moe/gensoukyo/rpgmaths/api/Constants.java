package moe.gensoukyo.rpgmaths.api;

import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.ModDamageTypes;

/**
 * 本mod自带的RPG数值的名称
 * @author Chloe_koopa
 */
public class Constants {
    public static final IDamageType[] DEFAULT_DAMAGE_TYPES = {ModDamageTypes.PHYSICS};

    private Constants() {}

    public static final String STAT_ATK = "attack";
    public static final String STAT_DEF = "defense";
    public static final String STAT_ATS = "art_attack";
    public static final String STAT_ADF = "art_defense";
    public static final String STAT_CRITICAL = "crit";
    public static final String STAT_TRIGGER_ALL = "trigger";

    /**
     * 属性点 -> 概率的倍率
     * 概率P = 属性点 * 该值
     */
    public static final double RANDOM_STAT_SCALE = 1d/100d;

    public static final String TYPE_PHYSICS = "physics";
    public static final String TYPE_ARTS = "magic";
}
