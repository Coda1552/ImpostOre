package coda.impostore;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class ImpostOreEntity extends Monster {
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(ImpostOreEntity.class, EntityDataSerializers.BLOCK_POS);
    protected static final EntityDataAccessor<Optional<BlockState>> DATA_BLOCKSTATE = SynchedEntityData.defineId(ImpostOreEntity.class, EntityDataSerializers.BLOCK_STATE);

    public ImpostOreEntity(EntityType<? extends Monster> p_i48553_1_, Level p_i48553_2_) {
        super(p_i48553_1_, p_i48553_2_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
        super.dropCustomDeathLoot(p_213333_1_, p_213333_2_, p_213333_3_);
        Entity attacker = p_213333_1_.getEntity();
        if (attacker instanceof LivingEntity) {
            if (((LivingEntity) attacker).getMainHandItem().getItem().isCorrectToolForDrops(((LivingEntity) attacker).getMainHandItem(), getBlockState())) {
                for (ItemStack drop : getBlockState().getDrops(new LootContext.Builder((ServerLevel) level).withRandom(random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(getOnPos())).withParameter(LootContextParams.TOOL, ((LivingEntity) attacker).getMainHandItem()))) {
                    spawnAtLocation(drop);
                }
            }
            else {
                for (ItemStack drop : getBlockState().getDrops(new LootContext.Builder((ServerLevel) level).withRandom(random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(getOnPos())).withParameter(LootContextParams.TOOL, ((LivingEntity) attacker).getMainHandItem()))) {
                    spawnAtLocation(drop);
                }
            }
        }
    }

    public void addAdditionalSaveData(CompoundTag p_213281_1_) {
        p_213281_1_.put("BlockState", NbtUtils.writeBlockState(getBlockState()));
    }

    public void readAdditionalSaveData(CompoundTag p_70037_1_) {
        setBlockState(NbtUtils.readBlockState(p_70037_1_.getCompound("BlockState")));
    }

    public BlockState getBlockState() {
        return this.entityData.get(DATA_BLOCKSTATE).orElse(Blocks.STONE.defaultBlockState());
    }

    public void setBlockState(BlockState state) {
        this.entityData.set(DATA_BLOCKSTATE, Optional.of(state));
    }

    public Level getLevel() {
        return this.level;
    }

    public void setStartPos(BlockPos p_184530_1_) {
        this.entityData.set(DATA_START_POS, p_184530_1_);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
        this.entityData.define(DATA_BLOCKSTATE, Optional.empty());
    }
}
