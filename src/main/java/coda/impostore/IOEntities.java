package coda.impostore;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IOEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, ImpostOre.MOD_ID);

    public static final RegistryObject<EntityType<ImpostOreEntity>> IMPOST_ORE = create("impost_ore", EntityType.Builder.<ImpostOreEntity>of(ImpostOreEntity::new, MobCategory.MONSTER).sized(1.0f, 1.0f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(ImpostOre.MOD_ID + "." + name));
    }
}