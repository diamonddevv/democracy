package net.diamonddev.democracy.fuckmojmaps;

import net.minecraft.resources.ResourceLocation;

public class Identifier extends ResourceLocation {
    public Identifier(String string, String string2) {
        super(string, string2);
    }

    public ResourceLocation toResLoc() {
        return new ResourceLocation(getNamespace(), getPath());
    }
}
