/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.util;

import edu.zhenger.DGGS;
import edu.zhenger.model.Sphere;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 */
public final class Change implements DGGS
{
    private static Change ourInstance = new Change();

    public static Change getInstance()
    {
        return ourInstance;
    }

    private Change()
    {
    }

    @Override
    public Globe getGlobe()
    {
        return Sphere.getInstance();
    }

    public Vec4 fromLatLon(LatLon latLon)
    {
        //return getGlobe().computePointFromLocation(latLon);
        double epsilon = 1e-8;
        Vec4 vec4 = getGlobe().computePointFromLocation(latLon);
        double x, y, z;
        x = Math.abs(vec4.x) <= epsilon ? 0.0 : vec4.x;
        y = Math.abs(vec4.y) <= epsilon ? 0.0 : vec4.y;
        z = Math.abs(vec4.z) <= epsilon ? 0.0 : vec4.z;

        return new Vec4(x, y, z);
    }

    public LatLon fromVec4(Vec4 vec4)
    {
        return getGlobe().computePositionFromPoint(vec4);
    }
}
