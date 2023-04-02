package net.diamonddev.democracy.mixin;

import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(StatType.class)
public interface StatTypeAccessor<T> {
    @Accessor
    Map<T, Stat<T>> getMap();
}
