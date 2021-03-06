package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockColumn extends Block implements IHasModel
{
    public BlockColumn(String name, Material material, SoundType soundType, String harvestTool, int harvestLevel, float lightLevel)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.IMMERSIBROOK_MAIN);
        setSoundType(soundType);
        setHarvestLevel(harvestTool, harvestLevel);
        setHardness(8.0F);
        setResistance(8.0F);
        setLightLevel(lightLevel);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()).setMaxStackSize(64));
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
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
    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Override
    public float getAmbientOcclusionLightValue(IBlockState state)
    {
        return 1;
    }
}
