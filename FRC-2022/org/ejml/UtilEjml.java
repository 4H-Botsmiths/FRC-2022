/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import org.ejml.LinearSolverSafe;
import org.ejml.LinearSolverSparseSafe;
import org.ejml.MatrixDimensionException;
import org.ejml.data.BMatrixRMaj;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.DGrowArray;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.FGrowArray;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.IGrowArray;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixSparse;
import org.ejml.data.ReshapeMatrix;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.interfaces.linsol.LinearSolver;
import org.ejml.interfaces.linsol.LinearSolverDense;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.ops.DConvertMatrixStruct;
import org.ejml.ops.FConvertMatrixStruct;
import org.jetbrains.annotations.Nullable;
import pabeles.concurrency.GrowArray;

public class UtilEjml {
    public static double EPS = Math.pow(2.0, -52.0);
    public static float F_EPS = (float)Math.pow(2.0, -21.0);
    public static double PI = Math.PI;
    public static double PI2 = Math.PI * 2;
    public static double PId2 = 1.5707963267948966;
    public static float F_PI = (float)Math.PI;
    public static float F_PI2 = (float)Math.PI * 2;
    public static float F_PId2 = 1.5707964f;
    public static float TEST_F32 = 5.0E-4f;
    public static double TEST_F64 = 1.0E-8;
    public static float TESTP_F32 = 1.0E-6f;
    public static double TESTP_F64 = 1.0E-12;
    public static float TEST_F32_SQ = (float)Math.sqrt(TEST_F32);
    public static double TEST_F64_SQ = Math.sqrt(TEST_F64);
    public static int maxInverseSize = 5;
    public static final int[] ZERO_LENGTH_I32 = new int[0];
    public static final float[] ZERO_LENGTH_F32 = new float[0];
    public static final double[] ZERO_LENGTH_F64 = new double[0];

    public static void checkSameInstance(Object a, Object b) {
        if (a == b) {
            throw new IllegalArgumentException("Can't pass in the same instance");
        }
    }

    public static DMatrixRMaj reshapeOrDeclare(@Nullable DMatrixRMaj a, int rows, int cols) {
        if (a == null) {
            return new DMatrixRMaj(rows, cols);
        }
        if (a.numRows != rows || a.numCols != cols) {
            a.reshape(rows, cols);
        }
        return a;
    }

    public static FMatrixRMaj reshapeOrDeclare(@Nullable FMatrixRMaj a, int rows, int cols) {
        if (a == null) {
            return new FMatrixRMaj(rows, cols);
        }
        if (a.numRows != rows || a.numCols != cols) {
            a.reshape(rows, cols);
        }
        return a;
    }

    public static BMatrixRMaj reshapeOrDeclare(@Nullable BMatrixRMaj a, int rows, int cols) {
        if (a == null) {
            return new BMatrixRMaj(rows, cols);
        }
        if (a.numRows != rows || a.numCols != cols) {
            a.reshape(rows, cols);
        }
        return a;
    }

    public static ZMatrixRMaj reshapeOrDeclare(@Nullable ZMatrixRMaj a, int rows, int cols) {
        if (a == null) {
            return new ZMatrixRMaj(rows, cols);
        }
        if (a.numRows != rows || a.numCols != cols) {
            a.reshape(rows, cols);
        }
        return a;
    }

    public static CMatrixRMaj reshapeOrDeclare(@Nullable CMatrixRMaj a, int rows, int cols) {
        if (a == null) {
            return new CMatrixRMaj(rows, cols);
        }
        if (a.numRows != rows || a.numCols != cols) {
            a.reshape(rows, cols);
        }
        return a;
    }

    public static <T extends ReshapeMatrix> T reshapeOrDeclare(@Nullable T target, T reference) {
        if (target == null) {
            return (T)((ReshapeMatrix)reference.createLike());
        }
        if (target.getNumRows() != reference.getNumRows() || target.getNumCols() != reference.getNumCols()) {
            target.reshape(reference.getNumRows(), reference.getNumCols());
        }
        return target;
    }

    public static <T extends MatrixSparse> T reshapeOrDeclare(@Nullable T target, MatrixSparse reference) {
        if (target == null) {
            return (T)((MatrixSparse)reference.createLike());
        }
        target.reshape(reference.getNumRows(), reference.getNumCols(), reference.getNonZeroLength());
        return target;
    }

    public static <T extends ReshapeMatrix> T reshapeOrDeclare(@Nullable T target, T reference, int rows, int cols) {
        if (target == null) {
            return (T)((ReshapeMatrix)reference.create(rows, cols));
        }
        if (target.getNumRows() != rows || target.getNumCols() != cols) {
            target.reshape(rows, cols);
        }
        return target;
    }

    public static DMatrixSparseCSC reshapeOrDeclare(@Nullable DMatrixSparseCSC target, int rows, int cols, int nz_length) {
        if (target == null) {
            return new DMatrixSparseCSC(rows, cols, nz_length);
        }
        target.reshape(rows, cols, nz_length);
        return target;
    }

    public static FMatrixSparseCSC reshapeOrDeclare(@Nullable FMatrixSparseCSC target, int rows, int cols, int nz_length) {
        if (target == null) {
            return new FMatrixSparseCSC(rows, cols, nz_length);
        }
        target.reshape(rows, cols, nz_length);
        return target;
    }

    public static void checkSameShape(Matrix a, Matrix b, boolean allowedSameInstance) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            throw new MatrixDimensionException("Must be same shape. " + a.getNumRows() + "x" + a.getNumCols() + " vs " + b.getNumRows() + "x" + b.getNumCols());
        }
        if (!allowedSameInstance && a == b) {
            throw new IllegalArgumentException("Must not be the same instance");
        }
    }

    public static void checkSameShape(Matrix a, Matrix b, Matrix c) {
        if (a.getNumRows() != b.getNumRows() || a.getNumCols() != b.getNumCols()) {
            throw new MatrixDimensionException("Must be same shape. " + a.getNumRows() + "x" + a.getNumCols() + " vs " + b.getNumRows() + "x" + b.getNumCols());
        }
        if (a.getNumRows() != c.getNumRows() || a.getNumCols() != c.getNumCols()) {
            throw new IllegalArgumentException("Must be same shape. " + a.getNumRows() + "x" + a.getNumCols() + " vs " + c.getNumRows() + "x" + c.getNumCols());
        }
    }

    public static <S extends Matrix, D extends Matrix> LinearSolver<S, D> safe(LinearSolver<S, D> solver) {
        if (solver.modifiesA() || solver.modifiesB()) {
            if (solver instanceof LinearSolverDense) {
                return new LinearSolverSafe((LinearSolverDense)solver);
            }
            if (solver instanceof LinearSolverSparse) {
                return new LinearSolverSparseSafe((LinearSolverSparse)solver);
            }
            throw new IllegalArgumentException("Unknown solver type");
        }
        return solver;
    }

    public static <D extends ReshapeMatrix> LinearSolverDense<D> safe(LinearSolverDense<D> solver) {
        if (solver.modifiesA() || solver.modifiesB()) {
            return new LinearSolverSafe<D>(solver);
        }
        return solver;
    }

    public static void checkTooLarge(int rows, int cols) {
        if ((long)(rows * cols) != (long)rows * (long)cols) {
            throw new IllegalArgumentException("Matrix size exceeds the size of an integer");
        }
    }

    public static void checkTooLargeComplex(int rows, int cols) {
        if ((long)(2 * rows * cols) != (long)rows * (long)cols * 2L) {
            throw new IllegalArgumentException("Matrix size exceeds the size of an integer");
        }
    }

    public static boolean isUncountable(double val) {
        return Double.isNaN(val) || Double.isInfinite(val);
    }

    public static boolean isUncountable(float val) {
        return Float.isNaN(val) || Float.isInfinite(val);
    }

    public static boolean isIdentical(double a, double b, double tol) {
        double diff = Math.abs(a - b);
        if (tol >= diff) {
            return true;
        }
        if (Double.isNaN(a)) {
            return Double.isNaN(b);
        }
        return Double.isInfinite(a) && a == b;
    }

    public static boolean isIdentical(float a, float b, float tol) {
        double diff = Math.abs(a - b);
        if ((double)tol >= diff) {
            return true;
        }
        if (Float.isNaN(a)) {
            return Float.isNaN(b);
        }
        return Float.isInfinite(a) && a == b;
    }

    public static void memset(double[] data, double val, int length) {
        for (int i = 0; i < length; ++i) {
            data[i] = val;
        }
    }

    public static void memset(int[] data, int val, int length) {
        for (int i = 0; i < length; ++i) {
            data[i] = val;
        }
    }

    public static <T> void setnull(T[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = null;
        }
    }

    public static double max(double[] array, int start, int length) {
        double max = array[start];
        int end = start + length;
        for (int i = start + 1; i < end; ++i) {
            double v = array[i];
            if (!(v > max)) continue;
            max = v;
        }
        return max;
    }

    public static float max(float[] array, int start, int length) {
        float max = array[start];
        int end = start + length;
        for (int i = start + 1; i < end; ++i) {
            float v = array[i];
            if (!(v > max)) continue;
            max = v;
        }
        return max;
    }

    public static DMatrixRMaj parse_DDRM(String s, int numColumns) {
        String[] vals = s.split("(\\s)+");
        int start = vals[0].isEmpty() ? 1 : 0;
        int numRows = (vals.length - start) / numColumns;
        DMatrixRMaj ret = new DMatrixRMaj(numRows, numColumns);
        int index = start;
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numColumns; ++j) {
                ret.set(i, j, Double.parseDouble(vals[index++]));
            }
        }
        return ret;
    }

    public static FMatrixRMaj parse_FDRM(String s, int numColumns) {
        String[] vals = s.split("(\\s)+");
        int start = vals[0].isEmpty() ? 1 : 0;
        int numRows = (vals.length - start) / numColumns;
        FMatrixRMaj ret = new FMatrixRMaj(numRows, numColumns);
        int index = start;
        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numColumns; ++j) {
                ret.set(i, j, Float.parseFloat(vals[index++]));
            }
        }
        return ret;
    }

    public static Integer[] sortByIndex(double[] data, int size) {
        Integer[] idx = new Integer[size];
        for (int i = 0; i < size; ++i) {
            idx[i] = i;
        }
        Arrays.sort(idx, Comparator.comparingDouble(o -> data[o]));
        return idx;
    }

    public static DMatrixSparseCSC parse_DSCC(String s, int numColumns) {
        DMatrixRMaj tmp = UtilEjml.parse_DDRM(s, numColumns);
        return DConvertMatrixStruct.convert(tmp, (DMatrixSparseCSC)null, 0.0);
    }

    public static FMatrixSparseCSC parse_FSCC(String s, int numColumns) {
        FMatrixRMaj tmp = UtilEjml.parse_FDRM(s, numColumns);
        return FConvertMatrixStruct.convert(tmp, (FMatrixSparseCSC)null, 0.0f);
    }

    public static int[] shuffled(int N, Random rand) {
        return UtilEjml.shuffled(N, N, rand);
    }

    public static int[] shuffled(int N, int shuffleUpTo, Random rand) {
        int[] l = new int[N];
        for (int i = 0; i < N; ++i) {
            l[i] = i;
        }
        UtilEjml.shuffle(l, N, 0, shuffleUpTo, rand);
        return l;
    }

    public static int[] shuffledSorted(int N, int shuffleUpTo, Random rand) {
        int[] l = new int[N];
        for (int i = 0; i < N; ++i) {
            l[i] = i;
        }
        UtilEjml.shuffle(l, N, 0, shuffleUpTo, rand);
        Arrays.sort(l, 0, shuffleUpTo);
        return l;
    }

    public static void shuffle(int[] list, int N, int start, int end, Random rand) {
        int range = end - start;
        for (int i = 0; i < range; ++i) {
            int selected = rand.nextInt(N - i) + i + start;
            int v = list[i];
            list[i] = list[selected];
            list[selected] = v;
        }
    }

    public static int[] pivotVector(int[] pivots, int length, @Nullable IGrowArray storage) {
        if (storage == null) {
            storage = new IGrowArray();
        }
        storage.reshape(length);
        System.arraycopy(pivots, 0, storage.data, 0, length);
        return storage.data;
    }

    public static int permutationSign(int[] p, int N, int[] work) {
        System.arraycopy(p, 0, work, 0, N);
        p = work;
        int cnt = 0;
        for (int i = 0; i < N; ++i) {
            while (i != p[i]) {
                ++cnt;
                int tmp = p[i];
                p[i] = p[p[i]];
                p[tmp] = tmp;
            }
        }
        return cnt % 2 == 0 ? 1 : -1;
    }

    public static double[] randomVector_F64(Random rand, int length) {
        double[] d = new double[length];
        for (int i = 0; i < length; ++i) {
            d[i] = rand.nextDouble();
        }
        return d;
    }

    public static float[] randomVector_F32(Random rand, int length) {
        float[] d = new float[length];
        for (int i = 0; i < length; ++i) {
            d[i] = rand.nextFloat();
        }
        return d;
    }

    public static String stringShapes(Matrix A, Matrix B, Matrix C) {
        return "( " + A.getNumRows() + "x" + A.getNumCols() + " ) ( " + B.getNumRows() + "x" + B.getNumCols() + " ) ( " + C.getNumRows() + "x" + C.getNumCols() + " )";
    }

    public static String stringShapes(Matrix A, Matrix B) {
        return "( " + A.getNumRows() + "x" + A.getNumCols() + " ) ( " + B.getNumRows() + "x" + B.getNumCols() + " )";
    }

    public static String fancyStringF(double value, DecimalFormat format, int length, int significant) {
        String formatted = UtilEjml.fancyString(value, format, length, significant);
        int n = length - formatted.length();
        if (n > 0) {
            StringBuilder builder = new StringBuilder(n);
            for (int i = 0; i < n; ++i) {
                builder.append(' ');
            }
            return formatted + builder.toString();
        }
        return formatted;
    }

    public static String fancyString(double value, DecimalFormat format, int length, int significant) {
        return UtilEjml.fancyString(value, format, true, length, significant);
    }

    public static String fancyString(double value, DecimalFormat format, boolean hasSpace, int length, int significant) {
        String formatted;
        boolean isNegative;
        boolean bl = isNegative = Double.doubleToRawLongBits(value) < 0L;
        if (value == 0.0) {
            formatted = isNegative ? "-0" : (hasSpace ? " 0" : "0");
        } else {
            int digits = length - 1;
            String extraSpace = isNegative ? "" : (hasSpace ? " " : "");
            double vabs = Math.abs(value);
            int a = (int)Math.floor(Math.log10(vabs));
            if (a >= 0 && a < digits) {
                format.setMaximumFractionDigits(digits - 2 - a);
                formatted = extraSpace + format.format(value);
            } else if (a < 0 && digits + a > significant) {
                format.setMaximumFractionDigits(digits - 1);
                formatted = extraSpace + format.format(value);
            } else {
                int exp = (int)Math.log10(Math.abs(a)) + 1;
                formatted = (significant = Math.min(significant, digits - significant - exp)) > 0 ? extraSpace + String.format("%." + significant + "E", value) : extraSpace + String.format("%.0E", value);
            }
        }
        return formatted;
    }

    public static int[] adjust(@Nullable IGrowArray gwork, int desired) {
        if (gwork == null) {
            gwork = new IGrowArray();
        }
        gwork.reshape(desired);
        return gwork.data;
    }

    public static int[] adjust(@Nullable IGrowArray gwork, int desired, int zeroToM) {
        int[] w = UtilEjml.adjust(gwork, desired);
        Arrays.fill(w, 0, zeroToM, 0);
        return w;
    }

    public static int[] adjustClear(@Nullable IGrowArray gwork, int desired) {
        return UtilEjml.adjust(gwork, desired, desired);
    }

    public static int[] adjustFill(@Nullable IGrowArray gwork, int desired, int value) {
        int[] w = UtilEjml.adjust(gwork, desired);
        Arrays.fill(w, 0, desired, value);
        return w;
    }

    public static double[] adjust(@Nullable DGrowArray gwork, int desired) {
        if (gwork == null) {
            gwork = new DGrowArray();
        }
        gwork.reshape(desired);
        return gwork.data;
    }

    public static float[] adjust(@Nullable FGrowArray gwork, int desired) {
        if (gwork == null) {
            gwork = new FGrowArray();
        }
        gwork.reshape(desired);
        return gwork.data;
    }

    public static boolean hasNullableArgument(Method func) {
        Annotation[][] annotations = func.getParameterAnnotations();
        if (annotations.length == 0) {
            return false;
        }
        Class<?>[] types = func.getParameterTypes();
        for (int i = 0; i < types.length; ++i) {
            Annotation last;
            Annotation[] argumentAnnotations = annotations[i];
            if (argumentAnnotations.length == 0 || !Matrix.class.isAssignableFrom(types[i]) || !(last = argumentAnnotations[argumentAnnotations.length - 1]).toString().contains("Nullable")) continue;
            return true;
        }
        return false;
    }

    public static GrowArray<FGrowArray> checkDeclare_F32(@Nullable GrowArray<FGrowArray> workspace) {
        if (workspace == null) {
            return new GrowArray<FGrowArray>(FGrowArray::new);
        }
        return workspace;
    }

    public static GrowArray<DGrowArray> checkDeclare_F64(@Nullable GrowArray<DGrowArray> workspace) {
        if (workspace == null) {
            return new GrowArray<DGrowArray>(DGrowArray::new);
        }
        return workspace;
    }

    public static boolean exceedsMaxMatrixSize(int numRows, int numCols) {
        if (numRows == 0 || numCols == 0) {
            return false;
        }
        return numCols > Integer.MAX_VALUE / numRows;
    }

    public static void printTime(String message, Process timer) {
        UtilEjml.printTime("Processing... ", message, timer);
    }

    public static void printTime(String pre, String message, Process timer) {
        System.out.printf(pre, new Object[0]);
        long time0 = System.nanoTime();
        timer.process();
        long time1 = System.nanoTime();
        System.out.println(message + " " + (double)(time1 - time0) * 1.0E-6 + " (ms)");
    }

    public static void assertEq(int valA, int valB) {
        UtilEjml.assertEq(valA, valB, "");
    }

    public static void assertEq(int valA, int valB, String message) {
        if (valA != valB) {
            throw new IllegalArgumentException(valA + " != " + valB + " " + message);
        }
    }

    public static void assertTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertTrue(boolean value) {
        if (!value) {
            throw new IllegalArgumentException("Expected true");
        }
    }

    public static void assertShape(int valA, int valB, String message) {
        if (valA != valB) {
            throw new MatrixDimensionException(valA + " != " + valB + " " + message);
        }
    }

    public static void assertShape(boolean value, String message) {
        if (!value) {
            throw new MatrixDimensionException(message);
        }
    }

    public static void checkReshapeSolve(int numRowsA, int numColsA, ReshapeMatrix B, ReshapeMatrix X) {
        if (B.getNumRows() != numRowsA) {
            throw new IllegalArgumentException("Unexpected number of rows in B based on shape of A. Found=" + B.getNumRows() + " Expected=" + numRowsA);
        }
        X.reshape(numColsA, B.getNumCols());
    }

    public static interface Process {
        public void process();
    }
}

