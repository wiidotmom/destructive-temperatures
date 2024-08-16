package dev.igalaxy.destructivetemperatures.tempmodifiers;

import com.momosoftworks.coldsweat.api.temperature.modifier.TempModifier;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.api.util.Temperature.Units;
import com.petrolpark.destroy.capability.level.pollution.LevelPollution.PollutionType;
import com.petrolpark.destroy.util.PollutionHelper;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class PollutionTempModifier extends TempModifier {
    @Override
    protected Function<Double, Double> calculate(LivingEntity livingEntity, Temperature.Trait trait) {
        if (!livingEntity.level().isClientSide) {
            float ozoneDepletion = (float) PollutionHelper.getPollution(livingEntity.level(), PollutionType.OZONE_DEPLETION) / PollutionType.OZONE_DEPLETION.max;
            float greenhouse = (float) PollutionHelper.getPollution(livingEntity.level(), PollutionType.GREENHOUSE) / PollutionType.GREENHOUSE.max;

            // Uses same scale as Destroy
            // https://github.com/petrolpark/Destroy/blob/816100216a97950e21a731ccad3ef96ffe79ed4e/src/main/java/com/petrolpark/destroy/capability/level/pollution/LevelPollution.java#L137
            return temp -> temp + Temperature.convert(greenhouse * 20D, Units.C, Units.MC, false) + Temperature.convert(ozoneDepletion * 4D, Units.C, Units.MC, false);
        }
        return temp -> temp;
    }
}
