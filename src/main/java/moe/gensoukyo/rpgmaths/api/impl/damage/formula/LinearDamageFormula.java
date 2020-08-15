package moe.gensoukyo.rpgmaths.api.impl.damage.formula;

/**
 * @author Chloe_koopa
 */
public class LinearDamageFormula extends BaseDamageFormula {
    private final double atkFactor;
    private final double defFactor;

    public LinearDamageFormula(double atkFactor, double defFactor) {
        this.atkFactor = atkFactor;
        this.defFactor = defFactor;
    }

    @Override
    protected double calculateDamage0(double atk, double def) {
        return atk * atkFactor - def * defFactor;
    }
}
