package dev.hooman.feature;

import dev.hooman.config.CrystalUtilsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

public class AntiLootBlowUp {
    public static boolean isCrystal(Entity entity) {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.player == null || mc.world == null || entity == null || entity.isRemoved()) return false;

        if (mc.player.getMainHandStack().getItem() instanceof ToolItem || mc.player.getOffHandStack().getItem() instanceof ToolItem)
            return entity.getType().equals(EntityType.END_CRYSTAL);

        return entity.getType().equals(EntityType.END_CRYSTAL) || entity.getType().equals(EntityType.SLIME) || entity.getType().equals(EntityType.MAGMA_CUBE);
    }

    public static boolean cannotExplode() {
        if(MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().player != null) {
            switch (CrystalUtilsConfig.instance().detectionMode) {
                case Dead:
                    return MinecraftClient.getInstance().world.getPlayers().stream()
                            .filter(e -> MinecraftClient.getInstance().player.squaredDistanceTo(e) <= CrystalUtilsConfig.instance().detectionRadius * CrystalUtilsConfig.instance().detectionRadius)
                            .anyMatch(LivingEntity::isDead);
                case Item:
                    for (ItemEntity e : MinecraftClient.getInstance().world.getEntitiesByType(EntityType.ITEM, MinecraftClient.getInstance().player.getBoundingBox().expand(CrystalUtilsConfig.instance().detectionRadius), entity -> true)) {
                        ItemStack stack = e.getStack();
                        if (cannotExplodeItem(stack)) {
                            return true;
                        }
                    }
            }
        }
        return false;
    }
    private static boolean matchesItemType(CrystalUtilsConfig.ItemType itemType, boolean armor, boolean tool) {
        return switch (itemType) {
            case Armor -> armor;
            case Tool -> tool;
            case Both -> armor || tool;
        };
    }

    public static boolean cannotExplodeItem(ItemStack i) {
        boolean diamond = i.getItem().getName().toString().toLowerCase().contains("diamond");
        boolean neth = i.getItem().getName().toString().toLowerCase().contains("netherite");
        boolean armor = i.getItem() instanceof ArmorItem;
        boolean tool = i.getItem() instanceof ToolItem;
        CrystalUtilsConfig configInstance = CrystalUtilsConfig.instance();
        CrystalUtilsConfig.Material material = configInstance.material;
        CrystalUtilsConfig.ItemType itemType = configInstance.itemType;

        return switch (material) {
            case Diamond -> diamond && matchesItemType(itemType, armor, tool);
            case Netherite -> neth && matchesItemType(itemType, armor, tool);
            case Both -> (diamond || neth) && matchesItemType(itemType, armor, tool);
        };
    }
}
