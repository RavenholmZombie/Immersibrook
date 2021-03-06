package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.saveData.AntennaData;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCellAntenna extends Block implements IHasModel {

	public BlockCellAntenna(String name)
	{
		super(Material.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.METAL);
		setHardness(8.0F);
		setResistance(8.0F);
		setCreativeTab(Main.IMMERSIBROOK_MAIN);
		setHarvestLevel("pickaxe", 0);
		setTickRandomly(true);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(ItemBlock.getItemFromBlock(this), 0);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		checkHeight(worldIn, pos);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		AntennaData.getOrCreate(worldIn).removeHeight(pos);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		super.randomTick(worldIn, pos, state, random);
		checkHeight(worldIn, pos);
	}
	
	private void checkHeight(World world, BlockPos currentPosition)
	{
		if (world.isRemote)
		{
			return;
		}
		
		int startX = currentPosition.getX() - (ModConfig.cellAntennaHeightScanWidth / 2);
		int startZ = currentPosition.getZ() - (ModConfig.cellAntennaHeightScanWidth / 2);
		int heightTotal = 0;
		
		for(int x = startX; x < (startX + ModConfig.cellAntennaHeightScanWidth); x++)
		{
			for (int z = startZ; z < (startZ + ModConfig.cellAntennaHeightScanWidth); z++)
			{
				boolean encounteredGround = false;
				for(int y = currentPosition.getY() - 1; y > (currentPosition.getY() - ModConfig.cellAntennaOptimalHeight); y--)
				{
					BlockPos pos = new BlockPos(x, y, z);
					IBlockState state = world.getBlockState(pos);
					if (state.isFullCube())
					{
						encounteredGround = true;
						heightTotal += currentPosition.getY() - y - 1;
						break;
					}
				}
				
				if (!encounteredGround)
				{
					heightTotal += ModConfig.cellAntennaOptimalHeight;
				}
			}
		}
		
		int effectiveHeight = heightTotal / (int)Math.pow(ModConfig.cellAntennaHeightScanWidth, 2);
		AntennaData.getOrCreate(world).setHeight(currentPosition, effectiveHeight);
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
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean causesSuffocation(IBlockState state)
	{
		return false;
	}

	@Override
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1;
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
