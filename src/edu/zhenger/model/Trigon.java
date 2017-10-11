/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.model;

import edu.zhenger.Cell;
import edu.zhenger.util.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.globes.Globe;

/**
 * Author: WangZheng Email: tbpwang@gmail.com Function: The model of Triangular subdivision is a basic unit in Sphere
 * Date: 2017/10/10
 */
public class Trigon implements Cell, MeasurableArea, MeasurableLength
{
    private LatLon top, left, right, center;
    private String geocode;
    private Globe globe;

    public Trigon(LatLon top, LatLon left, LatLon right, String geocode)
    {
        if (top == null || left == null || right == null)
        {
            throw new Mistake("nullValue: VertexIsNull");
        }

        this.top = new LatLon(top);
        this.left = new LatLon(left);
        this.right = new LatLon(right);
        this.geocode = geocode;
        Vec4 a, b, c, d;
        a = Turn.getInstance().fromLatLon(top);
        b = Turn.getInstance().fromLatLon(left);
        c = Turn.getInstance().fromLatLon(right);
        d = new Vec4((a.x + b.x + c.x) / 3, (a.y + b.y + c.y) / 3, (a.z + b.z + c.z) / 3);

        this.center = new LatLon(Turn.getInstance().fromVec4(d));
        this.globe = Turn.getInstance().getGlobe();

        a = null;
        b = null;
        c = null;
        d = null;
    }

    public double getArea()
    {
        return getArea(globe);
    }

    public double getPerimeter()
    {
        return getPerimeter(globe);
    }

    @Override
    public LatLon[] getVertex()
    {
        return new LatLon[] {top, left, right};
    }

    @Override
    public String getGeocode()
    {
        return geocode;
    }

    @Override
    public LatLon getRefPoint()
    {
        return center;
    }

    @Override
    public double getArea(Globe globe)
    {
        // getSphereArea
        //已知三边abc求面积
        //公式来源：一般球面三角形计算公式
        //数学手册编写组，数学手册，北京：高等教育出版社，2010年印，p49-50.
        //公式半角: sin(A/2)=sqrt(sin(p-b)*sin(p-c)/(sin(b)*sin(c)))
        // S = (A+B+C-PI)*R^2
        double a, b, c, p;
        double A, B, C;

        a = LatLon.greatCircleDistance(left, right).getRadians();
        b = LatLon.greatCircleDistance(right, top).getRadians();
        c = LatLon.greatCircleDistance(top, left).getRadians();
        // half-side of triangle
        p = (a + b + c) / 2;

        A = 2 * Math.asin(Math.sqrt(Math.sin(p - b) * Math.sin(p - c) / (Math.sin(b) * Math.sin(c))));
        B = 2 * Math.asin(Math.sqrt(Math.sin(p - c) * Math.sin(p - a) / (Math.sin(c) * Math.sin(a))));
        C = 2 * Math.asin(Math.sqrt(Math.sin(p - a) * Math.sin(p - b) / (Math.sin(a) * Math.sin(b))));

        return globe.getRadius() * globe.getRadius() * (A + B + C - Math.PI);
    }

    @Override
    public double getPerimeter(Globe globe)
    {
        double a, b, c;
        a = LatLon.greatCircleDistance(left, right).getRadians();
        b = LatLon.greatCircleDistance(right, top).getRadians();
        c = LatLon.greatCircleDistance(top, left).getRadians();
        return globe.getRadius() * (a + b + c);
    }

    @Override
    public double getWidth(Globe globe)
    {
        // output min side as width
        double a, b, c, min;
        a = LatLon.greatCircleDistance(left, right).getRadians();
        b = LatLon.greatCircleDistance(right, top).getRadians();
        c = LatLon.greatCircleDistance(top, left).getRadians();
        min = (a < b ? a : b) < c ? (a < b ? a : b) : c;
        return globe.getRadius() * min;
    }

    @Override
    public double getHeight(Globe globe)
    {
        // output the distance of great circle,
        // from top vertex along longitude to opposite side
        double lat = Angle.average(left.getLatitude(), right.getLatitude()).getRadians();
        double lon = Math.abs(top.getLatitude().getDegrees()) == 90 ? Angle.average(left.getLongitude(),
            right.getLongitude()).getRadians() : top.getLongitude().getRadians();
        LatLon temp = LatLon.fromRadians(lat, lon);

        return LatLon.greatCircleDistance(top, temp).getRadians() * globe.getRadius();
    }

    @Override
    public double getLength(Globe globe)
    {
        // output max side as width
        double a, b, c, max;
        a = LatLon.greatCircleDistance(left, right).getRadians();
        b = LatLon.greatCircleDistance(right, top).getRadians();
        c = LatLon.greatCircleDistance(top, left).getRadians();
        max = (a > b ? a : b) > c ? (a > b ? a : b) : c;
        return globe.getRadius() * max;
    }
}
