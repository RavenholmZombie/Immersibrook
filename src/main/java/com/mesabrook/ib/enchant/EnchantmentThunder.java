package com.mesabrook.ib.enchant;

import com.mesabrook.ib.init.ModEnchants;
import com.mesabrook.ib.util.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentThunder extends Enchantment
{
	public EnchantmentThunder()
	{
		super(Rarity.UNCOMMON, ModEnchants.WEAPONS, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
		setRegistryName("thunder");
		setName("thunder");
	}
	
	@Override
    public int getMaxLevel()
    {
        return 1;
    }
	
	@Override
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
	{
		if(ModConfig.thorCausesFires)
		{
			user.world.addWeatherEffect(new EntityLightningBolt(user.world, target.posX, target.posY, target.posZ, false));
		}
		else
		{
			user.world.addWeatherEffect(new EntityLightningBolt(user.world, target.posX, target.posY, target.posZ, true));
		}
	}
}
