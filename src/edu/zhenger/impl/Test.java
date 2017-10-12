/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

import edu.zhenger.util.Change;
import gov.nasa.worldwind.geom.*;

/**
 * Author: WangZheng Email: tbpwang@gmail.com Function:
 * <p>
 * Date: 2017/10/11
 */
public class Test
{
    public static void main(String[] args)
    {
        double r = Change.getInstance().getGlobe().getRadius();
        LatLon latLonA = LatLon.fromDegrees(90,0);
        LatLon latLonB = LatLon.fromDegrees(0,0);
        LatLon latLonC = LatLon.fromDegrees(0,90);
        LatLon latLonD = LatLon.fromDegrees(0,180);
        LatLon latLonE = LatLon.fromDegrees(0,-90);
        System.out.println(Change.getInstance().fromLatLon(latLonA));
        System.out.println(Change.getInstance().fromLatLon(latLonB));
        System.out.println(Change.getInstance().fromLatLon(latLonC));
        System.out.println(Change.getInstance().fromLatLon(latLonD));
        System.out.println(Change.getInstance().fromLatLon(latLonE));
//        Vec4 a = new Vec4(0,0,r);
//        Vec4 b = new Vec4(r,0,0);
//        Vec4 c = new Vec4(0,r,0);
//        Vec4 d = new Vec4(-r,0,0);
//        Vec4 e = new Vec4(0,-r,0);
        Vec4 vec4A = new Vec4(0,r,0);//(90,0)
        Vec4 vec4B = new Vec4(0,0,r);//(0,0)
        Vec4 vec4C = new Vec4(r,0,0);//(0,90)
        Vec4 vec4D = new Vec4(0,0,-r);//(0,180)
        Vec4 vec4E = new Vec4(-r,0,0);//(0,-90)

        System.out.println(Change.getInstance().fromVec4(vec4A));
        System.out.println(Change.getInstance().fromVec4(vec4B));
        System.out.println(Change.getInstance().fromVec4(vec4C));
        System.out.println(Change.getInstance().fromVec4(vec4D));
        System.out.println(Change.getInstance().fromVec4(vec4E));
    }
}
