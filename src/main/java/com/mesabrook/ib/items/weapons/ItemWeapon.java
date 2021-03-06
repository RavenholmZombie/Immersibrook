package com.mesabrook.ib.items.weapons;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.config.ModConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemWeapon extends ItemSword implements IHasModel
{
	private final TextComponentTranslation emerald = new TextComponentTranslation("im.cliche");
	public ItemWeapon(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);

		emerald.getStyle().setItalic(true);
		emerald.getStyle().setColor(TextFormatting.GREEN);

		ModItems.ITEMS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(ModConfig.funnyTooltips)
		{
			if(this.getUnlocalizedName().contains("sod"))
			{
				tooltip.add(TextFormatting.RED + "noo you can't just break a sword in half and call it a sod!");
				tooltip.add(TextFormatting.AQUA + "hehe sod go swish slash");
			}
		}

		if(stack.getItem() == ModItems.EMERALD_SWORD)
		{
			tooltip.add(emerald.getFormattedText());
		}
		super.addInformation(stack, world, tooltip, flag);
	}

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0);
	}
}
