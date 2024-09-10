package house.greenhouse.bovinesandbuttercups;

import house.greenhouse.bovinesandbuttercups.access.BeeGoalAccess;
import house.greenhouse.bovinesandbuttercups.access.MooshroomInitializedTypeAccess;
import house.greenhouse.bovinesandbuttercups.api.attachment.CowTypeAttachment;
import house.greenhouse.bovinesandbuttercups.api.attachment.LockdownAttachment;
import house.greenhouse.bovinesandbuttercups.api.cowtype.CowModelLayer;
import house.greenhouse.bovinesandbuttercups.api.cowtype.modifier.TextureModifierFactory;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.LockEffectTrigger;
import house.greenhouse.bovinesandbuttercups.content.advancements.criterion.PreventEffectTrigger;
import house.greenhouse.bovinesandbuttercups.content.effect.LockdownEffect;
import house.greenhouse.bovinesandbuttercups.content.entity.Moobloom;
import house.greenhouse.bovinesandbuttercups.content.entity.goal.MoveToMoobloomGoal;
import house.greenhouse.bovinesandbuttercups.content.entity.goal.PollinateMoobloomGoal;
import house.greenhouse.bovinesandbuttercups.mixin.AnimalAccessor;
import house.greenhouse.bovinesandbuttercups.network.clientbound.SyncConditionedTextureModifier;
import house.greenhouse.bovinesandbuttercups.network.clientbound.SyncCowTypeClientboundPacket;
import house.greenhouse.bovinesandbuttercups.network.clientbound.SyncLockdownEffectsClientboundPacket;
import house.greenhouse.bovinesandbuttercups.platform.BovinesPlatformHelperNeoForge;
import house.greenhouse.bovinesandbuttercups.registry.BovinesAttachments;
import house.greenhouse.bovinesandbuttercups.registry.BovinesEffects;
import house.greenhouse.bovinesandbuttercups.registry.BovinesEntityTypes;
import house.greenhouse.bovinesandbuttercups.registry.BovinesItems;
import house.greenhouse.bovinesandbuttercups.util.CreativeTabHelper;
import house.greenhouse.bovinesandbuttercups.util.MooshroomChildTypeUtil;
import house.greenhouse.bovinesandbuttercups.util.MooshroomSpawnUtil;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mod(BovinesAndButtercups.MOD_ID)
public class BovinesAndButtercupsNeoForge {
    
    public BovinesAndButtercupsNeoForge(IEventBus eventBus) {
        BovinesAndButtercups.init(new BovinesPlatformHelperNeoForge());
    }

    @EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class GameEvents {
        @SubscribeEvent
        public static void onStartTracking(PlayerEvent.StartTracking event) {
            if (event.getTarget() instanceof LivingEntity living) {
                if (living.hasData(BovinesAttachments.LOCKDOWN))
                    LockdownAttachment.sync(living);
                if (living.hasData(BovinesAttachments.COW_TYPE)) {
                    CowTypeAttachment.sync(living);
                    CowTypeAttachment attachment = living.getData(BovinesAttachments.COW_TYPE);
                    for (CowModelLayer layer : attachment.cowType().value().configuration().layers()) {
                        for (TextureModifierFactory<?> modifier : layer.textureModifiers())
                            modifier.init(living);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            Level level = event.getLevel();
            Entity entity = event.getEntity();
            if (entity instanceof Bee bee) {
                PollinateMoobloomGoal pollinateGoal = new PollinateMoobloomGoal(bee);
                bee.getGoalSelector().addGoal(3, pollinateGoal);
                bee.getGoalSelector().addGoal(3, new MoveToMoobloomGoal(bee));
                ((BeeGoalAccess) bee).bovinesandbuttercups$setPollinateFlowerCowGoal(pollinateGoal);
            }

            if (event.getEntity().getType() == EntityType.MOOSHROOM && !level.isClientSide()) {
                Optional<CowTypeAttachment> attachment = event.getEntity().getExistingData(BovinesAttachments.COW_TYPE);
                if (attachment.isEmpty()) {
                    if (((MooshroomInitializedTypeAccess)entity).bovinesandbuttercups$initialType() != null) {
                        CowTypeAttachment.setCowType((MushroomCow) entity, MooshroomSpawnUtil.getMooshroomTypeFromMushroomType(level, ((MooshroomInitializedTypeAccess)entity).bovinesandbuttercups$initialType()));
                    } else if (MooshroomSpawnUtil.getTotalSpawnWeight(level, entity.blockPosition()) > 0) {
                        CowTypeAttachment.setCowType((MushroomCow) entity, MooshroomSpawnUtil.getMooshroomSpawnTypeDependingOnBiome(level, entity.blockPosition(), level.getRandom()));
                    } else {
                        CowTypeAttachment.setCowType((MushroomCow) entity, MooshroomSpawnUtil.getMostCommonMooshroomSpawnType(level, ((MushroomCow)entity).getVariant()));
                    }
                    CowTypeAttachment.sync((MushroomCow)entity);
                }
                ((MooshroomInitializedTypeAccess)entity).bovinesandbuttercups$clearInitialType();
            }
        }

        @SubscribeEvent
        public static void onBabyEntitySpawn(BabyEntitySpawnEvent event) {
            Mob parentA = event.getParentA();
            Mob parentB = event.getParentB();
            AgeableMob child = event.getChild();

            if (parentA instanceof MushroomCow parentACow && parentB instanceof MushroomCow parentBCow && child instanceof MushroomCow childCow) {
                CowTypeAttachment.setCowType(child, MooshroomChildTypeUtil.chooseMooshroomBabyType(parentACow, parentBCow, childCow, event.getCausedByPlayer()));
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

            if (event.getEntity() instanceof Bee bee && !event.getEntity().level().isClientSide() && ((BeeGoalAccess)event.getEntity()).bovinesandbuttercups$getPollinateFlowerCowGoal() != null) {
                ((BeeGoalAccess)bee).bovinesandbuttercups$getPollinateFlowerCowGoal().tickCooldown();
            }
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

        @SubscribeEvent
        public static void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
            Entity entity = event.getEntity();
            if (entity instanceof LivingEntity living && !(entity instanceof Moobloom) && entity.hasData(BovinesAttachments.COW_TYPE)) {
                CowTypeAttachment attachment = entity.getData(BovinesAttachments.COW_TYPE);
                if (attachment.previousCowType().isEmpty()) {
                    if (attachment.cowType().value().configuration().settings().thunderConverts().isEmpty())
                        return;

                    var compatibleList = attachment.cowType().value().configuration().settings().filterThunderConverts(attachment.cowType().value().type());
                    int totalWeight = 0;

                    if (compatibleList.size() == 1) {
                        CowTypeAttachment.setCowType(living, (Holder) compatibleList.getFirst().data(), (Holder) attachment.cowType());
                        CowTypeAttachment.sync(living);
                        BovinesAndButtercups.convertedByBovines = true;
                    } else if (!compatibleList.isEmpty()) {
                        for (var cct : compatibleList) {
                            totalWeight -= cct.weight().asInt();
                            if (totalWeight <= 0) {
                                CowTypeAttachment.setCowType(living, (Holder) cct.data(), (Holder) attachment.cowType());
                                CowTypeAttachment.sync(living);
                                BovinesAndButtercups.convertedByBovines = true;
                                break;
                            }
                        }
                    }
                } else {
                    CowTypeAttachment.setCowType(living, (Holder) attachment.previousCowType().get());
                    CowTypeAttachment.sync(living);
                    BovinesAndButtercups.convertedByBovines = true;
                }
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
        }

        @SubscribeEvent
        public static void modifySpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(EntityType.MOOSHROOM, (entityType, levelAccessor, mobSpawnType, blockPos, randomSource) -> MooshroomSpawnUtil.getTotalSpawnWeight(levelAccessor, blockPos) > 0, RegisterSpawnPlacementsEvent.Operation.AND);
            event.register(EntityType.MOOSHROOM, (entityType, levelAccessor, mobSpawnType, blockPos, randomSource) -> (!levelAccessor.getBiome(blockPos).is(Biomes.MUSHROOM_FIELDS) && levelAccessor.getBlockState(blockPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON)) && AnimalAccessor.bovinesandbuttercups$invokeIsBrightEnoughToSpawn(levelAccessor, blockPos) && MooshroomSpawnUtil.getTotalSpawnWeight(levelAccessor, blockPos) > 0, RegisterSpawnPlacementsEvent.Operation.OR);
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
                if (startItem != null) {
                    List<Item> items = List.of(
                            BovinesItems.FREESIA,
                            BovinesItems.BIRD_OF_PARADISE,
                            BovinesItems.BUTTERCUP,
                            BovinesItems.LIMELIGHT,
                            BovinesItems.LINGHOLM,
                            BovinesItems.CHARGELILY,
                            BovinesItems.TROPICAL_BLUE,
                            BovinesItems.HYACINTH,
                            BovinesItems.PINK_DAISY,
                            BovinesItems.SNOWDROP
                    );

                    for (Item item : items) {
                        ItemStack stack = new ItemStack(item);
                        event.insertAfter(startItem, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        startItem = stack;
                    }
                }
                CreativeTabHelper.getCustomFlowersForCreativeTab(event.getParameters().holders()).reversed().forEach(stack -> event.insertAfter(new ItemStack(BovinesItems.SNOWDROP), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                CreativeTabHelper.getCustomMushroomsForCreativeTab(event.getParameters().holders()).reversed().forEach(stack -> event.insertAfter(new ItemStack(Items.RED_MUSHROOM), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                CreativeTabHelper.getCustomMushroomBlocksForCreativeTab(event.getParameters().holders()).reversed().forEach(stack -> event.insertAfter(new ItemStack(Items.RED_MUSHROOM_BLOCK), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
            } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                CreativeTabHelper.getNectarBowlsForCreativeTab(event.getParameters().holders()).reversed().forEach(stack -> event.insertAfter(new ItemStack(Items.MILK_BUCKET), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
                event.insertAfter(new ItemStack(Items.HONEY_BOTTLE), new ItemStack(BovinesItems.RICH_HONEY_BOTTLE), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                event.accept(BovinesItems.MOOBLOOM_SPAWN_EGG);
            } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                CreativeTabHelper.getFlowerCrownsForCreativeTab(event.getParameters().holders()).reversed().forEach(stack -> event.insertAfter(new ItemStack(Items.SADDLE), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
            }
        }
    }

}