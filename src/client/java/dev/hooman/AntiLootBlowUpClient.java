package dev.hooman;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ToolItem;

public class AntiLootBlowUpClient implements ClientModInitializer {
	//public static boolean cannotDestroy = false;
	//private long cooldown;

	@Override
	public void onInitializeClient() {

		/*ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (cannotDestroy && System.currentTimeMillis() - cooldown >= 2000) {
				cannotDestroy = false;
			}
		});

		ServerLivingEntityEvents.AFTER_DEATH.register( (entity, source) -> {
			if(entity instanceof PlayerEntity) {
				if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.distanceTo(entity) < 6) {
					cannotDestroy = true;
					cooldown = System.currentTimeMillis();
				}
			}
		});*/
	}

	public static boolean isCrystal(Entity entity) {
		MinecraftClient mc = MinecraftClient.getInstance();
		
		if (mc.player == null || mc.world == null || entity == null || entity.isRemoved()) return false;

		if (mc.player.getMainHandStack().getItem() instanceof ToolItem || mc.player.getOffHandStack().getItem() instanceof ToolItem)
			return entity.getType().equals(EntityType.END_CRYSTAL);

		return entity.getType().equals(EntityType.END_CRYSTAL) || entity.getType().equals(EntityType.SLIME) || entity.getType().equals(EntityType.MAGMA_CUBE);
	}

	public static boolean cannotExplode() {
		if(MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().player != null) {
			return MinecraftClient.getInstance().world.getPlayers().stream()
				.filter(e -> MinecraftClient.getInstance().player.squaredDistanceTo(e) <= 36)
				.anyMatch(LivingEntity::isDead);
		}
		return false;
	}
}
