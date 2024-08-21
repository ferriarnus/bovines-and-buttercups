package net.merchantpug.bovinesandbuttercups.mixin.fabric;

import com.mojang.datafixers.DataFixer;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercupsFabric;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;

@Mixin(value = MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow public abstract RegistryAccess.Frozen registryAccess();

    // Run before FAPI MinecraftServerMixin.
    @Inject(method = "<init>", at = @At("RETURN"), order = 500)
    private void bovinesandbuttercups$captureRegistryAccess(Thread thread, LevelStorageSource.LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem worldStem, Proxy proxy, DataFixer dataFixer, Services services, ChunkProgressListenerFactory chunkProgressListenerFactory, CallbackInfo ci) {
        BovinesAndButtercupsFabric.setBiomeRegistries(registryAccess());
    }

    @Inject(method = "<init>", at = @At("RETURN"), order = 1500)
    private void bovinesandbuttercups$resetRegistryAccess(Thread thread, LevelStorageSource.LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem worldStem, Proxy proxy, DataFixer dataFixer, Services services, ChunkProgressListenerFactory chunkProgressListenerFactory, CallbackInfo ci) {
        BovinesAndButtercupsFabric.setBiomeRegistries(registryAccess());
    }
}
