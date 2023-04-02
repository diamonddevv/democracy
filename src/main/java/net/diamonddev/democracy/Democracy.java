package net.diamonddev.democracy;

import net.diamonddev.democracy.bribes.BribeLoader;
import net.diamonddev.democracy.fuckmojmaps.Identifier;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Democracy implements ModInitializer {

	public static final String modid = "democracy";
	public static final Logger LOGGER = LoggerFactory.getLogger("democracy");

	public static boolean isCustomRulesInitialized = false;

	@Override
	public void onInitialize() {
		long start = System.currentTimeMillis();
		//

		LOGGER.info("In the wise words of Ben Kenobi, DEMOCRACY");
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new BribeLoader());

		//
		long initTime = System.currentTimeMillis() - start;
		LOGGER.info("Mod " + modid + " initialized in " + initTime + " millisecond(s)!");
	}

	public static Identifier id(String path) {
		return new Identifier(modid, path);
	}
}
