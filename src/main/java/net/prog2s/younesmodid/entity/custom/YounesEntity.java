package net.prog2s.younesmodid.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.prog2s.younesmodid.entity.ModEntityTypes;
import net.prog2s.younesmodid.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;

import software.bernie.geckolib3.core.PlayState;

import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.*;
import software.bernie.geckolib3.util.GeckoLibUtil;




public class YounesEntity extends PathfinderMob implements IAnimatable {
    private final ItemStack heldItem = new ItemStack(ModItems.PHONE.get());
    private static final EntityDataAccessor<Boolean> DATA_TAMED = SynchedEntityData.defineId(YounesEntity.class, EntityDataSerializers.BOOLEAN);

    private static final String TAG_TAMED = "Tamed";
    private static final String TAG_OWNER = "OwnerUUID";
    private Player owner;
    private static final ItemStack tamingItem = new ItemStack(ModItems.PHONE.get());

    private boolean isTamed = false;
    private static final ILoopType loop =ILoopType.EDefaultLoopTypes.LOOP;
    private  boolean attacks = false;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
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


    protected void registerGoals(){
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal<EnderMan>(this, EnderMan.class, true,true));
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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.getItem() == tamingItem.getItem() && !this.isTamed()) {
            this.isTamed = true;
            this.setOwner(player);
            this.setItemSlot(EquipmentSlot.MAINHAND, heldItem);

            if(!player.isCreative()) {
                itemStack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        }


        return super.mobInteract(player, hand);
    }

    private void setTamed(boolean tamed) {
    this.entityData.set(DATA_TAMED, tamed);
    }
    private boolean isTamed() {
        return isTamed;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
public  void setAttacks(boolean bool){
        attacks = bool;
}
    @Nullable
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void tick() {
        super.tick();


        if(this.isTamed() && this.getOwner() != null){
            double distanceSq = this.distanceToSqr(this.owner);
            if(distanceSq >16.0){
                this.getNavigation().moveTo(this.owner,1.0);
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