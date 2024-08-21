package net.merchantpug.bovinesandbuttercups;

import net.merchantpug.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import net.merchantpug.bovinesandbuttercups.api.attachment.LockdownAttachment;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import net.merchantpug.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import net.merchantpug.bovinesandbuttercups.content.effect.LockdownEffect;
import net.merchantpug.bovinesandbuttercups.content.entity.Moobloom;
import net.merchantpug.bovinesandbuttercups.network.clientbound.SyncConditionedTextureModifier;
import net.merchantpug.bovinesandbuttercups.network.clientbound.SyncCowTypeClientboundPacket;
import net.merchantpug.bovinesandbuttercups.network.clientbound.SyncLockdownEffectsClientboundPacket;
import net.merchantpug.bovinesandbuttercups.platform.NeoBovinesPlatformHelper;
import net.merchantpug.bovinesandbuttercups.registry.BovinesAttachments;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEffects;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.util.CreativeTabHelper;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.HashMap;
import java.util.Optional;

@Mod(BovinesAndButtercups.MOD_ID)
public class BovinesAndButtercupsNeoForge {
    
    public BovinesAndButtercupsNeoForge(IEventBus eventBus) {
        BovinesAndButtercups.init(new NeoBovinesPlatformHelper());
    }

    @EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class GameEvents {
        @SubscribeEvent
        public static void onStartTracking(PlayerEvent.StartTracking event) {
            if (event.getTarget() instanceof LivingEntity living) {
                if (living.hasData(BovinesAttachments.LOCKDOWN))
                    LockdownAttachment.sync(living);
                if (living.hasData(BovinesAttachments.COW_TYPE))
                    CowTypeAttachment.sync(living);
            }
        }

        @SubscribeEvent
        public static void onLivingTick(EntityTickEvent.Post event) {
            if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof LivingEntity living && living.hasEffect(BovinesEffects.LOCKDOWN)) {
                HashMap<Holder<MobEffect>, Integer> lockdownEffectsToUpdate = new HashMap<>();
                living.getExistingData(BovinesAttachments.LOCKDOWN).ifPresent(attachment -> attachment.effects().forEach(((effect, integer) -> {
                    if (integer > 0) {
                        lockdownEffectsToUpdate.put(effect, --integer);
                    } else if (integer == -1) {
                        lockdownEffectsToUpdate.put(effect, -1);
                    }
                })));
                living.getData(BovinesAttachments.LOCKDOWN).setLockdownMobEffects(lockdownEffectsToUpdate);
                LockdownAttachment.sync(living);
            }

            if (event.getEntity().hasData(BovinesAttachments.COW_TYPE) && event.getEntity().getData(BovinesAttachments.COW_TYPE).cowType().isBound())
                event.getEntity().getData(BovinesAttachments.COW_TYPE).cowType().value().configuration().tick(event.getEntity());
        }
        @SubscribeEvent
        public static void onMobEffectAdded(MobEffectEvent.Added event) {
            LivingEntity entity = event.getEntity();

            LockdownAttachment attachment = entity.getData(BovinesAttachments.LOCKDOWN);
            MobEffectInstance effect = event.getEffectInstance();
            if (!entity.level().isClientSide && effect.getEffect() instanceof LockdownEffect && (attachment.effects().isEmpty() || attachment.effects().values().stream().allMatch(value -> value < effect.getDuration()))) {
                Optional<Holder.Reference<MobEffect>> randomEffect = Util.getRandomSafe(BuiltInRegistries.MOB_EFFECT.holders().filter(holder -> holder.isBound() && holder.value().isEnabled(entity.level().enabledFeatures())).toList(), entity.level().getRandom());
                randomEffect.ifPresent(entry -> {
                    attachment.addLockdownMobEffect(entry, effect.getDuration());
                    LockdownAttachment.sync(entity);
                });
            }
            if (!entity.level().isClientSide && entity instanceof ServerPlayer serverPlayer && effect.getEffect() instanceof LockdownEffect && !attachment.effects().isEmpty()) {
                attachment.effects().forEach((effect1, duration) -> {
                    if (!entity.hasEffect(effect1)) return;
                    LockEffectTrigger.INSTANCE.trigger(serverPlayer, effect1);
                });
            }
        }

        @SubscribeEvent
        public static void onMobEffectRemoved(MobEffectEvent.Remove event) {
            if (event.getEffectInstance() == null || !(event.getEffectInstance().getEffect() instanceof LockdownEffect)) return;
            Optional<LockdownAttachment> attachment = event.getEntity().getExistingData(BovinesAttachments.LOCKDOWN);
            if (attachment.isPresent()) {
                attachment.get().effects().clear();
                LockdownAttachment.sync(event.getEntity());
            }
        }

        @SubscribeEvent
        public static void onMobEffectExpired(MobEffectEvent.Expired event) {
            if (event.getEffectInstance() == null || !(event.getEffectInstance().getEffect() instanceof LockdownEffect)) return;
            Optional<LockdownAttachment> attachment = event.getEntity().getExistingData(BovinesAttachments.LOCKDOWN);
            if (attachment.isPresent()) {
                attachment.get().effects().clear();
                LockdownAttachment.sync(event.getEntity());
            }
        }

        @SubscribeEvent
        public static void changeMobEffectApplicability(MobEffectEvent.Applicable event) {
            Entity entity = event.getEntity();
            Optional<LockdownAttachment> attachment = event.getEntity().getExistingData(BovinesAttachments.LOCKDOWN);
            if (attachment.isPresent() && attachment.get().effects().containsKey(event.getEffectInstance().getEffect())) {
                if (!entity.level().isClientSide && entity instanceof ServerPlayer serverPlayer) {
                    PreventEffectTrigger.INSTANCE.trigger(serverPlayer, event.getEffectInstance().getEffect());
                }
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

    @EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void createEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(BovinesEntityTypes.MOOBLOOM, Moobloom.createAttributes().build());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(BovinesEntityTypes.MOOBLOOM, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Moobloom::canMoobloomSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            // TODO: Mooshroom data.
            // event.register(EntityType.MOOSHROOM, (entityType, levelAccessor, mobSpawnType, blockPos, randomSource) -> (levelAccessor.getBiome(blockPos).is(Biomes.MUSHROOM_FIELDS) && levelAccessor.getBlockState(blockPos.below()).is(BlockTags.MOOSHROOMS_SPAWNABLE_ON) || !levelAccessor.getBiome(blockPos).is(Biomes.MUSHROOM_FIELDS) && levelAccessor.getBlockState(blockPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON)) && Animal.isBrightEnoughToSpawn(levelAccessor, blockPos) && (MushroomCowSpawnUtil.getTotalSpawnWeight(levelAccessor, blockPos) > 0 || (MushroomCowSpawnUtil.getTotalSpawnWeight(levelAccessor, blockPos) > 0 || MushroomCowSpawnUtil.getTotalSpawnWeight(levelAccessor, blockPos) == 0 && levelAccessor.getBiome(blockPos).is(Biomes.MUSHROOM_FIELDS) && BovineRegistryUtil.configuredCowTypeStream().anyMatch(configuredCowType -> configuredCowType.configuration() instanceof MushroomCowConfiguration mushroomCowConfiguration && mushroomCowConfiguration.usesVanillaSpawningHack()) && MushroomCow.checkMushroomSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource))), SpawnPlacementRegisterEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void registerPackets(RegisterPayloadHandlersEvent event) {
            event.registrar("2.0.0")
                    .playToClient(SyncConditionedTextureModifier.TYPE, SyncConditionedTextureModifier.STREAM_CODEC, (payload, context) -> payload.handle())
                    .playToClient(SyncCowTypeClientboundPacket.TYPE, SyncCowTypeClientboundPacket.STREAM_CODEC, (payload, context) -> payload.handle())
                    .playToClient(SyncLockdownEffectsClientboundPacket.TYPE, SyncLockdownEffectsClientboundPacket.STREAM_CODEC, (payload, context) -> payload.handle());
        }

        @SubscribeEvent
        public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
                ItemStack startItem = null;
                for (ItemStack entry : event.getParentEntries()) {
                    if (entry.is(Items.SPORE_BLOSSOM)) {
                        startItem = entry;
                        break;
                    }
                }


                ItemStack freesia = new ItemStack(BovinesItems.FREESIA);
                ItemStack birdOfParadise = new ItemStack(BovinesItems.BIRD_OF_PARADISE);
                ItemStack buttercup = new ItemStack(BovinesItems.BUTTERCUP);
                ItemStack limelight = new ItemStack(BovinesItems.LIMELIGHT);
                ItemStack chargelily = new ItemStack(BovinesItems.CHARGELILY);
                ItemStack tropicalBlue = new ItemStack(BovinesItems.TROPICAL_BLUE);
                ItemStack hyacinth = new ItemStack(BovinesItems.HYACINTH);
                ItemStack pinkDaisy = new ItemStack(BovinesItems.PINK_DAISY);
                ItemStack snowdrop = new ItemStack(BovinesItems.SNOWDROP);

                if (startItem == null)
                    event.accept(freesia, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                else
                    event.insertAfter(startItem, freesia, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(freesia, birdOfParadise, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(birdOfParadise, buttercup, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(buttercup, limelight, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(limelight, chargelily, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(chargelily, tropicalBlue, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(tropicalBlue, hyacinth, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(hyacinth, pinkDaisy, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.insertAfter(pinkDaisy, snowdrop, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

                CreativeTabHelper.getCustomFlowersForCreativeTab(event.getParameters().holders()).forEach(stack -> event.insertAfter(new ItemStack(BovinesItems.SNOWDROP), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                CreativeTabHelper.getCustomMushroomsForCreativeTab(event.getParameters().holders()).forEach(stack -> event.insertAfter(new ItemStack(Items.RED_MUSHROOM), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                CreativeTabHelper.getCustomMushroomBlocksForCreativeTab(event.getParameters().holders()).forEach(stack -> event.insertAfter(new ItemStack(Items.RED_MUSHROOM_BLOCK), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
            } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                CreativeTabHelper.getNectarBowlsForCreativeTab(event.getParameters().holders()).forEach(stack -> event.insertAfter(new ItemStack(Items.MILK_BUCKET), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
            } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                event.accept(BovinesItems.MOOBLOOM_SPAWN_EGG);
            }
        }
    }

}