package com.mesabrook.ib.blocks.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFoodBox extends TileEntity
{
    private String company = "";
    private String boxID = "";
    private int uses;

    public TileEntityFoodBox()
    {
        super();
    }

    public TileEntityFoodBox(String boxID)
    {
        super();
        this.boxID = boxID;
        this.uses = 9;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        boxID = compound.getString("boxID");
        company = compound.getString("company");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setString("boxID", boxID);
        compound.setString("company", company);
        return super.writeToNBT(compound);
    }

    public int getUses() {return uses;}
    public void setUses(int uses)
    {
        this.uses = uses;
        markDirty();
    }

    public String getBoxID()
    {
        return boxID;
    }

    public void setBoxID(String boxID)
    {
        this.boxID = boxID;
        markDirty();
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
        markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = super.getUpdateTag();
        tag.setString("boxID", getBoxID());
        tag.setString("company", getCompany());
        return tag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);
        this.boxID = tag.getString("boxID");
        this.company = tag.getString("company");
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("boxID", getBoxID());
        tag.setString("company", getCompany());
        return new SPacketUpdateTileEntity(getPos(), 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.boxID = pkt.getNbtCompound().getString("boxID");
        this.company = pkt.getNbtCompound().getString("company");
        super.onDataPacket(net, pkt);
    }
}
