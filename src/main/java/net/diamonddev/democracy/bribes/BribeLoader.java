package net.diamonddev.democracy.bribes;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.diamonddev.democracy.Democracy;
import net.diamonddev.democracy.fuckmojmaps.Identifier;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BribeLoader extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {

    public static final Identifier ID = Democracy.id("bribes");
    public static List<Bribe> bribes = new ArrayList<>();

    public BribeLoader() {
        super(new Gson(), "bribes");
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        bribes.clear();
        int successful = 0;
        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            try {
                List<Bribe> localBribes = Bribe.fromJson(entry.getValue().getAsJsonObject());
                bribes.addAll(localBribes);
                successful++;
            }
            catch (Exception e) {
                Democracy.LOGGER.error("Exception while loading bribe '" + entry.getKey() + "': ", e);
            }
        }
        if (successful > 0) {
            Democracy.LOGGER.info("Loaded " + successful + " bribe files with " + bribes.size() + " bribes");
        }
    }
}
