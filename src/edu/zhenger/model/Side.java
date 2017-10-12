/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Path;

import java.util.Arrays;

/**
 * Author: WangZheng Email: tbpwang@gmail.com Function: Side represents the side of the spherical polygon Date:
 * 2017/10/11
 */
public class Side extends Path
{
    private String defaultPathType = AVKey.GREAT_CIRCLE;

    private Trigon trigon;

    private String geocode;

    public Side(Trigon trigon)
    {
        super((Position.PositionList) Arrays.asList(trigon.getVertex()));
        this.geocode = trigon.getGeocode();
        this.trigon = trigon;
        this.setPathType(defaultPathType);
    }

    public Side(Trigon trigon, String pathType)
    {
        super((Position.PositionList) Arrays.asList(trigon.getVertex()));
        this.geocode = trigon.getGeocode();
        this.trigon = trigon;
        this.setPathType(pathType);
    }

    public Trigon getTrigon()
    {
        return trigon;
    }

    public String getGeocode()
    {
        return geocode;
    }
}
