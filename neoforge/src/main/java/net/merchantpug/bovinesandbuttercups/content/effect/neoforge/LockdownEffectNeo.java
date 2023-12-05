package net.merchantpug.bovinesandbuttercups.content.effect.neoforge;

import net.merchantpug.bovinesandbuttercups.client.LockdownClientEffectExtensions;
import net.merchantpug.bovinesandbuttercups.content.effect.LockdownEffect;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

import java.util.function.Consumer;

public class LockdownEffectNeo extends LockdownEffect {
    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new LockdownClientEffectExtensions());
    }
}