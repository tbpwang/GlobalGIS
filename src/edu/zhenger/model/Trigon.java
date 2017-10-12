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
    private Vec4 top, left, right;
    private LatLon center;
    private String geocode;
    private Globe globe;

    public Trigon(LatLon top, LatLon left, LatLon right, String geocode)
    {
        if (top == null || left == null || right == null)
        {
            throw new Mistake("nullValue: VertexIsNull");
        }

        Vec4 a, b, c, d;
        a = Change.getInstance().fromLatLon(top);
        b = Change.getInstance().fromLatLon(left);
        c = Change.getInstance().fromLatLon(right);
        d = new Vec4((a.x + b.x + c.x) / 3, (a.y + b.y + c.y) / 3, (a.z + b.z + c.z) / 3);

        this.top = new Vec4(a.getX(), a.getY(), a.getZ());
        this.left = new Vec4(b.getX(), b.getY(), b.getZ());
        this.right = new Vec4(c.getX(), c.getY(), c.getZ());
        this.geocode = geocode;

        this.center = new LatLon(Change.getInstance().fromVec4(d));
        this.globe = Change.getInstance().getGlobe();

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
    public Vec4[] getVertex()
    {
        return new Vec4[] {top, left, right};
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

        LatLon latLonA, latLonB, latLonC;
        latLonA = Change.getInstance().fromVec4(top);
        latLonB = Change.getInstance().fromVec4(left);
        latLonC = Change.getInstance().fromVec4(right);

        double a, b, c, p;
        double A, B, C;

        a = LatLon.greatCircleDistance(latLonB, latLonC).getRadians();
        b = LatLon.greatCircleDistance(latLonC, latLonA).getRadians();
        c = LatLon.greatCircleDistance(latLonA, latLonB).getRadians();
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
        double a = left.angleBetween3(right).getRadians();
        double b = right.angleBetween3(top).getRadians();
        double c = top.angleBetween3(left).getRadians();

        return (a + b + c) * globe.getRadius();
    }

    @Override
    public double getWidth(Globe globe)
    {
        // output min side as width

//        double a = left.distanceTo2(right);
//        double b = right.distanceTo2(top);
//        double c = top.distanceTo2(left);

        double a = Math.abs(left.angleBetween3(right).getRadians());
        double b = Math.abs(right.angleBetween3(top).getRadians());
        double c = Math.abs(top.angleBetween3(left).getRadians());

        double min = (a < b ? a : b) < c ? (a < b ? a : b) : c;
        return min * globe.getRadius();
    }

    @Override
    public double getHeight(Globe globe)
    {
        // output the distance of great circle,
        // from top vertex along longitude to opposite side

        LatLon latLonA = Change.getInstance().fromVec4(top);
        LatLon latLonB = Change.getInstance().fromVec4(left);
        LatLon latLonC = Change.getInstance().fromVec4(right);

        double lat = Angle.average(latLonB.getLatitude(), latLonC.getLatitude()).getRadians();
        double lon = Math.abs(latLonA.getLatitude().getDegrees()) == 90 ? Angle.average(latLonB.getLongitude(),
            latLonC.getLongitude()).getRadians() : latLonA.getLongitude().getRadians();
        LatLon temp = LatLon.fromRadians(lat, lon);

        return LatLon.greatCircleDistance(latLonA, temp).getRadians() * globe.getRadius();
    }

    @Override
    public double getLength(Globe globe)
    {
        // output max side as width
//        LatLon latLonA = Change.getInstance().fromVec4(top);
//        LatLon latLonB = Change.getInstance().fromVec4(left);
//        LatLon latLonC = Change.getInstance().fromVec4(right);
        double a = Math.abs(left.angleBetween3(right).getRadians());
        double b = Math.abs(right.angleBetween3(top).getRadians());
        double c = Math.abs(top.angleBetween3(left).getRadians());
//        double a = LatLon.greatCircleDistance(latLonB, latLonC).getRadians();
//        double b = LatLon.greatCircleDistance(latLonC, latLonA).getRadians();
//        double c = LatLon.greatCircleDistance(latLonA, latLonB).getRadians();
        double max = (a > b ? a : b) > c ? (a > b ? a : b) : c;
        return max * globe.getRadius();
    }
}
