package dev.hooman.mixin;

import dev.hooman.config.CrystalUtilsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(FlyingItemEntityRenderer.class)
public abstract class FlyingItemEntityRendererMixin<T extends Entity> {
    @Mutable
    @Final
    @Shadow
    private float scale;
    @Mutable
    @Shadow @Final private boolean lit;
    @Unique
    public List<Entity> notified = new ArrayList<>();

    @Inject(method = "render", at = @At("TAIL"))
    public void updated(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci){
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        CrystalUtilsConfig instance = CrystalUtilsConfig.getInstance();
        if(instance.pearlSize && entity instanceof EnderPearlEntity){
            this.scale = instance.pearlScale;
            this.lit = instance.lit;
            if(entity.age <= 2 && !notified.contains(entity ) && instance.playSound && player != null){
                Identifier soundId = new Identifier(SoundEvents.ENTITY_ENDERMAN_TELEPORT.getId().toString());
                player.playSound(SoundEvent.of(soundId), 1, 1);
                notified.add(entity);
            }
        } else{
            this.scale = 1;
        }
    }
}
