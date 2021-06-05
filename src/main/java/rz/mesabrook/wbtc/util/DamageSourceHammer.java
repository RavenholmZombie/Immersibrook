package rz.mesabrook.wbtc.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourceHammer extends DamageSource
{
    public DamageSourceHammer(String damageSourceIn)
    {
        super(damageSourceIn);
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
    {
        if(SoundRandomizer.hammerResult == "beaned")
        {
            return new TextComponentTranslation("im.death.beaned", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "bong")
        {
            return new TextComponentTranslation("im.death.bong", entityLivingBaseIn.getDisplayName());
        }
        else if(SoundRandomizer.hammerResult == "bonk")
        {
            return new TextComponentTranslation("im.death.bonk", entityLivingBaseIn.getDisplayName());
        }
        return new TextComponentTranslation("im.death.default", entityLivingBaseIn.getDisplayName());
    }
}
