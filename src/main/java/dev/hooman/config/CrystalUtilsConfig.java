package dev.hooman.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
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

    public static CrystalUtilsConfig getInstance(){
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

    @SerialEntry
    public boolean pearlSize = true;

    @SerialEntry
    public Float pearlScale = 2.0f;

    @SerialEntry
    public boolean playSound = true;

    @SerialEntry
    public boolean lit = true;

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

    public Screen create(Screen parent, CrystalUtilsConfig instance) {
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
                                        .binding(false, () -> instance.antiLootBlowupEnabled, (v) -> instance.antiLootBlowupEnabled = v)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true))
                                        .build()
                                )
                                .option(Option.<DetectionMode>createBuilder()
                                        .name(Text.of("Detection mode"))
                                        .description(OptionDescription.of(Text.of("How the mod decides there is loot nearby")))
                                        .binding(DetectionMode.Item, () -> instance.detectionMode, (v) -> instance.detectionMode = v)
                                        .controller(opt -> EnumControllerBuilder.create(opt).enumClass(DetectionMode.class))
                                        .build())
                                .option(Option.<ItemType>createBuilder()
                                        .name(Text.of("Item type"))
                                        .description(OptionDescription.of(Text.of("What type of item the mod should look for")))
                                        .binding(ItemType.Armor, () -> instance.itemType, (v) -> instance.itemType = v)
                                        .controller(opt -> EnumControllerBuilder.create(opt).enumClass(ItemType.class))
                                        .build())
                                .option(Option.<Material>createBuilder()
                                        .name(Text.of("Material"))
                                        .description(OptionDescription.of(Text.of("What kinds of materials the mod should look for")))
                                        .binding(Material.Both, () -> instance.material, (v) -> instance.material = v)
                                        .controller(opt -> EnumControllerBuilder.create(opt).enumClass(Material.class))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.of("Detection radius"))
                                        .description(OptionDescription.of(Text.of("How far the mod should look when detecting loot nearby")))
                                        .binding(6, () -> instance.detectionRadius, (v) -> instance.detectionRadius = v)
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 32).step(1).formatValue(i -> Text.of(i + " Blocks")))
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("Render"))
                        .tooltip(Text.of("Rendering settings"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Pearls"))
                                .description(OptionDescription.of(Text.of("Ender pearl settings")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.of("Enabled"))
                                        .description(OptionDescription.of(Text.of("Whether or not this feature should be enabled")))
                                        .binding(true, () -> instance.pearlSize, (v) -> instance.pearlSize = v)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Text.of("Scale"))
                                        .description(OptionDescription.of(Text.of("How large the pearls should be")))
                                        .binding(2.0f, () -> instance.pearlScale, (v) -> instance.pearlScale = v)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.1f, 5.0f).step(0.1f).formatValue(f -> Text.of(f + "x")))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.of("Play sound"))
                                        .description(OptionDescription.of(Text.of("Whether or not to play a sound when a pearl is thrown")))
                                        .binding(true, () -> instance.playSound, (v) -> instance.playSound = v)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.of("Lit"))
                                        .description(OptionDescription.of(Text.of("Whether or not the pearls should be lit")))
                                        .binding(true, () -> instance.lit, (v) -> instance.lit = v)
                                        .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true))
                                        .build())
                                .build())
                        .build())
                .save(CrystalUtilsConfig::save)
                .build()
                .generateScreen(parent);
    }
}
