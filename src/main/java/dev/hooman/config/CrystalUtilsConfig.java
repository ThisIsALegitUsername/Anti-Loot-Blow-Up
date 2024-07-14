package dev.hooman.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.nio.file.Path;

public class CrystalUtilsConfig {
    private static final Path CONFIG_PATH = Path.of("config/crystalutils.json");

    public static final ConfigClassHandler<CrystalUtilsConfig> HANDLER = ConfigClassHandler.createBuilder(CrystalUtilsConfig.class)
            .id(new Identifier("crystal-utilities", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(CONFIG_PATH)
                    //.setJson5(true) // Uncomment this line to use JSON5 instead of JSON.
                    .build())
            .build();

    public static void load(){
        HANDLER.load();
    }

    public static void save(){
        HANDLER.save();
    }

    public static CrystalUtilsConfig instance(){
        return HANDLER.instance();
    }

    @SerialEntry
    public boolean antiLootBlowupEnabled = false;

    @SerialEntry
    public DetectionMode detectionMode = DetectionMode.Item;

    @SerialEntry
    public ItemType itemType = ItemType.Armor;

    @SerialEntry
    public Material material = Material.Both;

    @SerialEntry
    public Integer detectionRadius = 6;

    public enum DetectionMode {
        Dead,
        Item
    }

    public enum ItemType {
        Armor,
        Tool,
        Both
    }

    public enum Material {
        Diamond,
        Netherite,
        Both
    }

    public Screen create(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("CrystalUtilities CrystalUtilsConfig"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("General"))
                        .tooltip(Text.of("General settings"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Anti Loot Blowup"))
                                .description(OptionDescription.of(Text.of("Prevents you from blowing up your hard-earned loot")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.of("Enabled"))
                                        .description(OptionDescription.of(Text.of("Whether or not this feature should be enabled")))
                                        .binding(false, () -> CrystalUtilsConfig.instance().antiLootBlowupEnabled, (v) -> CrystalUtilsConfig.instance().antiLootBlowupEnabled = v)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true))
                                        .build()
                                )
                                .option(Option.<DetectionMode>createBuilder()
                                        .name(Text.of("Detection mode"))
                                        .description(OptionDescription.of(Text.of("How the mod decides there is loot nearby")))
                                        .binding(DetectionMode.Item, () -> CrystalUtilsConfig.instance().detectionMode, (v) -> CrystalUtilsConfig.instance().detectionMode = v)
                                        .controller(opt -> EnumControllerBuilder.create(opt).enumClass(DetectionMode.class))
                                        .build())
                                .optionIf(CrystalUtilsConfig.instance().detectionMode == DetectionMode.Item,
                                        Option.<ItemType>createBuilder()
                                        .name(Text.of("Item type"))
                                        .description(OptionDescription.of(Text.of("What type of item the mod should look for")))
                                        .binding(ItemType.Armor, () -> CrystalUtilsConfig.instance().itemType, (v) -> CrystalUtilsConfig.instance().itemType = v)
                                        .controller(opt -> EnumControllerBuilder.create(opt).enumClass(ItemType.class))
                                        .build())
                                .optionIf(CrystalUtilsConfig.instance().detectionMode == DetectionMode.Item,
                                        Option.<Material>createBuilder()
                                        .name(Text.of("Material"))
                                        .description(OptionDescription.of(Text.of("What kinds of materials the mod should look for")))
                                        .binding(Material.Both, () -> CrystalUtilsConfig.instance().material, (v) -> CrystalUtilsConfig.instance().material = v)
                                        .controller(opt -> EnumControllerBuilder.create(opt).enumClass(Material.class))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.of("Detection radius"))
                                        .description(OptionDescription.of(Text.of("How far the mod should look when detecting loot nearby")))
                                        .binding(6, () -> CrystalUtilsConfig.instance().detectionRadius, (v) -> CrystalUtilsConfig.instance().detectionRadius = v)
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 32).step(1).formatValue(i -> Text.of(i + " Blocks")))
                                        .build())
                                .build())
                        .build())
                .save(CrystalUtilsConfig::save)
                .build()
                .generateScreen(parent);
    }
}
