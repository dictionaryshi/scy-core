package com.scy.core;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * @author : shichunyang
 * Date    : 2022/11/1
 * Time    : 4:09 下午
 * ---------------------------------------
 * Desc    : GeoUtil
 */
public class GeoUtil {

    public static double getDistance(GlobalCoordinates start, GlobalCoordinates end) {
        GeodeticCurve geodeticCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, start, end);
        return geodeticCurve.getEllipsoidalDistance();
    }
}
