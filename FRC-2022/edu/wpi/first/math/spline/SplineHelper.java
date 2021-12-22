/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.spline;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.spline.CubicHermiteSpline;
import edu.wpi.first.math.spline.QuinticHermiteSpline;
import edu.wpi.first.math.spline.Spline;
import java.util.Arrays;
import java.util.List;

public final class SplineHelper {
    private SplineHelper() {
    }

    public static Spline.ControlVector[] getCubicControlVectorsFromWaypoints(Pose2d start, Translation2d[] interiorWaypoints, Pose2d end) {
        Spline.ControlVector endCV;
        Spline.ControlVector initialCV;
        if (interiorWaypoints.length < 1) {
            double scalar = start.getTranslation().getDistance(end.getTranslation()) * 1.2;
            initialCV = SplineHelper.getCubicControlVector(scalar, start);
            endCV = SplineHelper.getCubicControlVector(scalar, end);
        } else {
            double scalar = start.getTranslation().getDistance(interiorWaypoints[0]) * 1.2;
            initialCV = SplineHelper.getCubicControlVector(scalar, start);
            scalar = end.getTranslation().getDistance(interiorWaypoints[interiorWaypoints.length - 1]) * 1.2;
            endCV = SplineHelper.getCubicControlVector(scalar, end);
        }
        return new Spline.ControlVector[]{initialCV, endCV};
    }

    public static QuinticHermiteSpline[] getQuinticSplinesFromWaypoints(List<Pose2d> waypoints) {
        QuinticHermiteSpline[] splines = new QuinticHermiteSpline[waypoints.size() - 1];
        for (int i = 0; i < waypoints.size() - 1; ++i) {
            Pose2d p0 = waypoints.get(i);
            Pose2d p1 = waypoints.get(i + 1);
            double scalar = 1.2 * p0.getTranslation().getDistance(p1.getTranslation());
            Spline.ControlVector controlVecA = SplineHelper.getQuinticControlVector(scalar, p0);
            Spline.ControlVector controlVecB = SplineHelper.getQuinticControlVector(scalar, p1);
            splines[i] = new QuinticHermiteSpline(controlVecA.x, controlVecB.x, controlVecA.y, controlVecB.y);
        }
        return splines;
    }

    public static CubicHermiteSpline[] getCubicSplinesFromControlVectors(Spline.ControlVector start, Translation2d[] waypoints, Spline.ControlVector end) {
        CubicHermiteSpline[] splines = new CubicHermiteSpline[waypoints.length + 1];
        double[] xInitial = start.x;
        double[] yInitial = start.y;
        double[] xFinal = end.x;
        double[] yFinal = end.y;
        if (waypoints.length > 1) {
            int i;
            Translation2d[] newWaypts = new Translation2d[waypoints.length + 2];
            newWaypts[0] = new Translation2d(xInitial[0], yInitial[0]);
            System.arraycopy(waypoints, 0, newWaypts, 1, waypoints.length);
            newWaypts[newWaypts.length - 1] = new Translation2d(xFinal[0], yFinal[0]);
            double[] a = new double[newWaypts.length - 2];
            double[] b = new double[newWaypts.length - 2];
            Arrays.fill(b, 4.0);
            double[] c = new double[newWaypts.length - 2];
            double[] dx = new double[newWaypts.length - 2];
            double[] dy = new double[newWaypts.length - 2];
            double[] fx = new double[newWaypts.length - 2];
            double[] fy = new double[newWaypts.length - 2];
            a[0] = 0.0;
            for (i = 0; i < newWaypts.length - 3; ++i) {
                a[i + 1] = 1.0;
                c[i] = 1.0;
            }
            c[c.length - 1] = 0.0;
            dx[0] = 3.0 * (newWaypts[2].getX() - newWaypts[0].getX()) - xInitial[1];
            dy[0] = 3.0 * (newWaypts[2].getY() - newWaypts[0].getY()) - yInitial[1];
            if (newWaypts.length > 4) {
                for (i = 1; i <= newWaypts.length - 4; ++i) {
                    dx[i] = 3.0 * (newWaypts[i + 2].getX() - newWaypts[i].getX());
                    dy[i] = 3.0 * (newWaypts[i + 2].getY() - newWaypts[i].getY());
                }
            }
            dx[dx.length - 1] = 3.0 * (newWaypts[newWaypts.length - 1].getX() - newWaypts[newWaypts.length - 3].getX()) - xFinal[1];
            dy[dy.length - 1] = 3.0 * (newWaypts[newWaypts.length - 1].getY() - newWaypts[newWaypts.length - 3].getY()) - yFinal[1];
            SplineHelper.thomasAlgorithm(a, b, c, dx, fx);
            SplineHelper.thomasAlgorithm(a, b, c, dy, fy);
            double[] newFx = new double[fx.length + 2];
            double[] newFy = new double[fy.length + 2];
            newFx[0] = xInitial[1];
            newFy[0] = yInitial[1];
            System.arraycopy(fx, 0, newFx, 1, fx.length);
            System.arraycopy(fy, 0, newFy, 1, fy.length);
            newFx[newFx.length - 1] = xFinal[1];
            newFy[newFy.length - 1] = yFinal[1];
            for (int i2 = 0; i2 < newFx.length - 1; ++i2) {
                splines[i2] = new CubicHermiteSpline(new double[]{newWaypts[i2].getX(), newFx[i2]}, new double[]{newWaypts[i2 + 1].getX(), newFx[i2 + 1]}, new double[]{newWaypts[i2].getY(), newFy[i2]}, new double[]{newWaypts[i2 + 1].getY(), newFy[i2 + 1]});
            }
        } else if (waypoints.length == 1) {
            double xDeriv = (3.0 * (xFinal[0] - xInitial[0]) - xFinal[1] - xInitial[1]) / 4.0;
            double yDeriv = (3.0 * (yFinal[0] - yInitial[0]) - yFinal[1] - yInitial[1]) / 4.0;
            double[] midXControlVector = new double[]{waypoints[0].getX(), xDeriv};
            double[] midYControlVector = new double[]{waypoints[0].getY(), yDeriv};
            splines[0] = new CubicHermiteSpline(xInitial, midXControlVector, yInitial, midYControlVector);
            splines[1] = new CubicHermiteSpline(midXControlVector, xFinal, midYControlVector, yFinal);
        } else {
            splines[0] = new CubicHermiteSpline(xInitial, xFinal, yInitial, yFinal);
        }
        return splines;
    }

    public static QuinticHermiteSpline[] getQuinticSplinesFromControlVectors(Spline.ControlVector[] controlVectors) {
        QuinticHermiteSpline[] splines = new QuinticHermiteSpline[controlVectors.length - 1];
        for (int i = 0; i < controlVectors.length - 1; ++i) {
            double[] xInitial = controlVectors[i].x;
            double[] xFinal = controlVectors[i + 1].x;
            double[] yInitial = controlVectors[i].y;
            double[] yFinal = controlVectors[i + 1].y;
            splines[i] = new QuinticHermiteSpline(xInitial, xFinal, yInitial, yFinal);
        }
        return splines;
    }

    private static void thomasAlgorithm(double[] a, double[] b, double[] c, double[] d, double[] solutionVector) {
        int i;
        int N = d.length;
        double[] cStar = new double[N];
        double[] dStar = new double[N];
        cStar[0] = c[0] / b[0];
        dStar[0] = d[0] / b[0];
        for (i = 1; i < N; ++i) {
            double m = 1.0 / (b[i] - a[i] * cStar[i - 1]);
            cStar[i] = c[i] * m;
            dStar[i] = (d[i] - a[i] * dStar[i - 1]) * m;
        }
        solutionVector[N - 1] = dStar[N - 1];
        for (i = N - 2; i >= 0; --i) {
            solutionVector[i] = dStar[i] - cStar[i] * solutionVector[i + 1];
        }
    }

    private static Spline.ControlVector getCubicControlVector(double scalar, Pose2d point) {
        return new Spline.ControlVector(new double[]{point.getX(), scalar * point.getRotation().getCos()}, new double[]{point.getY(), scalar * point.getRotation().getSin()});
    }

    private static Spline.ControlVector getQuinticControlVector(double scalar, Pose2d point) {
        return new Spline.ControlVector(new double[]{point.getX(), scalar * point.getRotation().getCos(), 0.0}, new double[]{point.getY(), scalar * point.getRotation().getSin(), 0.0});
    }
}

