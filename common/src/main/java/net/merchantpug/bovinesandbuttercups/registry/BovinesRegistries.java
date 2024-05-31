package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowTypeType;
import net.minecraft.core.Registry;

public class BovinesRegistries {
    public static final Registry<CowTypeType<?>> COW_TYPE_TYPE = BovinesAndButtercups.getHelper().createRegistry(BovinesRegistryKeys.COW_TYPE_TYPE);

    public static void init() {
    }

}
