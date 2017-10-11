/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger;

import gov.nasa.worldwind.geom.LatLon;

import java.io.Serializable;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function:
 * @Date: 2017/10/10
 */
public interface Cell extends Serializable
{
    LatLon[] getVertex();

    //String getID();
    String getGeocode();

    LatLon getRefPoint();
}
