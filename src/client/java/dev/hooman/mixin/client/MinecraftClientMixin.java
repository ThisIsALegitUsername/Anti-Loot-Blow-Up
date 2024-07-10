package dev.hooman.mixin.client;

import dev.hooman.AntiLootBlowUpClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    public HitResult crosshairTarget;

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    public void dontAttackCrystal(CallbackInfoReturnable<Boolean> cir) {
        if(crosshairTarget == null) {
            LoggerFactory.getLogger("anti-loot-blow-up").error("crosshairTarget is null; this shouldn't happen!");
        }

        if(crosshairTarget.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult)crosshairTarget).getEntity();
            if(AntiLootBlowUpClient.isCrystal(entity) && AntiLootBlowUpClient.cannotDestroy) {
                cir.setReturnValue(false);
            }
        }
    }
}
