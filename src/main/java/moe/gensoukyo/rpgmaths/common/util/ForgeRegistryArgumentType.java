package moe.gensoukyo.rpgmaths.common.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 把forge注册表转为命令参数列表
 * @author Chloe_koopa
 */
public class ForgeRegistryArgumentType<T extends IForgeRegistryEntry<T>>
        implements ArgumentType<T>
{
    private final IForgeRegistry<T> registry;
    private ForgeRegistryArgumentType(IForgeRegistry<T> registry)
    {
        this.registry = registry;
    }

    public static <T extends IForgeRegistryEntry<T>>
    ForgeRegistryArgumentType<T> of(IForgeRegistry<T> registry)
    {
        return new ForgeRegistryArgumentType<>(registry);
    }

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException
    {
        return registry.getValue(new ResourceLocation(reader.readString()));
    }


    @Override
    public <S> CompletableFuture<Suggestions>
    listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
    {
        getCandidates().forEach(builder::suggest);
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples()
    {
        return getCandidates();
    }

    /**
     * 缓存结果
     */
    private List<String> candidates;
    /**
     * 初始化并返回命令成员列表
     */
    private Collection<String> getCandidates()
    {
        if (candidates == null)
        {
            candidates = registry.getKeys().stream()
                    .map(ResourceLocation::toString)
                    .collect(Collectors.toList());
        }
        return candidates;
    }
}
