package dev.igalaxy.destructivetemperatures;

import com.momosoftworks.coldsweat.api.event.core.GatherDefaultTempModifiersEvent;
import com.momosoftworks.coldsweat.api.event.core.TempModifierRegisterEvent;
import com.momosoftworks.coldsweat.api.temperature.modifier.UndergroundTempModifier;
import com.momosoftworks.coldsweat.api.util.Placement;
import com.momosoftworks.coldsweat.api.util.Temperature;
import dev.igalaxy.destructivetemperatures.tempmodifiers.PollutionTempModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DestructiveTemperatures.MODID)
public class DestructiveTemperatures {

  public static final String MODID = "destructivetemperatures";
  public static final Logger LOGGER = LogManager.getLogger();

  public DestructiveTemperatures() {
    MinecraftForge.EVENT_BUS.register(DestructiveTemperatures.class);
  }

  @SubscribeEvent
  public static void onModifiersRegister(TempModifierRegisterEvent event)
  {
    LOGGER.info("Registered pollution temp modifier");
    event.register(new ResourceLocation(MODID, "pollution"), () -> new PollutionTempModifier());
  }

  @SubscribeEvent
  public static void defaultModifiersInit(GatherDefaultTempModifiersEvent event)
  {
    if (event.getEntity() instanceof Player && event.getTrait() == Temperature.Trait.WORLD)
    {
      LOGGER.info("Initialized pollution temp modifier for " + ((Player) event.getEntity()).getName().getString());
      event.addModifier(new PollutionTempModifier().tickRate(60), Placement.Duplicates.BY_CLASS, Placement.of(Placement.Mode.BEFORE, Placement.Order.FIRST, (mod2) -> mod2 instanceof UndergroundTempModifier));
    }
  }
}
