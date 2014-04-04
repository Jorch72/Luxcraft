package com.rwtema.luxcraft.tiles;

import com.rwtema.luxcraft.luxapi.ILuxContainer;
import com.rwtema.luxcraft.luxapi.ILuxLaserDivertor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class TileEntityLuxInserter extends TileEntity implements ILuxLaserDivertor {

    List<Pos> pos = new ArrayList<Pos>();

    public boolean canUpdate() {
        return false;
    }


    @Override
    public List<Pos> getAlternatePositions() {
        Pos start = new Pos(xCoord, yCoord, zCoord);
        pos.clear();
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            Pos p = start.copy().advance(dir);
            if (worldObj.getTileEntity(p.x, p.y, p.z) instanceof ILuxContainer)
                pos.add(p);
        }

        return pos;
    }
}
