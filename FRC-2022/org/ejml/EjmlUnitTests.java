/*
 * Decompiled with CFR 0.150.
 */
package org.ejml;

import org.ejml.UtilEjml;
import org.ejml.data.CMatrix;
import org.ejml.data.Complex_F32;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrix;
import org.ejml.data.FMatrix;
import org.ejml.data.Matrix;
import org.ejml.data.ZMatrix;

public class EjmlUnitTests {
    public static void assertCountable(DMatrix A) {
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                EjmlUnitTests.assertTrue(!Double.isNaN(A.get(i, j)), "NaN found at " + i + " " + j);
                EjmlUnitTests.assertTrue(!Double.isInfinite(A.get(i, j)), "Infinite found at " + i + " " + j);
            }
        }
    }

    public static void assertShape(Matrix A, Matrix B) {
        EjmlUnitTests.assertTrue(A.getNumRows() == B.getNumRows(), "Number of rows do not match");
        EjmlUnitTests.assertTrue(A.getNumCols() == B.getNumCols(), "Number of columns do not match");
    }

    public static void assertShape(Matrix A, int numRows, int numCols) {
        EjmlUnitTests.assertTrue(A.getNumRows() == numRows, "Unexpected number of rows.");
        EjmlUnitTests.assertTrue(A.getNumCols() == numCols, "Unexpected number of columns.");
    }

    public static void assertEqualsUncountable(DMatrix A, DMatrix B, double tol) {
        EjmlUnitTests.assertShape(A, B);
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                double valA = A.get(i, j);
                double valB = B.get(i, j);
                if (Double.isNaN(valA)) {
                    EjmlUnitTests.assertTrue(Double.isNaN(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                    continue;
                }
                if (Double.isInfinite(valA)) {
                    EjmlUnitTests.assertTrue(Double.isInfinite(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                    continue;
                }
                double diff = Math.abs(valA - valB);
                EjmlUnitTests.assertTrue(diff <= tol, "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
            }
        }
    }

    public static void assertEquals(Matrix A, Matrix B) {
        if (A instanceof DMatrix && B instanceof DMatrix) {
            EjmlUnitTests.assertEquals((DMatrix)A, (DMatrix)B, UtilEjml.TEST_F64);
        } else if (A instanceof FMatrix && B instanceof FMatrix) {
            EjmlUnitTests.assertEquals((FMatrix)A, (FMatrix)B, UtilEjml.TEST_F32);
        } else if (A instanceof FMatrix) {
            EjmlUnitTests.assertEquals((FMatrix)A, (DMatrix)B, UtilEjml.TEST_F32);
        } else {
            EjmlUnitTests.assertEquals((FMatrix)B, (DMatrix)A, UtilEjml.TEST_F32);
        }
    }

    public static void assertEquals(DMatrix A, DMatrix B, double tol) {
        EjmlUnitTests.assertShape(A, B);
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                double valA = A.get(i, j);
                double valB = B.get(i, j);
                EjmlUnitTests.assertTrue(!Double.isNaN(valA) && !Double.isNaN(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                EjmlUnitTests.assertTrue(!Double.isInfinite(valA) && !Double.isInfinite(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                double error = Math.abs(valA - valB);
                EjmlUnitTests.assertTrue(error <= tol, "At (" + i + "," + j + ") A = " + valA + " B = " + valB + " error = " + error + " tol = " + tol);
            }
        }
    }

    public static void assertRelativeEquals(DMatrix expected, DMatrix found, double tol) {
        EjmlUnitTests.assertShape(expected, found);
        for (int i = 0; i < expected.getNumRows(); ++i) {
            for (int j = 0; j < expected.getNumCols(); ++j) {
                double expecEl = expected.get(i, j);
                double foundEl = found.get(i, j);
                if (Double.isNaN(expecEl) != Double.isNaN(foundEl) || Double.isInfinite(expecEl) != Double.isInfinite(foundEl)) {
                    throw new AssertionError((Object)("At (" + i + "," + j + ") A = " + expecEl + " B = " + foundEl));
                }
                double error = Math.abs(expecEl - foundEl);
                if (expecEl != 0.0) {
                    double max = Math.max(Math.abs(expecEl), Math.abs(foundEl));
                    error /= max;
                }
                if (!(error > tol)) continue;
                if (expected.getNumRows() <= 10) {
                    System.out.println("------------  A  -----------");
                    expected.print();
                    System.out.println("\n------------  B  -----------");
                    found.print();
                }
                throw new AssertionError((Object)("At (" + i + "," + j + ") expected = " + expecEl + " found = " + foundEl + " error = " + error + " tol = " + tol));
            }
        }
    }

    public static void assertRelativeEquals(FMatrix expected, FMatrix found, double tol) {
        EjmlUnitTests.assertShape(expected, found);
        for (int i = 0; i < expected.getNumRows(); ++i) {
            for (int j = 0; j < expected.getNumCols(); ++j) {
                float expecEl = expected.get(i, j);
                float foundEl = found.get(i, j);
                if (Float.isNaN(expecEl) != Float.isNaN(foundEl) || Double.isInfinite(expecEl) != Double.isInfinite(foundEl)) {
                    throw new AssertionError((Object)("At (" + i + "," + j + ") A = " + expecEl + " B = " + foundEl));
                }
                float error = Math.abs(expecEl - foundEl);
                if ((double)expecEl != 0.0) {
                    float max = Math.max(Math.abs(expecEl), Math.abs(foundEl));
                    error /= max;
                }
                if (!((double)error > tol)) continue;
                if (expected.getNumRows() <= 10) {
                    System.out.println("------------  A  -----------");
                    expected.print();
                    System.out.println("\n------------  B  -----------");
                    found.print();
                }
                throw new AssertionError((Object)("At (" + i + "," + j + ") expected = " + expecEl + " found = " + foundEl + " error = " + error + " tol = " + tol));
            }
        }
    }

    public static void assertEquals(FMatrix A, FMatrix B, float tol) {
        EjmlUnitTests.assertShape(A, B);
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                float valA = A.get(i, j);
                float valB = B.get(i, j);
                EjmlUnitTests.assertTrue(!Float.isNaN(valA) && !Float.isNaN(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                EjmlUnitTests.assertTrue(!Float.isInfinite(valA) && !Float.isInfinite(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                float error = Math.abs(valA - valB);
                EjmlUnitTests.assertTrue(error <= tol, "At (" + i + "," + j + ") A = " + valA + " B = " + valB + " error = " + error + " tol = " + tol);
            }
        }
    }

    private static void assertEquals(FMatrix A, DMatrix B, float tol) {
        EjmlUnitTests.assertShape(A, B);
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                float valA = A.get(i, j);
                double valB = B.get(i, j);
                EjmlUnitTests.assertTrue(!Float.isNaN(valA) && !Double.isNaN(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                EjmlUnitTests.assertTrue(!Float.isInfinite(valA) && !Double.isInfinite(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
                double error = Math.abs((double)valA - valB);
                EjmlUnitTests.assertTrue(error <= (double)tol, "At (" + i + "," + j + ") A = " + valA + " B = " + valB + " error = " + error + " tol = " + tol);
            }
        }
    }

    public static void assertEquals(Complex_F64 a, Complex_F64 b, double tol) {
        EjmlUnitTests.assertTrue(!Double.isNaN(a.real) && !Double.isNaN(b.real), "real a = " + a.real + " b = " + b.real);
        EjmlUnitTests.assertTrue(!Double.isInfinite(a.real) && !Double.isInfinite(b.real), "real a = " + a.real + " b = " + b.real);
        EjmlUnitTests.assertTrue(Math.abs(a.real - b.real) <= tol, "real a = " + a.real + " b = " + b.real);
        EjmlUnitTests.assertTrue(!Double.isNaN(a.imaginary) && !Double.isNaN(b.imaginary), "imaginary a = " + a.imaginary + " b = " + b.imaginary);
        EjmlUnitTests.assertTrue(!Double.isInfinite(a.imaginary) && !Double.isInfinite(b.imaginary), "imaginary a = " + a.imaginary + " b = " + b.imaginary);
        EjmlUnitTests.assertTrue(Math.abs(a.imaginary - b.imaginary) <= tol, "imaginary a = " + a.imaginary + " b = " + b.imaginary);
    }

    public static void assertEquals(Complex_F32 a, Complex_F32 b, float tol) {
        EjmlUnitTests.assertTrue(!Float.isNaN(a.real) && !Float.isNaN(b.real), "real a = " + a.real + " b = " + b.real);
        EjmlUnitTests.assertTrue(!Float.isInfinite(a.real) && !Float.isInfinite(b.real), "real a = " + a.real + " b = " + b.real);
        EjmlUnitTests.assertTrue(Math.abs(a.real - b.real) <= tol, "real a = " + a.real + " b = " + b.real);
        EjmlUnitTests.assertTrue(!Float.isNaN(a.imaginary) && !Float.isNaN(b.imaginary), "imaginary a = " + a.imaginary + " b = " + b.imaginary);
        EjmlUnitTests.assertTrue(!Float.isInfinite(a.imaginary) && !Float.isInfinite(b.imaginary), "imaginary a = " + a.imaginary + " b = " + b.imaginary);
        EjmlUnitTests.assertTrue(Math.abs(a.imaginary - b.imaginary) <= tol, "imaginary a = " + a.imaginary + " b = " + b.imaginary);
    }

    public static void assertEquals(ZMatrix A, ZMatrix B, double tol) {
        EjmlUnitTests.assertShape(A, B);
        Complex_F64 a = new Complex_F64();
        Complex_F64 b = new Complex_F64();
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                A.get(i, j, a);
                B.get(i, j, b);
                EjmlUnitTests.assertTrue(!Double.isNaN(a.real) && !Double.isNaN(b.real), "Real At (" + i + "," + j + ") A = " + a.real + " B = " + b.real);
                EjmlUnitTests.assertTrue(!Double.isInfinite(a.real) && !Double.isInfinite(b.real), "Real At (" + i + "," + j + ") A = " + a.real + " B = " + b.real);
                EjmlUnitTests.assertTrue(Math.abs(a.real - b.real) <= tol, "Real At (" + i + "," + j + ") A = " + a.real + " B = " + b.real);
                EjmlUnitTests.assertTrue(!Double.isNaN(a.imaginary) && !Double.isNaN(b.imaginary), "Img At (" + i + "," + j + ") A = " + a.imaginary + " B = " + b.imaginary);
                EjmlUnitTests.assertTrue(!Double.isInfinite(a.imaginary) && !Double.isInfinite(b.imaginary), "Img At (" + i + "," + j + ") A = " + a.imaginary + " B = " + b.imaginary);
                EjmlUnitTests.assertTrue(Math.abs(a.imaginary - b.imaginary) <= tol, "Img At (" + i + "," + j + ") A = " + a.imaginary + " B = " + b.imaginary);
            }
        }
    }

    public static void assertEquals(CMatrix A, CMatrix B, float tol) {
        EjmlUnitTests.assertShape(A, B);
        Complex_F32 a = new Complex_F32();
        Complex_F32 b = new Complex_F32();
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                A.get(i, j, a);
                B.get(i, j, b);
                EjmlUnitTests.assertTrue(!Float.isNaN(a.real) && !Float.isNaN(b.real), "Real At (" + i + "," + j + ") A = " + a.real + " B = " + b.real);
                EjmlUnitTests.assertTrue(!Float.isInfinite(a.real) && !Float.isInfinite(b.real), "Real At (" + i + "," + j + ") A = " + a.real + " B = " + b.real);
                EjmlUnitTests.assertTrue(Math.abs(a.real - b.real) <= tol, "Real At (" + i + "," + j + ") A = " + a.real + " B = " + b.real);
                EjmlUnitTests.assertTrue(!Float.isNaN(a.imaginary) && !Float.isNaN(b.imaginary), "Img At (" + i + "," + j + ") A = " + a.imaginary + " B = " + b.imaginary);
                EjmlUnitTests.assertTrue(!Float.isInfinite(a.imaginary) && !Float.isInfinite(b.imaginary), "Img At (" + i + "," + j + ") A = " + a.imaginary + " B = " + b.imaginary);
                EjmlUnitTests.assertTrue(Math.abs(a.imaginary - b.imaginary) <= tol, "Img At (" + i + "," + j + ") A = " + a.imaginary + " B = " + b.imaginary);
            }
        }
    }

    public static void assertEqualsTrans(DMatrix A, DMatrix B, double tol) {
        EjmlUnitTests.assertShape(A, B.getNumCols(), B.getNumRows());
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                double valA = A.get(i, j);
                double valB = B.get(j, i);
                EjmlUnitTests.assertTrue(!Double.isNaN(valA) && !Double.isNaN(valB), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
                EjmlUnitTests.assertTrue(!Double.isInfinite(valA) && !Double.isInfinite(valB), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
                EjmlUnitTests.assertTrue(Math.abs(valA - valB) <= tol, "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
            }
        }
    }

    public static void assertEqualsTrans(FMatrix A, FMatrix B, double tol) {
        EjmlUnitTests.assertShape(A, B.getNumCols(), B.getNumRows());
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                Float valA = Float.valueOf(A.get(i, j));
                Float valB = Float.valueOf(B.get(j, i));
                EjmlUnitTests.assertTrue(!Float.isNaN(valA.floatValue()) && !Float.isNaN(valB.floatValue()), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
                EjmlUnitTests.assertTrue(!Float.isInfinite(valA.floatValue()) && !Float.isInfinite(valB.floatValue()), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
                EjmlUnitTests.assertTrue((double)Math.abs(valA.floatValue() - valB.floatValue()) <= tol, "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
            }
        }
    }

    private static void assertTrue(boolean result, String message) {
        assert (result) : message;
        if (!result) {
            throw new AssertionError((Object)message);
        }
    }
}

