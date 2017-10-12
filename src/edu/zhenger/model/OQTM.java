/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.*;
import gov.nasa.worldwind.globes.Globe;

/**
 * @Author: WangZheng
 * @Email: tbpwang@gmail.com
 * @Function: OQTM is the method of subdivision,
 *     and comprises operations in the subdivision
 * @Date: 2017/10/11
 */
public class OQTM implements QTM
{
    private static OQTM ourInstance = new OQTM();

    public static OQTM getInstance()
    {
        return ourInstance;
    }

    private OQTM()
    {
    }
    @Override
    public Globe getGlobe()
    {
        return Sphere.getInstance();
    }

    @Override
    public void analyze(Cell cell)
    {
        
    }

    @Override
    public void subdivide(Cell cell)
    {

    }
}
