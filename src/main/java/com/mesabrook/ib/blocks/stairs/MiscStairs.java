package com.mesabrook.ib.blocks.stairs;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class MiscStairs extends BlockStairs implements IHasModel
{
	public MiscStairs(String name, IBlockState modelState, SoundType snd, String harvestTool, int harvestLevel)
	{
		super(modelState);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(1.8F);
		setResistance(1.8F);
		setSoundType(snd);
		setHarvestLevel(harvestTool, harvestLevel);
		
		this.useNeighborBrightness = true;
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }
	
	@Override
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1;
	}
	

	
	@Override
    protected boolean canSilkHarvest()
    {
        return true;
    }
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}
}
