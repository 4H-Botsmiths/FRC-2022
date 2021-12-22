/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.spline;

import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.spline.PoseWithCurvature;
import edu.wpi.first.math.spline.Spline;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public final class SplineParameterizer {
    private static final double kMaxDx = 0.127;
    private static final double kMaxDy = 0.00127;
    private static final double kMaxDtheta = 0.0872;
    private static final int kMaxIterations = 5000;

    private SplineParameterizer() {
    }

    public static List<PoseWithCurvature> parameterize(Spline spline) {
        return SplineParameterizer.parameterize(spline, 0.0, 1.0);
    }

    public static List<PoseWithCurvature> parameterize(Spline spline, double t0, double t1) {
        ArrayList<PoseWithCurvature> splinePoints = new ArrayList<PoseWithCurvature>();
        splinePoints.add(spline.getPoint(t0));
        ArrayDeque<StackContents> stack = new ArrayDeque<StackContents>();
        stack.push(new StackContents(t0, t1));
        int iterations = 0;
        while (!stack.isEmpty()) {
            StackContents current = (StackContents)stack.removeFirst();
            PoseWithCurvature start = spline.getPoint(current.t0);
            PoseWithCurvature end = spline.getPoint(current.t1);
            Twist2d twist = start.poseMeters.log(end.poseMeters);
            if (Math.abs(twist.dy) > 0.00127 || Math.abs(twist.dx) > 0.127 || Math.abs(twist.dtheta) > 0.0872) {
                stack.addFirst(new StackContents((current.t0 + current.t1) / 2.0, current.t1));
                stack.addFirst(new StackContents(current.t0, (current.t0 + current.t1) / 2.0));
            } else {
                splinePoints.add(spline.getPoint(current.t1));
            }
            if (++iterations < 5000) continue;
            throw new MalformedSplineException("Could not parameterize a malformed spline. This means that you probably had two or  more adjacent waypoints that were very close together with headings in opposing directions.");
        }
        return splinePoints;
    }

    public static class MalformedSplineException
    extends RuntimeException {
        private MalformedSplineException(String message) {
            super(message);
        }
    }

    private static class StackContents {
        final double t1;
        final double t0;

        StackContents(double t0, double t1) {
            this.t0 = t0;
            this.t1 = t1;
        }
    }
}

