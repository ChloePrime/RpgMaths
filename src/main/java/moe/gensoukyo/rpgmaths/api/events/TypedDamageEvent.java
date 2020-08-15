package moe.gensoukyo.rpgmaths.api.events;

import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class TypedDamageEvent extends Event {
    private final ICapabilityProvider attacker;
    private final ICapabilityProvider victim;
    private double rawDamage;
    private double damageMultiplier;
    private double triggerRate;
    private final IDamageType type;


    public TypedDamageEvent(ICapabilityProvider attacker,
                            ICapabilityProvider victim,
                            double rawDamage,
                            double damageMultiplier,
                            double triggerRate,
                            IDamageType type) {
        this.attacker = attacker;
        this.victim = victim;
        this.rawDamage = rawDamage;
        this.damageMultiplier = damageMultiplier;
        this.triggerRate = triggerRate;
        this.type = type;
    }

    public ICapabilityProvider getAttacker() {
        return attacker;
    }

    public ICapabilityProvider getVictim() {
        return victim;
    }

    public double getRawDamage() {
        return rawDamage;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public double getTriggerRate() {
        return triggerRate;
    }

    public IDamageType getType() {
        return type;
    }

    public void setRawDamage(double rawDamage) {
        this.rawDamage = rawDamage;
    }

    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public void setTriggerRate(double triggerRate) {
        this.triggerRate = triggerRate;
    }
}
