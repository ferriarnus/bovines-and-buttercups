package net.merchantpug.bovinesandbuttercups.content.entity;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.cowtype.OffspringConditionsConfiguration;
import net.merchantpug.bovinesandbuttercups.content.block.entity.CustomFlowerBlockEntity;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomFlower;
import net.merchantpug.bovinesandbuttercups.content.component.ItemMoobloomType;
import net.merchantpug.bovinesandbuttercups.content.component.NectarEffects;
import net.merchantpug.bovinesandbuttercups.content.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.mixin.EntityAccessor;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypeTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCriteriaTriggers;
import net.merchantpug.bovinesandbuttercups.registry.BovinesDataComponents;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.registry.BovinesLootContextParamSets;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.registry.BovinesSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Moobloom extends Cow {
    private static final EntityDataAccessor<String> TYPE_ID = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> PREVIOUS_TYPE_ID = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> POLLINATED_RESET_TICKS = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STANDING_STILL_FOR_BEE_TICKS = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ALLOW_SHEARING = SynchedEntityData.defineId(Moobloom.class, EntityDataSerializers.BOOLEAN);
    private Holder<CowType<?>> type;
    @Nullable
    public Bee bee;
    private boolean hasRefreshedDimensionsForLaying;
    @Nullable private UUID lastLightningBoltUUID;
    private final List<Vec3> particlePositions = new ArrayList<>();

    public Moobloom(EntityType<? extends Moobloom> entityType, Level level) {
        super(entityType, level);
        this.bee = null;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TYPE_ID, "bovinesandbuttercups:missing_moobloom");
        builder.define(PREVIOUS_TYPE_ID, "");
        builder.define(POLLINATED_RESET_TICKS, 0);
        builder.define(STANDING_STILL_FOR_BEE_TICKS, 0);
        builder.define(ALLOW_SHEARING, true);
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
        compound.putString("Type", getTypeId());
        if (!this.getPreviousTypeId().equals("")) {
            compound.putString("PreviousType", getPreviousTypeId());
        }
        compound.putInt("PollinatedResetTicks", getPollinatedResetTicks());
        compound.putBoolean("AllowShearing", shouldAllowShearing());
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
            if (getPreviousTypeId().equals("")) {
                if (getMoobloomType().value().configuration().settings().thunderConverts().isEmpty()) {
                    super.thunderHit(level, bolt);
                    return;
                }
                setPreviousTypeId(getTypeId());

                List<MoobloomConfiguration.WeightedConfiguredCowType<MoobloomConfiguration>> compatibleList = new ArrayList<>();
                int totalWeight = 0;

                for (CowTypeConfiguration.WeightedConfiguredCowType<MoobloomConfiguration> weightedCowType : getMoobloomType().value().configuration().settings().thunderConverts()) {
                    if (weightedCowType.weight() > 0) {
                        compatibleList.add(weightedCowType);
                    }
                }

                if (compatibleList.isEmpty()) {
                    super.thunderHit(level, bolt);
                    return;
                } else if (compatibleList.size() == 1) {
                    setFlowerType(compatibleList.getFirst().cowType(), level);
                } else {
                    for (CowTypeConfiguration.WeightedConfiguredCowType<MoobloomConfiguration> cct : compatibleList) {
                        totalWeight -= cct.weight();
                        if (totalWeight <= 0) {
                            setFlowerType(cct.cowType(), level);
                            break;
                        }
                    }
                }
            } else {
                setFlowerType(this.getPreviousTypeId());
                setPreviousTypeId("");
            }
            lastLightningBoltUUID = uuid;
            playSound(BovinesSoundEvents.MOOBLOOM_CONVERT, 2.0F, 1.0F);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return false;
        }
        if (bee != null && !level().isClientSide()) {
            setStandingStillForBeeTicks(0);
            bee = null;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        if (bee != null && !bee.isAlive() && !level().isClientSide()) {
            setStandingStillForBeeTicks(0);
            bee = null;
        }
        if (getStandingStillForBeeTicks() > 0 && !level().isClientSide())
            setStandingStillForBeeTicks(getStandingStillForBeeTicks() - 1);

        super.tick();
        if (getPollinatedResetTicks() > 0)
            setPollinatedResetTicks(getPollinatedResetTicks() - 1);

        if (getStandingStillForBeeTicks() > 0) {
            if (!hasRefreshedDimensionsForLaying) {
                refreshDimensions();
                ((EntityAccessor)this).bovinesandbuttercups$setEyeHeight(getDimensions(getPose()).height() * 0.85F);
                hasRefreshedDimensionsForLaying = true;
            }
            if (!level().isClientSide() && bee != null)
                getLookControl().setLookAt(bee);
        } else if (hasRefreshedDimensionsForLaying) {
            refreshDimensions();
            ((EntityAccessor)this).bovinesandbuttercups$setEyeHeight(getDimensions(getPose()).height() * 0.85F);
            hasRefreshedDimensionsForLaying = false;
        }
    }

    public void spreadFlowers() {
        if (this.level().isClientSide) return;

        BlockState state = null;
        if (getMoobloomType().value().configuration().flower().blockState().isPresent())
            state = getMoobloomType().value().configuration().flower().blockState().get().getBlock().defaultBlockState();
        else if (getMoobloomType().value().configuration().flower().customType().isPresent())
            state = BovinesBlocks.CUSTOM_FLOWER.defaultBlockState();

        if (state == null) {
            BovinesAndButtercups.LOG.warn("Moobloom with type '{}' tried to spread flowers without a valid flower type.", getMoobloomType().getRegisteredName());
            return;
        }

        int maxTries = 16;
        int xZScale = 3;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for(int i = 0; i < maxTries; ++i) {
            pos.setWithOffset(this.blockPosition(), random.nextInt(xZScale) - random.nextInt(xZScale), random.nextInt(2) - random.nextInt(2), random.nextInt(xZScale) - random.nextInt(xZScale));
            if (state.canSurvive(this.level(), pos) && this.level().getBlockState(pos).isAir())
                setBlockToFlower(state, pos);
        }
        this.gameEvent(GameEvent.BLOCK_PLACE, this);
    }

    public void setBlockToFlower(BlockState state, BlockPos pos) {
        if (this.level().isClientSide) return;
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5D, pos.getY() + 0.3D, pos.getZ() + 0.5D, 4, 0.2, 0.1, 0.2, 0.0);
        if (state.getBlock() == BovinesBlocks.CUSTOM_FLOWER && this.getMoobloomType().value().configuration().flower().customType().isPresent()) {
            this.level().setBlock(pos, state, 3);
            BlockEntity blockEntity = this.level().getBlockEntity(pos);
            if (blockEntity instanceof CustomFlowerBlockEntity customFlowerBlockEntity) {
                customFlowerBlockEntity.setCustomFlower(new ItemCustomFlower(getMoobloomType().value().configuration().flower().customType().get()));
                customFlowerBlockEntity.setChanged();
            }
        } else {
            this.level().setBlock(pos, state, 3);
        }
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        if (this.getStandingStillForBeeTicks() > 0) {
            return super.getDefaultDimensions(pose).scale(1.0F, 0.7F);
        }
        return super.getDefaultDimensions(pose);
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
                this.playSound(BovinesSoundEvents.MOOBLOOM_EAT, 1.0f, (random.nextFloat() * 0.4F) + 0.8F);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else if (itemStack.is(Items.BOWL)) {
                ItemStack itemStack2;
                itemStack2 = new ItemStack(BovinesItems.NECTAR_BOWL);
                if (!getMoobloomType().value().configuration().nectarEffects().effects().isEmpty()) {
                    itemStack2.set(BovinesDataComponents.NECTAR_EFFECTS, getMoobloomType().value().configuration().nectarEffects());
                } else if (getMoobloomType().value().configuration().flower().blockState().isPresent() && this.getMoobloomType().value().configuration().flower().blockState().get().getBlock() instanceof FlowerBlock) {
                    ((FlowerBlock)getMoobloomType().value().configuration().flower().blockState().get().getBlock()).getSuspiciousEffects().effects().forEach(effectEntry -> {
                        itemStack2.set(BovinesDataComponents.NECTAR_EFFECTS, itemStack2.getOrDefault(BovinesDataComponents.NECTAR_EFFECTS, new NectarEffects(List.of()).withEffectAdded(new NectarEffects.Entry(effectEntry.effect(), effectEntry.duration()))));
                    });
                } else {
                    return InteractionResult.PASS;
                }

                itemStack2.set(BovinesDataComponents.MOOBLOOM_TYPE, new ItemMoobloomType(getMoobloomType()));
                ItemStack itemStack3 = ItemUtils.createFilledResult(itemStack, player, itemStack2, false);
                player.setItemInHand(hand, itemStack3);
                this.playSound(BovinesSoundEvents.MOOBLOOM_MILK, 1.0f, 1.0f);
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    public Holder<CowType<MoobloomConfiguration>> chooseBabyType(ServerLevel level, Moobloom otherParent, Moobloom child) {
        List<Holder<CowType<MoobloomConfiguration>>> eligibleCowTypes = new ArrayList<>();

        for (Holder.Reference<CowType<?>> cowType : level.registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().holders().filter(type -> type.isBound() && type.value().type() == BovinesCowTypeTypes.MOOBLOOM_TYPE).toList()) {
            Holder.Reference<CowType<MoobloomConfiguration>> flowerCowType = (Holder.Reference) cowType;
            if (flowerCowType.value().configuration().offspringConditions() == OffspringConditionsConfiguration.EMPTY) continue;
            var conditions = flowerCowType.value().configuration().offspringConditions();

            LootParams.Builder params = new LootParams.Builder(level);
            params.withParameter(LootContextParams.THIS_ENTITY, this);
            params.withParameter(BovinesLootContextParamSets.PARTNER, otherParent);
            params.withParameter(BovinesLootContextParamSets.CHILD, child);
            params.withParameter(LootContextParams.ORIGIN, child.position());

            LootContext thisContext = new LootContext.Builder(params.create(BovinesLootContextParamSets.BREEDING)).create(Optional.empty());

            params.withParameter(LootContextParams.THIS_ENTITY, otherParent);
            params.withParameter(BovinesLootContextParamSets.PARTNER, this);
            LootContext otherContext = new LootContext.Builder(params.create(BovinesLootContextParamSets.BREEDING)).create(Optional.empty());

            if (conditions.thisConditions().stream().allMatch(condition -> condition.test(thisContext))
            && conditions.otherConditions().stream().allMatch(condition -> condition.test(otherContext)))
                eligibleCowTypes.add(flowerCowType);
        }

        if (!eligibleCowTypes.isEmpty()) {
            int random = this.getRandom().nextInt(eligibleCowTypes.size());
            var randomType = eligibleCowTypes.get(random);

            child.createParticles();

            if (this.getLoveCause() != null)
                BovinesCriteriaTriggers.MUTATION.trigger(this.getLoveCause(), this, otherParent, child, (Holder) randomType);
            return randomType;
        }

        if (!otherParent.getMoobloomType().equals(this.getMoobloomType()) && this.getRandom().nextBoolean())
            return otherParent.getMoobloomType();

        return this.getMoobloomType();
    }

    public void addParticlePosition(Vec3 pos) {
        particlePositions.add(pos);
    }

    public void createParticles() {
        if (!getMoobloomType().isBound() || getMoobloomType().value().configuration().settings().particle().isEmpty())
            return;

        if (particlePositions.isEmpty() && !level().isClientSide())
            ((ServerLevel)this.level()).sendParticles(getMoobloomType().value().configuration().settings().particle().get(), getX(), getY(0.5), getZ(), 6, 0.05, 0.05, 0.05, 0.01);

        for (Vec3 pos : particlePositions)
            createParticleTrail(pos, getMoobloomType().value().configuration().settings().particle().get());

        particlePositions.clear();
    }

    public void createParticleTrail(Vec3 pos, ParticleOptions options) {
        double value = (1 - (1 / (pos.distanceTo(position()) + 1))) / 4;

        for (double d = 0.0; d < 1.0; d += value) {
            ((ServerLevel)level()).sendParticles(options, Mth.lerp(d, pos.x(), position().x()), Mth.lerp(d, pos.y(), position().y()), Mth.lerp(d, pos.z(), position().z()), 1, 0.05, 0.05,  0.05, 0.01);
        }
    }

    @Override
    public Moobloom getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Moobloom moobloom = BovinesEntityTypes.MOOBLOOM.create(serverLevel);
        moobloom.setFlowerType(chooseBabyType(serverLevel, (Moobloom)ageableMob, moobloom), serverLevel);
        return moobloom;
    }

    public boolean commonReadyForShearing() {
        return isAlive() && !isBaby() && entityData.get(ALLOW_SHEARING);
    }

    public Holder<CowType<MoobloomConfiguration>> getMoobloomType() {
        try {
            Registry<CowType<?>> registry = this.level().registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow();
            if (type != null && type.isBound() && getTypeId() != null && registry.containsKey(new ResourceLocation(getTypeId())) && registry.get(new ResourceLocation(getTypeId())).configuration() instanceof MoobloomConfiguration && type.value().configuration() != registry.get(new ResourceLocation(getTypeId())).configuration()) {
                type = registry.getHolder(new ResourceLocation(getTypeId())).orElseThrow();
                return (Holder) type;
            } else if (type != null && type.isBound()) {
                return (Holder) type;
            } else if (getTypeId() != null && registry.containsKey(new ResourceLocation(getTypeId())) && registry.get(new ResourceLocation(getTypeId())).configuration() instanceof MoobloomConfiguration) {
                type = registry.getHolder(new ResourceLocation(getTypeId())).orElseThrow();
                return (Holder) type;
            }
            type = level().registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().getHolder(BovinesCowTypes.MoobloomKeys.MISSING_MOOBLOOM).orElseThrow();
            BovinesAndButtercups.LOG.warn("Could not find type '{}' from moobloom at {}. Setting type to 'bovinesandbuttercups:missing_moobloom'.", ResourceLocation.tryParse(getTypeId()), position());
            return (Holder)type;
        } catch (Exception e) {
            type = level().registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().getHolder(BovinesCowTypes.MoobloomKeys.MISSING_MOOBLOOM).orElseThrow();
            BovinesAndButtercups.LOG.warn("Could not get type '{}' from moobloom at {}. Setting type to 'bovinesandbuttercups:missing_moobloom'. {}", ResourceLocation.tryParse(getTypeId()), position(), e.getMessage());
            return (Holder)type;
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

    public void setFlowerType(Holder<CowType<MoobloomConfiguration>> value, LevelAccessor level) {
        this.entityData.set(TYPE_ID, value.getRegisteredName());
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

    public static int getTotalSpawnWeight(LevelAccessor level, BlockPos pos) {
        int totalWeight = 0;

        for (CowType<?> cowType : level.registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().stream().filter(configuredCowType -> configuredCowType.configuration() instanceof MoobloomConfiguration).toList()) {
            if (!(cowType.configuration() instanceof MoobloomConfiguration configuration))
                continue;

            Optional<WeightedEntry.Wrapper<HolderSet<Biome>>> biome = configuration.settings().biomes().stream().filter(holderSetWrapper -> holderSetWrapper.data().contains(level.getBiome(pos))).findFirst();
            if (biome.isPresent())
                totalWeight += biome.get().weight().asInt();
        }
        return totalWeight;
    }

    public List<ItemStack> commonShear(SoundSource category) {
        List<ItemStack> stacks = new ArrayList<>();
        this.level().playSound(null, this, BovinesSoundEvents.MOOBLOOM_SHEAR, category, 1.0f, 1.0f);
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
                    if (getMoobloomType().value().configuration().flower().blockState().isPresent()) {
                        stacks.add(new ItemStack(getMoobloomType().value().configuration().flower().blockState().get().getBlock()));
                    } else if (getMoobloomType().value().configuration().flower().customType().isPresent()) {
                        ItemStack itemStack = new ItemStack(BovinesItems.CUSTOM_FLOWER);
                        itemStack.set(BovinesDataComponents.CUSTOM_FLOWER, new ItemCustomFlower(getMoobloomType().value().configuration().flower().customType().get()));
                        stacks.add(itemStack);
                    }
                }
            }
        }
        return stacks;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
        if (data == null) {
            data = new MoobloomGroupData();
        }
        this.setFlowerType(((MoobloomGroupData)data).getSpawnType(blockPosition(), level, level.getRandom()), level);
        return super.finalizeSpawn(level, difficulty, spawnType, data);
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

    public static class MoobloomGroupData extends AgeableMob.AgeableMobGroupData {
        public MoobloomGroupData() {
            super(true);
        }

        public Holder<CowType<MoobloomConfiguration>> getSpawnType(BlockPos pos, ServerLevelAccessor level, RandomSource random) {
            if (getTotalSpawnWeight(level, pos) > 0)
                return getMoobloomSpawnTypeDependingOnBiome(level, pos, random);
            else
                return getMostCommonMoobloomSpawnType(level, random);
        }

        public Holder<CowType<MoobloomConfiguration>> getMostCommonMoobloomSpawnType(ServerLevelAccessor level, RandomSource random) {
            int largestWeight = 0;
            Holder<CowType<?>> finalCowType = level.registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().getHolder(BovinesCowTypes.MoobloomKeys.MISSING_MOOBLOOM).get();

            for (Holder<CowType<?>> cowType : level.registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().holders().filter(configuredCowType -> configuredCowType.isBound() && configuredCowType.value().configuration() instanceof MoobloomConfiguration).toList()) {
                if (!(cowType.value().configuration() instanceof MoobloomConfiguration configuration)) continue;

                int max = configuration.settings().biomes().stream().map(wrapper -> wrapper.weight().asInt()).max(Comparator.comparingInt(value -> value)).orElse(0);
                if (max > largestWeight) {
                    finalCowType = cowType;
                    largestWeight = max;
                }
            }

            return (Holder)finalCowType;
        }

        public Holder<CowType<MoobloomConfiguration>> getMoobloomSpawnTypeDependingOnBiome(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
            List<Holder<CowType<MoobloomConfiguration>>> moobloomList = new ArrayList<>();
            int totalWeight = 0;

            for (Holder.Reference<CowType<?>> cowType : level.registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().holders().filter(configuredCowType -> configuredCowType.isBound() && configuredCowType.value().configuration() instanceof MoobloomConfiguration).toList()) {
                if (!(cowType.value().configuration() instanceof MoobloomConfiguration configuration)) continue;

                Optional<WeightedEntry.Wrapper<HolderSet<Biome>>> biome = configuration.settings().biomes().stream().filter(holderSetWrapper -> holderSetWrapper.data().contains(level.getBiome(pos))).findFirst();
                if (biome.isPresent()) {
                    moobloomList.add((Holder) cowType);
                    totalWeight += biome.get().weight().asInt();
                }
            }

            if (moobloomList.size() == 1) {
                return moobloomList.getFirst();
            } else if (!moobloomList.isEmpty()) {
                int r = Mth.nextInt(random, 0, totalWeight - 1);
                for (Holder<CowType<MoobloomConfiguration>> cowType : moobloomList) {
                    int max = cowType.value().configuration().settings().biomes().stream().filter(wrapper -> wrapper.data().contains(level.getBiome(pos))).map(wrapper -> wrapper.weight().asInt()).max(Comparator.comparingInt(value -> value)).orElse(0);
                    r -= max;
                    if (r < 0.0)
                        return cowType;
                }
            }
            return (Holder)level.registryAccess().registry(BovinesRegistryKeys.COW_TYPE).orElseThrow().getHolder(BovinesCowTypes.MoobloomKeys.MISSING_MOOBLOOM).get();
        }
    }
}
