package com.mesabrook.ib.blocks;

import com.mesabrook.ib.Main;
import com.mesabrook.ib.init.ModBlocks;
import com.mesabrook.ib.init.ModItems;
import com.mesabrook.ib.util.IHasModel;
import com.mesabrook.ib.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockLargePlaque extends Block implements IHasModel
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected final ArrayList<AxisAlignedBB> AABBs;

    public BlockLargePlaque(String name, MapColor color, AxisAlignedBB unrotatedAABB)
    {
        super(Material.WOOD, color);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHarvestLevel("pickaxe", 1);
        setHardness(1.0F);
        setResistance(3.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        AABBs = new ArrayList<AxisAlignedBB>(Arrays.asList(
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.DOWN, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.UP, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.NORTH, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.SOUTH, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.WEST, false),
                ModUtils.getRotatedAABB(unrotatedAABB, EnumFacing.EAST, false),
                unrotatedAABB, unrotatedAABB // Array fill to ensure that the array size covers 4 bit (meta & 0x07).
        ));

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABBs.get(((EnumFacing)state.getValue(FACING)).getIndex() & 0x7);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return side != EnumFacing.DOWN && side != EnumFacing.UP && this.canAttachTo(worldIn, pos, side);
    }

    public boolean canAttachTo(World worldIn, BlockPos pos, EnumFacing facing)
    {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return this.isAcceptableNeighbor(worldIn, pos.offset(facing.getOpposite()), facing) && (block == Blocks.AIR || block == Blocks.VINE || this.isAcceptableNeighbor(worldIn, pos.up(), EnumFacing.UP));
    }

    private boolean isAcceptableNeighbor(World worldIn, BlockPos pos, EnumFacing facing)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        return iblockstate.getBlockFaceShape(worldIn, pos, facing) == BlockFaceShape.SOLID;
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

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void registerModels()
    {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
    }
}
