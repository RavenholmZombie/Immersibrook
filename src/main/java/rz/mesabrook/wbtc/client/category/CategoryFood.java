package rz.mesabrook.wbtc.client.category;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rz.mesabrook.wbtc.init.ModBlocks;

public class CategoryFood extends AbstractCategory 
{
	
	public CategoryFood()
	{
		super("im.filter.food", new ItemStack(ModBlocks.CUBE_APPLES));
	}

	@Override
	public void init() 
	{
		add(ModBlocks.CUBE_PORK);
		add(ModBlocks.CUBE_BEEF);
		add(ModBlocks.CUBE_CHICKEN);
		add(ModBlocks.CUBE_MUTTON);
		add(ModBlocks.CUBE_RABBIT);
		add(ModBlocks.CUBE_APPLES);
		add(ModBlocks.CUBE_CHEESE);
		add(ModBlocks.CUBE_CARROT);
		add(ModBlocks.CUBE_POTATO);
		add(ModBlocks.CUBE_PUMPKIN_PIE);
		add(ModBlocks.CUBE_BEET);
		add(ModBlocks.CUBE_COOKIE);
		add(ModBlocks.CUBE_GAPPLE);
		add(ModBlocks.CUBE_BREAD);
		add(ModBlocks.CUBE_FISH);
		add(ModBlocks.CUBE_SALMON);
		add(ModBlocks.CUBE_PUFF);
		add(ModBlocks.CUBE_CLOWN);
		add(ModBlocks.CUBE_APPLE_PIE);
		add(ModBlocks.CUBE_STRAWBERRY_PIE);
		add(ModBlocks.CUBE_BLUEBERRY_PIE);
		add(ModBlocks.CUBE_CHERRY_PIE);
		add(ModBlocks.CUBE_SWEETPOTATO_PIE);
		add(ModBlocks.CUBE_KEYLIME_PIE);
		add(ModBlocks.CUBE_RASPBERRY_PIE);
		add(ModBlocks.CUBE_PECAN_PIE);
		add(ModBlocks.CUBE_GOOSEBERRY_PIE);
		add(ModBlocks.CUBE_SPIDER_PIE);
		add(ModBlocks.CUBE_RHUBARB_PIE);
		add(ModBlocks.CUBE_PATREON_PIE);
		add(ModBlocks.CUBE_SLIME_PIE);
		add(ModBlocks.CUBE_MEAT_PIE);
		add(ModBlocks.CUBE_SPINACH_PIE);
		add(ModBlocks.CUBE_MINCE_PIE);
		add(ModBlocks.CUBE_COTTAGE_PIE);
		add(ModBlocks.CUBE_SHEPHERD_PIE);
	}
}
