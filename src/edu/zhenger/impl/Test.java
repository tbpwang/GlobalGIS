/*
 * Copyright (C) 2017 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package edu.zhenger.impl;

/**
 * Author: WangZheng Email: tbpwang@gmail.com Function:
 * <p>
 * Date: 2017/10/11
 */
public class Test
{
    public static void main(String[] args)
    {
        double a, b, c, min;
        a = 3;
        b = 2;
        c = 1;
        min = a > b ? b > c ? c : b : a > c ? c : a;
        System.out.println(min);
    }
}
