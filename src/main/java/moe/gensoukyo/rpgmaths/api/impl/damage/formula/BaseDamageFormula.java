package moe.gensoukyo.rpgmaths.api.impl.damage.formula;

import moe.gensoukyo.rpgmaths.RpgMathsMod;
import moe.gensoukyo.rpgmaths.api.Constants;
import moe.gensoukyo.rpgmaths.api.IRpgMathsApi;
import moe.gensoukyo.rpgmaths.api.damage.IDamageFormula;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.data.IRpgData;
import moe.gensoukyo.rpgmaths.api.events.TypedDamageEvent;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Random;

import static moe.gensoukyo.rpgmaths.api.damage.type.IDamageType.TypedStatCategory.*;

/**
 * @author Chloe_koopa
 */
@ObjectHolder(RpgMathsMod.ID)
public abstract class BaseDamageFormula implements IDamageFormula {
    private static final Random RANDOM = new Random();
    protected static final IRpgMathsApi API = RpgMathsMod.getApi();

    protected final double getFinalAttack(ICapabilityProvider attacker, IDamageType damageType) {
        return getFinalValue(attacker, damageType.getStat(ATTACK));
    }

    protected final double getFinalDefense(ICapabilityProvider victim, IDamageType damageType) {
        return getFinalValue(victim, damageType.getStat(DEFENSE));
    }

    private double getFinalValue(ICapabilityProvider attacker, IStatType type) {
        return API.getRpgData(attacker)
                .map(iRpgData -> iRpgData.getStats().getFinalValue(type))
                .orElse(0d);
    }

    @Override
    public final double calculateDamage(ICapabilityProvider attacker, LivingEntity victim) {
        return IDamageFormula.super.calculateDamage(attacker, victim);
    }

    @Override
    public final double calculateDamage(ICapabilityProvider attacker,
                                        ItemStack weapon,
                                        LivingEntity victim) {
        double result = 0d;
        IDamageType[] types = API.getRpgData(weapon).map(IRpgData::getDefaultDamageTypes)
                .orElse(Constants.DEFAULT_DAMAGE_TYPES);

        for (final IDamageType type : types) {
            //基础公式计算
            double damage = calculateDamage0(
                    getFinalAttack(attacker, type),
                    getFinalDefense(victim, type));
            double trigRate = (getFinalValue(attacker, type.getStat(TRIGGER))
                    * Constants.RANDOM_STAT_SCALE)
                    * (1 - getFinalValue(victim, type.getStat(ANTI_TRIGGER))
                    * Constants.RANDOM_STAT_SCALE);
            //属性克制
            double dmgRate = RpgMathsMod.getApi().getRpgData(victim)
                    .map(IRpgData::getResistance)
                    .map(resistanceMap -> resistanceMap.getMultiplier(types))
                    .orElse(1.0);
            //事件
            TypedDamageEvent event =
                    new TypedDamageEvent(attacker, victim, damage, dmgRate, trigRate, type);
            boolean canceled = MinecraftForge.EVENT_BUS.post(event);
            if (canceled) {
                continue;
            }
            damage = event.getRawDamage();
            dmgRate = event.getDamageMultiplier();
            trigRate = event.getTriggerRate();
            //触发效果
            if (damage > 0) {
                type.onEffectTriggered(attacker, damage, false);
                type.onEffectTriggered(victim, damage, true);
                if (trigRate > RANDOM.nextFloat()) {
                    type.onEffectRandomlyTriggered(attacker, damage, false);
                    type.onEffectRandomlyTriggered(victim, damage, true);
                }
            }
            //累加伤害
            damage *= dmgRate;
            result += damage;
        }

        return result;
    }

    /**
     * 决定伤害公式的次数
     * 这通常决定了公式的类型
     * 对于每个属性都会计算一次
     *
     * @param atk 结算后的攻击力
     * @param def 结算后的防御力
     * @return 被最终处理前的伤害，不包括属性克制和真实伤害等对伤害值的后处理。
     */
    protected abstract double calculateDamage0(double atk, double def);
}
