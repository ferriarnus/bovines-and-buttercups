package net.merchantpug.bovinesandbuttercups.content.entity;

import com.williambl.dfunc.api.DFunctions;
import com.williambl.vampilang.stdlib.StandardVTypes;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.ConfiguredCowType;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomFlowerBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.merchantpug.bovinesandbuttercups.mixin.EntityAccessor;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCriteriaTriggers;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.registry.BovinesSoundEvents;
import net.merchantpug.bovinesandbuttercups.util.HolderUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Moobloom extends Cow {
    private static final EntityDataAccessor<String> TYPE_ID = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> PREVIOUS_TYPE_ID = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> POLLINATED_RESET_TICKS = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STANDING_STILL_FOR_BEE_TICKS = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ALLOW_SHEARING = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.BOOLEAN);
    private ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> type;
    @Nullable
    public Bee bee;
    private boolean hasRefreshedDimensionsForLaying;
    @Nullable private UUID lastLightningBoltUUID;

    public Moobloom(EntityType<? extends Moobloom> entityType, Level level) {
        super(entityType, level);
        this.bee = null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE_ID, "bovinesandbuttercups:missing");
        this.entityData.define(PREVIOUS_TYPE_ID, "");
        this.entityData.define(POLLINATED_RESET_TICKS, 0);
        this.entityData.define(STANDING_STILL_FOR_BEE_TICKS, 0);
        this.entityData.define(ALLOW_SHEARING, true);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(2, new Moobloom.LookAtBeeGoal());
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Moobloom.class, 2.0F, 1.0F, 1.0F, moobloomEntity -> moobloomEntity instanceof Moobloom && ((Moobloom) moobloomEntity).getStandingStillForBeeTicks() > 0));
        super.registerGoals();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Type", this.getTypeId());
        if (!this.getPreviousTypeId().equals("")) {
            compound.putString("PreviousType", this.getPreviousTypeId());
        }
        compound.putInt("PollinatedResetTicks", this.getPollinatedResetTicks());
        compound.putBoolean("AllowShearing", this.shouldAllowShearing());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Type")) {
            this.setFlowerType(compound.getString("Type"));
        }
        if (compound.contains("PreviousType")) {
            this.setPreviousTypeId(compound.getString("PreviousType"));
        }
        if (compound.contains("PollinatedResetTicks", 99)) {
            this.setPollinatedResetTicks(compound.getInt("PollinatedResetTicks"));
        }
        if (compound.contains("AllowShearing", Tag.TAG_BYTE)) {
            this.setAllowShearing(compound.getBoolean("AllowShearing"));
        }
    }

    public void setBee(@Nullable Bee value) {
        this.bee = value;
    }

    public static boolean canMoobloomSpawn(EntityType<? extends Moobloom> type, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && Animal.isBrightEnoughToSpawn(level, pos) && getTotalSpawnWeight(level, pos) > 0;
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt bolt) {
        UUID uuid = bolt.getUUID();
        if (!uuid.equals(this.lastLightningBoltUUID)) {
            if (this.getPreviousTypeId().equals("")) {
                if (this.getMoobloomType().configuration().settings().thunderConverts().isEmpty()) {
                    super.thunderHit(level, bolt);
                    return;
                }
                this.setPreviousTypeId(this.getTypeId());

                List<MoobloomConfiguration.WeightedConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>> compatibleList = new ArrayList<>();
                int totalWeight = 0;

                for (CowTypeConfiguration.WeightedConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> weightedCowType : this.getMoobloomType().configuration().settings().thunderConverts().get()) {
                    if (weightedCowType.weight() > 0) {
                        compatibleList.add(weightedCowType);
                    }
                }

                if (compatibleList.isEmpty()) {
                    super.thunderHit(level, bolt);
                    return;
                } else if (compatibleList.size() == 1) {
                    this.setFlowerType(compatibleList.get(0).configuredCowType().toString());
                } else {
                    for (CowTypeConfiguration.WeightedConfiguredCowType cct : compatibleList) {
                        totalWeight -= cct.weight();
                        if (totalWeight <= 0) {
                            this.setFlowerType(cct.configuredCowType().toString());
                            break;
                        }
                    }
                }
            } else {
                this.setFlowerType(this.getPreviousTypeId());
                this.setPreviousTypeId("");
            }
            this.lastLightningBoltUUID = uuid;
            this.playSound(BovinesSoundEvents.MOOBLOOM_CONVERT.value(), 2.0F, 1.0F);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (bee != null && !this.level().isClientSide()) {
            this.setStandingStillForBeeTicks(0);
            bee = null;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        if (bee != null && !bee.isAlive() && !this.level().isClientSide()) {
            this.setStandingStillForBeeTicks(0);
            bee = null;
        }
        if (this.getStandingStillForBeeTicks() > 0 && !this.level().isClientSide())
            this.setStandingStillForBeeTicks(this.getStandingStillForBeeTicks() - 1);

        super.tick();
        if (this.getPollinatedResetTicks() > 0)
            this.setPollinatedResetTicks(this.getPollinatedResetTicks() - 1);

        if (this.getStandingStillForBeeTicks() > 0) {
            if (!hasRefreshedDimensionsForLaying) {
                this.refreshDimensions();
                ((EntityAccessor)this).bovinesandbuttercups$setEyeHeight(this.getDimensions(this.getPose()).height * 0.85F);
                hasRefreshedDimensionsForLaying = true;
            }
            if (!this.level().isClientSide() && this.bee != null)
                this.getLookControl().setLookAt(bee);
        } else if (hasRefreshedDimensionsForLaying) {
            this.refreshDimensions();
            ((EntityAccessor)this).bovinesandbuttercups$setEyeHeight(this.getDimensions(this.getPose()).height * 0.85F);
            hasRefreshedDimensionsForLaying = false;
        }
    }

    public void spreadFlowers() {
        if (this.level().isClientSide) return;

        BlockState state = null;
        if (this.getMoobloomType().configuration().flower().blockState().isPresent())
            state = this.getMoobloomType().configuration().flower().blockState().get().getBlock().defaultBlockState();
        else if (this.getMoobloomType().configuration().flower().customType().isPresent())
            state = BovinesBlocks.CUSTOM_FLOWER.value().defaultBlockState();

        if (state == null) {
            BovinesAndButtercups.LOG.warn("Moobloom with type '{}' tried to spread flowers without a valid flower type.", this.level().registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(getMoobloomType()));
            return;
        }

        int maxTries = 16;
        int xZScale = 3;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for(int i = 0; i < maxTries; ++i) {
            pos.setWithOffset(this.blockPosition(), random.nextInt(xZScale) - random.nextInt(xZScale), random.nextInt(2) - random.nextInt(2), random.nextInt(xZScale) - random.nextInt(xZScale));
            if (state.canSurvive(this.level(), pos) && this.level().getBlockState(pos).isAir())
                this.setBlockToFlower(state, pos);
        }
        this.gameEvent(GameEvent.BLOCK_PLACE, this);
    }

    public void setBlockToFlower(BlockState state, BlockPos pos) {
        if (this.level().isClientSide) return;
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, 4, 0.2, 0.1, 0.2, 0.0);
        if (state.getBlock() == BovinesBlocks.CUSTOM_FLOWER.value() && this.getMoobloomType().configuration().flower().customType().isPresent()) {
            this.level().setBlock(pos, state, 3);
            BlockEntity blockEntity = this.level().getBlockEntity(pos);
            if (blockEntity instanceof CustomFlowerBlockEntity customFlowerBlockEntity) {
                customFlowerBlockEntity.setFlowerTypeName(this.level().registryAccess().registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().getKey(this.getMoobloomType().configuration().flower().customType().get().value()).toString());
                customFlowerBlockEntity.setChanged();
            }
        } else {
            this.level().setBlock(pos, state, 3);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.getStandingStillForBeeTicks() > 0) {
            return super.getDimensions(pose).scale(1.0F, 0.7F);
        }
        return super.getDimensions(pose);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isBaby()) {
            if (itemStack.is(Items.BONE_MEAL)) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                if (!this.level().isClientSide) {
                    ((ServerLevel) this.level()).sendParticles(ParticleTypes.HAPPY_VILLAGER, this.position().x(), this.position().y() + 1.6D, this.position().z(), 8, 0.5, 0.1, 0.4, 0.0);
                }
                this.spreadFlowers();
                this.playSound(BovinesSoundEvents.MOOBLOOM_EAT.value(), 1.0f, (random.nextFloat() * 0.4F) + 0.8F);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else if (itemStack.is(Items.BOWL)) {
                ItemStack itemStack2;
                itemStack2 = new ItemStack(BovinesItems.NECTAR_BOWL.value());
                if (this.getMoobloomType().configuration().nectarEffect().isPresent()) {
                    NectarBowlItem.saveMobEffect(itemStack2, this.getMoobloomType().configuration().nectarEffect().get().getEffect(), this.getMoobloomType().configuration().nectarEffect().get().getDuration());
                } else if (this.getMoobloomType().configuration().flower().blockState().isPresent() && this.getMoobloomType().configuration().flower().blockState().get().getBlock() instanceof FlowerBlock) {
                    ((FlowerBlock)this.getMoobloomType().configuration().flower().blockState().get().getBlock()).getSuspiciousEffects().forEach(effectEntry ->  {
                        NectarBowlItem.saveMobEffect(itemStack2, effectEntry.effect(), 600);
                    });
                } else {
                    return InteractionResult.PASS;
                }

                NectarBowlItem.saveMoobloomTypeKey(itemStack2, this.level().registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(this.getMoobloomType()));
                ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
                player.setItemInHand(hand, itemStack3);
                this.playSound(BovinesSoundEvents.MOOBLOOM_MILK.value(), 1.0f, 1.0f);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        return super.mobInteract(player, hand);
    }

    public ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> chooseBabyType(LevelAccessor level, Moobloom otherParent, Moobloom child) {
        List<ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>> eligibleCowTypes = new ArrayList<>();

        for (ConfiguredCowType<?, ?> cowType : level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().stream().filter(type -> type.configuration() instanceof Moobloom).toList()) {
            ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> flowerCowType = (ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>) cowType;
            if (flowerCowType.configuration().offspringConditions().isEmpty()) continue;
            var conditions = flowerCowType.configuration().offspringConditions().get();

            if (conditions.predicate().map(vExpression -> vExpression.evaluate(DFunctions.createEntityContext(this)).get(StandardVTypes.BOOLEAN)).orElse(true) && conditions.predicate().map(vExpression -> vExpression.evaluate(DFunctions.createEntityContext(otherParent)).get(StandardVTypes.BOOLEAN)).orElse(true))
                eligibleCowTypes.add(flowerCowType);
        }

        if (!eligibleCowTypes.isEmpty()) {
            int random = this.getRandom().nextInt(eligibleCowTypes.size());
            var randomType = eligibleCowTypes.get(random);

            // TODO: Particle creation upon a successful breed.

            if (this.getLoveCause() != null && !level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(randomType).equals(level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(this.getMoobloomType())) && !level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(randomType).equals(level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(otherParent.getMoobloomType()))) {
                BovinesCriteriaTriggers.MUTATION.trigger(this.getLoveCause(), this, otherParent, child, level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(randomType));
            }
            return randomType;
        }

        if (!otherParent.getMoobloomType().equals(this.getMoobloomType()) && this.getRandom().nextBoolean())
            return otherParent.getMoobloomType();

        return this.getMoobloomType();
    }

    public void createParticleTrail(Vec3 pos, ParticleOptions options) {
        double value = (1 - (1 / (pos.distanceTo(this.position()) + 1))) / 4;

        for (double d = 0.0; d < 1.0; d += value) {
            ((ServerLevel)this.level()).sendParticles(options, Mth.lerp(d, pos.x(), this.position().x()), Mth.lerp(d, pos.y(), this.position().y()), Mth.lerp(d, pos.z(), this.position().z()), 1, 0.05, 0.05,  0.05, 0.01);
        }
    }

    @Override
    public Moobloom getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Moobloom moobloom = BovinesEntityTypes.MOOBLOOM.value().create(serverLevel);
        moobloom.setFlowerType(this.chooseBabyType(serverLevel, (Moobloom)ageableMob, moobloom), serverLevel);
        return moobloom;
    }

    public boolean commonReadyForShearing() {
        return this.isAlive() && !this.isBaby() && this.entityData.get(ALLOW_SHEARING);
    }

    public ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> getMoobloomType() {
        try {
            Registry<ConfiguredCowType<?, ?>> registry = this.level().registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow();
            if (this.type != null && getTypeId() != null && registry.containsKey(new ResourceLocation(getTypeId())) && registry.get(new ResourceLocation(getTypeId())).configuration() instanceof MoobloomConfiguration && this.type.configuration() != registry.get(new ResourceLocation(getTypeId())).configuration()) {
                this.type = (ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>) registry.get(new ResourceLocation(getTypeId()));
                return this.type;
            } else if (this.type != null) {
                return this.type;
            } else if (getTypeId() != null && registry.containsKey(new ResourceLocation(getTypeId())) && registry.get(new ResourceLocation(getTypeId())).configuration() instanceof MoobloomConfiguration) {
                this.type = (ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>) registry.get(new ResourceLocation(getTypeId()));
                return this.type;
            }
            this.type = BovinesCowTypes.MOOBLOOM_TYPE.value().defaultConfigured().get();
            BovinesAndButtercups.LOG.warn("Could not find type '{}' from moobloom at {}. Setting type to 'bovinesandbuttercups:missing_moobloom'.", ResourceLocation.tryParse(getTypeId()), position());
            return this.type;
        } catch (Exception e) {
            this.type = BovinesCowTypes.MOOBLOOM_TYPE.value().defaultConfigured().get();
            BovinesAndButtercups.LOG.warn("Could not get type '{}' from moobloom at {}. Setting type to 'bovinesandbuttercups:missing_moobloom'. {}", ResourceLocation.tryParse(getTypeId()), position(), e.getMessage());
            return this.type;
        }
    }

    public String getTypeId() {
        return this.entityData.get(TYPE_ID);
    }

    public String getPreviousTypeId() {
        return this.entityData.get(PREVIOUS_TYPE_ID);
    }

    public void setPreviousTypeId(String value) {
        this.entityData.set(PREVIOUS_TYPE_ID, value);
    }

    public void setFlowerType(String value) {
        this.entityData.set(TYPE_ID, value);
        this.getMoobloomType();
    }

    public void setFlowerType(ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> value, LevelAccessor level) {
        this.entityData.set(TYPE_ID, level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().getKey(value).toString());
        this.getMoobloomType();
    }

    public int getPollinatedResetTicks() {
        return this.entityData.get(POLLINATED_RESET_TICKS);
    }

    public void setPollinatedResetTicks(int value) {
        this.entityData.set(POLLINATED_RESET_TICKS, value);
    }

    public int getStandingStillForBeeTicks() {
        return this.entityData.get(STANDING_STILL_FOR_BEE_TICKS);
    }

    public void setStandingStillForBeeTicks(int value) {
        this.entityData.set(STANDING_STILL_FOR_BEE_TICKS, value);
    }

    public boolean shouldAllowShearing() {
        return this.entityData.get(ALLOW_SHEARING);
    }

    public void setAllowShearing(boolean value) {
        this.entityData.set(ALLOW_SHEARING, value);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityTag) {
        if (entityTag == null || !entityTag.contains("Type")) {
            if (getTotalSpawnWeight(level(), this.blockPosition()) > 0) {
                this.setFlowerType(getMoobloomSpawnTypeDependingOnBiome(level(), this.blockPosition(), this.getRandom()), level());
            } else {
                this.setFlowerType(getMoobloomSpawnType(level(), this.getRandom()), level());
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, entityData, entityTag);
    }

    public static int getTotalSpawnWeight(LevelAccessor level, BlockPos pos) {
        int totalWeight = 0;

        for (ConfiguredCowType<?, ?> cowType : level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().stream().filter(configuredCowType -> configuredCowType.configuration() instanceof MoobloomConfiguration).toList()) {
            if (!(cowType.configuration() instanceof MoobloomConfiguration configuration)) continue;

            if (configuration.settings().naturalSpawnWeight() > 0 && configuration.settings().biomes().isPresent() && HolderUtil.containsBiomeHolder(level.getBiome(pos), configuration.settings().biomes().get())) {
                totalWeight += configuration.settings().naturalSpawnWeight();
            }
        }
        return totalWeight;
    }

    public ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> getMoobloomSpawnType(LevelAccessor level, RandomSource random) {
        int totalWeight = 0;

        List<ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>> moobloomList = new ArrayList<>();

        for (ConfiguredCowType<?, ?> configuredCowType : level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().stream().filter(configuredCowType -> configuredCowType.configuration() instanceof MoobloomConfiguration).toList()) {
            if (!(configuredCowType.configuration() instanceof MoobloomConfiguration configuration)) continue;

            if (configuration.settings().naturalSpawnWeight() > 0) {
                moobloomList.add((ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>) configuredCowType);
                totalWeight += configuration.settings().naturalSpawnWeight();
            }
        }

        if (moobloomList.size() == 1) {
            return moobloomList.get(0);
        } else if (!moobloomList.isEmpty()) {
            int r = Mth.nextInt(random, 0, totalWeight - 1);
            for (ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> cfc : moobloomList) {
                r -= cfc.configuration().settings().naturalSpawnWeight();
                if (r < 0.0) {
                    return cfc;
                }
            }
        }
        return (ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>) level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().get(BovinesCowTypes.MOOBLOOM_TYPE.value().defaultConfiguredId());
    }

    public ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> getMoobloomSpawnTypeDependingOnBiome(LevelAccessor level, BlockPos pos, RandomSource random) {
        List<ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>> moobloomList = new ArrayList<>();
        int totalWeight = 0;

        for (Map.Entry<ResourceKey<ConfiguredCowType<?, ?>>, ConfiguredCowType<?, ?>> entry : level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().entrySet().stream().filter(configuredCowType -> configuredCowType.getValue().configuration() instanceof MoobloomConfiguration).toList()) {
            ConfiguredCowType<?, ?> configuredCowType = entry.getValue();
            if (!(configuredCowType.configuration() instanceof MoobloomConfiguration configuration)) continue;

            if (configuration.settings().naturalSpawnWeight() > 0 && configuration.settings().biomes().isPresent() && HolderUtil.containsBiomeHolder(level.getBiome(pos), configuration.settings().biomes().get())) {
                moobloomList.add((ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>) configuredCowType);
                totalWeight += configuration.settings().naturalSpawnWeight();
            }
        }

        if (moobloomList.size() == 1) {
            return moobloomList.get(0);
        } else if (!moobloomList.isEmpty()) {
            int r = Mth.nextInt(random, 0, totalWeight - 1);
            for (ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>> cfc : moobloomList) {
                r -= cfc.configuration().settings().naturalSpawnWeight();
                if (r < 0.0) {
                    return cfc;
                }
            }
        }
        return (ConfiguredCowType<MoobloomConfiguration, CowType<MoobloomConfiguration>>)level.registryAccess().registry(BovinesResourceKeys.CONFIGURED_COW_TYPE).orElseThrow().get(BovinesAndButtercups.asResource("missing_moobloom"));
    }

    public List<ItemStack> commonShear(SoundSource category) {
        // Implemented in FlowerCowFabriclike and FlowerCowForge
        List<ItemStack> stacks = new ArrayList<>();
        this.level().playSound(null, this, BovinesSoundEvents.MOOBLOOM_SHEAR.value(), category, 1.0f, 1.0f);
        if (!this.level().isClientSide) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            this.discard();
            Cow cowEntity = EntityType.COW.create(this.level());
            if (cowEntity != null) {
                cowEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                cowEntity.setHealth(this.getHealth());
                cowEntity.yBodyRot = this.yBodyRot;
                if (this.hasCustomName()) {
                    cowEntity.setCustomName(this.getCustomName());
                    cowEntity.setCustomNameVisible(this.isCustomNameVisible());
                }
                if (this.isPersistenceRequired()) {
                    cowEntity.setPersistenceRequired();
                }
                cowEntity.setInvulnerable(this.isInvulnerable());
                this.level().addFreshEntity(cowEntity);
                for (int i = 0; i < 5; ++i) {
                    if (this.getMoobloomType().configuration().flower().blockState().isPresent()) {
                        stacks.add(new ItemStack(this.getMoobloomType().configuration().flower().blockState().get().getBlock()));
                    } else if (this.getMoobloomType().configuration().flower().customType().isPresent()) {
                        ItemStack itemStack = new ItemStack(BovinesItems.CUSTOM_FLOWER.value());
                        CompoundTag compound = new CompoundTag();
                        compound.putString("Type", level().registryAccess().registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().getKey(this.getMoobloomType().configuration().flower().customType().get().value()).toString());
                        itemStack.getOrCreateTag().put("BlockEntityTag", compound);
                        stacks.add(itemStack);
                    }
                }
            }
        }
        return stacks;
    }

    public class LookAtBeeGoal extends Goal {
        public LookAtBeeGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return Moobloom.this.getStandingStillForBeeTicks() > 0;
        }

        @Override
        public void start() {
            Moobloom.this.getNavigation().stop();
        }
    }
}
