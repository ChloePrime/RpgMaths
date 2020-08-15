package moe.gensoukyo.rpgmaths.api.impl.damage.type;

import com.google.common.base.Preconditions;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType;
import moe.gensoukyo.rpgmaths.api.damage.type.IDamageType.TypedStatCategory;
import moe.gensoukyo.rpgmaths.api.impl.stats.AttributeStatType;
import moe.gensoukyo.rpgmaths.api.stats.IStatType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

public class DamageTypeStatGenerator {
    /**
     * 伤害类型
     * minecraft:genso
     * ->
     * RPG属性
     * minecraft:typed_genso_attack
     * minecraft:typed_genso_trigger
     * ...
     */
    private static final String FORMAT = "typed_%s_%s";

    public static void genAndRegister(@Nonnull IForgeRegistry<IStatType> registry,
                                      @Nonnull IDamageType type) {
        Preconditions.checkNotNull(registry);
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(type.getRegistryName());

        ResourceLocation typeName = type.getRegistryName();

        for (TypedStatCategory category : TypedStatCategory.values()) {
            if (type.getStat(category) != null) {
                continue;
            }
            ResourceLocation generatedRegName = new ResourceLocation(typeName.getNamespace(),
                    String.format(FORMAT, type.getRegistryName().getPath(),
                            category.getName().toLowerCase())
            );
            IStatType result = new GeneratedStatType(type, category);
            result.setRegistryName(generatedRegName);

            registry.register(result);
            type.registerStat(category, result);
        }
    }

    public static void genAndRegister(@Nonnull IForgeRegistry<IStatType> registry,
                                      @Nonnull IDamageType[] types) {
        Preconditions.checkNotNull(registry);
        Preconditions.checkNotNull(types);

        for (IDamageType type : types) {
            genAndRegister(registry, type);
        }
    }

    private static class GeneratedStatType extends AttributeStatType {

        private final IDamageType owner;
        private final TypedStatCategory category;

        public GeneratedStatType(IDamageType owner, TypedStatCategory category) {
            super();
            this.owner = owner;
            this.category = category;
        }
        @Override
        public String toString() {
            return "Stat ["
                    + category.getName() +
                    "] for damage type ["
                    + owner.getRegistryName()
                    + "]";
        }
    }
}
