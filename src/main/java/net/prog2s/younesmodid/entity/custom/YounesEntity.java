package net.prog2s.younesmodid.entity.custom;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.prog2s.younesmodid.item.ModItems;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;

import software.bernie.geckolib3.core.PlayState;

import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.*;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


public class YounesEntity extends PathfinderMob implements IAnimatable {
    private final ItemStack heldItem = new ItemStack(ModItems.PHONE.get());
    protected static final EntityDataAccessor<Byte> FLAGS_ID = SynchedEntityData.defineId(YounesEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.defineId(YounesEntity.class, EntityDataSerializers.BOOLEAN);

   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(YounesEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private static final ItemStack tamingItem = new ItemStack(ModItems.PHONE.get());


    private static final ILoopType loop =ILoopType.EDefaultLoopTypes.LOOP;
    private  boolean attacks = false;
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public YounesEntity(EntityType<? extends YounesEntity> entityType, Level level) {
        super(entityType, level);
    }


    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6f)
                .add(Attributes.ATTACK_DAMAGE, 5.0f)
                .add(Attributes.ATTACK_SPEED, 0.3f).build();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TAMED, false);
        this.entityData.define(OWNER_UUID,Optional.empty());
        this.entityData.define(FLAGS_ID, (byte)0);
        super.defineSynchedData();
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }}
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String s = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }
        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
                this.setTamed(true);
            } catch (Throwable throwable) {
                this.setTamed(false);
            }
        }

    }

    protected void registerGoals(){
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal<EnderMan>(this, EnderMan.class, true,true));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(ModItems.PHONE.get()),false ));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class,1.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

    }



    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event){
        if (attacks){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.stalken", loop));
        }
        if (event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.walk2",loop));
            return PlayState.CONTINUE;
        }

        YounesEntity.this.handleEntityEvent((byte) 10);
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.idle", loop));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
    data.addAnimationController(new AnimationController(this, "controller",
            0, this::predicate));
    }


    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.getItem() == tamingItem.getItem() && !this.isTamed()) {
            setTamed(true);
            this.setOwnerUUID(player.getUUID());
            this.setItemSlot(EquipmentSlot.MAINHAND, heldItem);

            if(!player.isCreative()) {
                itemStack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        }


        return super.mobInteract(player, hand);
    }
    private void setTamed(boolean tamed) {
        byte b0 = this.entityData.get(FLAGS_ID);
        if (tamed) {
            this.entityData.set(FLAGS_ID, (byte)(b0 | 4));
        } else {
            this.entityData.set(FLAGS_ID, (byte)(b0 & -5));
        }
    }
    private boolean isTamed() {
        return (this.entityData.get(FLAGS_ID) & 4) != 0;
    }

    public void setOwnerUUID(@Nullable UUID p_21817_) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(p_21817_));
    }
public  void setAttacks(boolean bool){
        attacks = bool;
}
    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse((UUID)null);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void tick() {
        super.tick();


        if(this.isTamed() && this.getOwnerUUID() != null){
            Player owner = this.level.getPlayerByUUID(this.getOwnerUUID());
            double distanceSq = this.distanceToSqr(owner);
            if(distanceSq >16.0){
                this.getNavigation().moveTo(owner,1.0);
            }
        }
    }

    protected SoundEvent getHurtSound(){
        return SoundEvents.SKELETON_HURT;
    }
    protected SoundEvent getAmbientSound(){
        return SoundEvents.SKELETON_AMBIENT;
    }




    protected float getSoundVolume(){
        return 0.4F;
    }





}
