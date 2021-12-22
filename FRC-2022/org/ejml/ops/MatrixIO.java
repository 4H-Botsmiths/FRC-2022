/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 *  us.hebi.matlab.mat.ejml.Mat5Ejml
 *  us.hebi.matlab.mat.format.Mat5
 *  us.hebi.matlab.mat.format.Mat5File
 *  us.hebi.matlab.mat.types.Array
 *  us.hebi.matlab.mat.types.MatFile
 *  us.hebi.matlab.mat.types.MatFile$Entry
 */
package org.ejml.ops;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import org.ejml.UtilEjml;
import org.ejml.data.CMatrix;
import org.ejml.data.Complex_F32;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparse;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DMatrixSparseTriplet;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparse;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.FMatrixSparseTriplet;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixSparse;
import org.ejml.data.MatrixType;
import org.ejml.data.ZMatrix;
import org.ejml.ops.ReadMatrixCsv;
import org.jetbrains.annotations.Nullable;
import us.hebi.matlab.mat.ejml.Mat5Ejml;
import us.hebi.matlab.mat.format.Mat5;
import us.hebi.matlab.mat.format.Mat5File;
import us.hebi.matlab.mat.types.Array;
import us.hebi.matlab.mat.types.MatFile;

public class MatrixIO {
    public static final String DEFAULT_FLOAT_FORMAT = "%11.4E";
    public static final int DEFAULT_LENGTH = 11;
    public static String MATLAB_FORMAT = "%.8E";

    public static DMatrixRMaj matlabToDDRM(String text) {
        text = text.replaceAll("(\\s+|\\[|\\])", "");
        String[] stringRows = text.split(";");
        String[] words = stringRows[0].split(",");
        DMatrixRMaj output = new DMatrixRMaj(stringRows.length, words.length);
        for (int row = 0; row < output.numRows; ++row) {
            words = stringRows[row].split(",");
            if (words.length != output.numCols) {
                throw new IllegalArgumentException("Inconsistent column lengths. " + output.numCols + " " + words.length);
            }
            for (int col = 0; col < output.numCols; ++col) {
                double value = Double.parseDouble(words[col]);
                output.set(row, col, value);
            }
        }
        return output;
    }

    public static FMatrixRMaj matlabToFDRM(String text) {
        text = text.replaceAll("(\\s+|\\[|\\])", "");
        String[] stringRows = text.split(";");
        String[] words = stringRows[0].split(",");
        FMatrixRMaj output = new FMatrixRMaj(stringRows.length, words.length);
        for (int row = 0; row < output.numRows; ++row) {
            words = stringRows[row].split(",");
            if (words.length != output.numCols) {
                throw new IllegalArgumentException("Inconsistent column lengths. " + output.numCols + " " + words.length);
            }
            for (int col = 0; col < output.numCols; ++col) {
                float value = Float.parseFloat(words[col]);
                output.set(row, col, value);
            }
        }
        return output;
    }

    public static void saveMatrixMarketD(DMatrixSparse matrix, String floatFormat, Writer writer) {
        PrintWriter out = new PrintWriter(writer);
        out.println("% Matrix Market Coordinate file written by EJML 0.41");
        out.println("% printf format used '" + floatFormat + "'");
        out.printf("%9d %9d %9d\n", matrix.getNumRows(), matrix.getNumCols(), matrix.getNonZeroLength());
        String lineFormat = "%9d %9d " + floatFormat + "\n";
        Iterator<DMatrixSparse.CoordinateRealValue> iter = matrix.createCoordinateIterator();
        while (iter.hasNext()) {
            DMatrixSparse.CoordinateRealValue val = iter.next();
            out.printf(lineFormat, val.row + 1, val.col + 1, val.value);
        }
        out.flush();
    }

    public static void saveMatrixMarketF(FMatrixSparse matrix, String floatFormat, Writer writer) {
        PrintWriter out = new PrintWriter(writer);
        out.println("% Matrix Market Coordinate file written by EJML 0.41");
        out.println("% printf format used '" + floatFormat + "'");
        out.printf("%9d %9d %9d\n", matrix.getNumRows(), matrix.getNumCols(), matrix.getNonZeroLength());
        String lineFormat = "%9d %9d " + floatFormat + "\n";
        Iterator<FMatrixSparse.CoordinateRealValue> iter = matrix.createCoordinateIterator();
        while (iter.hasNext()) {
            FMatrixSparse.CoordinateRealValue val = iter.next();
            out.printf(lineFormat, val.row + 1, val.col + 1, Float.valueOf(val.value));
        }
        out.flush();
    }

    public static DMatrixSparseTriplet loadMatrixMarketD(Reader reader) {
        DMatrixSparseTriplet output = new DMatrixSparseTriplet();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            boolean hasHeader = false;
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.length() == 0 || line.charAt(0) == '%') {
                    line = bufferedReader.readLine();
                    continue;
                }
                String[] words = line.trim().split("\\s+");
                if (words.length != 3) {
                    throw new IOException("Unexpected number of words: " + words.length);
                }
                if (hasHeader) {
                    int row = Integer.parseInt(words[0]) - 1;
                    int col = Integer.parseInt(words[1]) - 1;
                    double value = Double.parseDouble(words[2]);
                    output.addItem(row, col, value);
                } else {
                    int rows = Integer.parseInt(words[0]);
                    int cols = Integer.parseInt(words[1]);
                    int nz_length = Integer.parseInt(words[2]);
                    output.reshape(rows, cols, nz_length);
                    hasHeader = true;
                }
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public static FMatrixSparseTriplet loadMatrixMarketF(Reader reader) {
        FMatrixSparseTriplet output = new FMatrixSparseTriplet();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            boolean hasHeader = false;
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.length() == 0 || line.charAt(0) == '%') {
                    line = bufferedReader.readLine();
                    continue;
                }
                String[] words = line.trim().split("\\s+");
                if (words.length != 3) {
                    throw new IOException("Unexpected number of words: " + words.length);
                }
                if (hasHeader) {
                    int row = Integer.parseInt(words[0]) - 1;
                    int col = Integer.parseInt(words[1]) - 1;
                    float value = Float.parseFloat(words[2]);
                    output.addItem(row, col, value);
                } else {
                    int rows = Integer.parseInt(words[0]);
                    int cols = Integer.parseInt(words[1]);
                    int nz_length = Integer.parseInt(words[2]);
                    output.reshape(rows, cols, nz_length);
                    hasHeader = true;
                }
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public static FMatrixSparseTriplet loadMatrixMarketF(InputStream streamIn) {
        FMatrixSparseTriplet output = new FMatrixSparseTriplet();
        BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn, StandardCharsets.UTF_8));
        try {
            boolean hasHeader = false;
            String line = reader.readLine();
            while (line != null) {
                if (line.length() == 0 || line.charAt(0) == '%') continue;
                String[] words = line.trim().split("\\s");
                if (words.length != 3) {
                    throw new IOException("Unexpected number of words: " + words.length);
                }
                if (hasHeader) {
                    int row = Integer.parseInt(words[0]);
                    int col = Integer.parseInt(words[1]);
                    float value = Float.parseFloat(words[2]);
                    output.addItem(row, col, value);
                } else {
                    int rows = Integer.parseInt(words[0]);
                    int cols = Integer.parseInt(words[1]);
                    int nz_length = Integer.parseInt(words[2]);
                    output.reshape(rows, cols, nz_length);
                    hasHeader = true;
                }
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public static void saveMatlab(Matrix A, String fileName) throws IOException {
        MflAccess.verifyEnabled();
        MflAccess.IO.saveMatlab(A, fileName);
    }

    public static <T extends Matrix> T loadMatlab(String fileName) throws IOException {
        return MatrixIO.loadMatlab(fileName, null);
    }

    public static <T extends Matrix> T loadMatlab(String fileName, @Nullable T output) throws IOException {
        MflAccess.verifyEnabled();
        return MflAccess.IO.loadMatlab(fileName, output);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void saveBin(DMatrix A, String fileName) throws IOException {
        FileOutputStream fileStream = new FileOutputStream(fileName);
        ObjectOutputStream stream = new ObjectOutputStream(fileStream);
        try {
            stream.writeObject(A);
            stream.flush();
        }
        finally {
            try {
                stream.close();
            }
            finally {
                fileStream.close();
            }
        }
    }

    @Deprecated
    public static <T extends DMatrix> T loadBin(String fileName) throws IOException {
        DMatrix ret;
        FileInputStream fileStream = new FileInputStream(fileName);
        ObjectInputStream stream = new ObjectInputStream(fileStream);
        try {
            ret = (DMatrix)stream.readObject();
            if (stream.available() != 0) {
                throw new RuntimeException("File not completely read?");
            }
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        stream.close();
        return (T)ret;
    }

    public static void saveDenseCSV(DMatrix A, String fileName) throws IOException {
        PrintStream fileStream = new PrintStream(fileName);
        fileStream.println(A.getNumRows() + " " + A.getNumCols() + " real");
        for (int i = 0; i < A.getNumRows(); ++i) {
            for (int j = 0; j < A.getNumCols(); ++j) {
                fileStream.print(A.get(i, j) + " ");
            }
            fileStream.println();
        }
        fileStream.close();
    }

    public static void saveSparseCSV(DMatrixSparseTriplet A, String fileName) throws IOException {
        PrintStream fileStream = new PrintStream(fileName);
        fileStream.println(A.getNumRows() + " " + A.getNumCols() + " " + A.nz_length + " real");
        for (int i = 0; i < A.nz_length; ++i) {
            int row = A.nz_rowcol.data[i * 2];
            int col = A.nz_rowcol.data[i * 2 + 1];
            double value = A.nz_value.data[i];
            fileStream.println(row + " " + col + " " + value);
        }
        fileStream.close();
    }

    public static void saveSparseCSV(FMatrixSparseTriplet A, String fileName) throws IOException {
        PrintStream fileStream = new PrintStream(fileName);
        fileStream.println(A.getNumRows() + " " + A.getNumCols() + " " + A.nz_length + " real");
        for (int i = 0; i < A.nz_length; ++i) {
            int row = A.nz_rowcol.data[i * 2];
            int col = A.nz_rowcol.data[i * 2 + 1];
            float value = A.nz_value.data[i];
            fileStream.println(row + " " + col + " " + value);
        }
        fileStream.close();
    }

    public static <T extends DMatrix> T loadCSV(String fileName, boolean doublePrecision) throws IOException {
        FileInputStream fileStream = new FileInputStream(fileName);
        ReadMatrixCsv csv = new ReadMatrixCsv(fileStream);
        DMatrix ret = doublePrecision ? (DMatrix)csv.read64() : (DMatrix)csv.read32();
        fileStream.close();
        return (T)ret;
    }

    public static DMatrixRMaj loadCSV(String fileName, int numRows, int numCols) throws IOException {
        FileInputStream fileStream = new FileInputStream(fileName);
        ReadMatrixCsv csv = new ReadMatrixCsv(fileStream);
        DMatrixRMaj ret = csv.readDDRM(numRows, numCols);
        fileStream.close();
        return ret;
    }

    public static void printFancy(PrintStream out, DMatrix mat, int length) {
        MatrixIO.printTypeSize(out, mat);
        DecimalFormat format = new DecimalFormat("#");
        int cols = mat.getNumCols();
        for (int row = 0; row < mat.getNumRows(); ++row) {
            for (int col = 0; col < cols; ++col) {
                out.print(UtilEjml.fancyStringF(mat.get(row, col), format, length, 4));
                if (col == cols - 1) continue;
                out.print(" ");
            }
            out.println();
        }
    }

    public static void printFancy(PrintStream out, FMatrix mat, int length) {
        MatrixIO.printTypeSize(out, mat);
        DecimalFormat format = new DecimalFormat("#");
        int cols = mat.getNumCols();
        for (int row = 0; row < mat.getNumRows(); ++row) {
            for (int col = 0; col < cols; ++col) {
                out.print(UtilEjml.fancyStringF(mat.get(row, col), format, length, 4));
                if (col == cols - 1) continue;
                out.print(" ");
            }
            out.println();
        }
    }

    public static void printFancy(PrintStream out, ZMatrix mat, int length) {
        MatrixIO.printTypeSize(out, mat);
        DecimalFormat format = new DecimalFormat("#");
        StringBuilder builder = new StringBuilder(length);
        int cols = mat.getNumCols();
        Complex_F64 c = new Complex_F64();
        for (int y = 0; y < mat.getNumRows(); ++y) {
            for (int x = 0; x < cols; ++x) {
                mat.get(y, x, c);
                String real = UtilEjml.fancyString(c.real, format, length, 4);
                String img = UtilEjml.fancyString(c.imaginary, format, length, 4);
                real = real + MatrixIO.padSpace(builder, length - real.length());
                img = img + "i" + MatrixIO.padSpace(builder, length - img.length());
                out.print(real + " + " + img);
                if (x >= mat.getNumCols() - 1) continue;
                out.print(" , ");
            }
            out.println();
        }
    }

    public static void printFancy(PrintStream out, CMatrix mat, int length) {
        MatrixIO.printTypeSize(out, mat);
        DecimalFormat format = new DecimalFormat("#");
        StringBuilder builder = new StringBuilder(length);
        int cols = mat.getNumCols();
        Complex_F32 c = new Complex_F32();
        for (int y = 0; y < mat.getNumRows(); ++y) {
            for (int x = 0; x < cols; ++x) {
                mat.get(y, x, c);
                String real = UtilEjml.fancyString(c.real, format, length, 4);
                String img = UtilEjml.fancyString(c.imaginary, format, length, 4);
                real = real + MatrixIO.padSpace(builder, length - real.length());
                img = img + MatrixIO.padSpace(builder, length - img.length());
                out.print(real + " + " + img + "i ");
                if (x >= mat.getNumCols() - 1) continue;
                out.print(" , ");
            }
            out.println();
        }
    }

    private static String padSpace(StringBuilder builder, int length) {
        builder.delete(0, builder.length());
        for (int i = 0; i < length; ++i) {
            builder.append(' ');
        }
        return builder.toString();
    }

    public static void printFancy(PrintStream out, DMatrixSparseCSC m, int length) {
        DecimalFormat format = new DecimalFormat("#");
        MatrixIO.printTypeSize(out, m);
        char[] zero = new char[length];
        Arrays.fill(zero, ' ');
        zero[length / 2] = 42;
        for (int row = 0; row < m.numRows; ++row) {
            for (int col = 0; col < m.numCols; ++col) {
                int index = m.nz_index(row, col);
                if (index >= 0) {
                    out.print(UtilEjml.fancyStringF(m.nz_values[index], format, length, 4));
                } else {
                    out.print(zero);
                }
                if (col == m.numCols - 1) continue;
                out.print(" ");
            }
            out.println();
        }
    }

    public static void print(PrintStream out, Matrix mat) {
        String format = DEFAULT_FLOAT_FORMAT;
        switch (mat.getType()) {
            case DDRM: {
                MatrixIO.print(out, (DMatrix)mat, format);
                break;
            }
            case FDRM: {
                MatrixIO.print(out, (FMatrix)mat, format);
                break;
            }
            case ZDRM: {
                MatrixIO.print(out, (ZMatrix)mat, format);
                break;
            }
            case CDRM: {
                MatrixIO.print(out, (CMatrix)mat, format);
                break;
            }
            case DSCC: {
                MatrixIO.print(out, (DMatrixSparseCSC)mat, format);
                break;
            }
            case DTRIPLET: {
                MatrixIO.print(out, (DMatrixSparseTriplet)mat, format);
                break;
            }
            case FSCC: {
                MatrixIO.print(out, (FMatrixSparseCSC)mat, format);
                break;
            }
            case FTRIPLET: {
                MatrixIO.print(out, (FMatrixSparseTriplet)mat, format);
                break;
            }
            default: {
                throw new RuntimeException("Unknown type " + (Object)((Object)mat.getType()));
            }
        }
    }

    public static void print(PrintStream out, DMatrix mat) {
        MatrixIO.print(out, mat, DEFAULT_FLOAT_FORMAT);
    }

    public static void print(PrintStream out, DMatrix mat, String format) {
        if (format.equalsIgnoreCase("matlab")) {
            MatrixIO.printMatlab(out, mat);
        } else if (format.equalsIgnoreCase("java")) {
            MatrixIO.printJava(out, mat, format);
        } else {
            MatrixIO.printTypeSize(out, mat);
            format = format + " ";
            for (int row = 0; row < mat.getNumRows(); ++row) {
                for (int col = 0; col < mat.getNumCols(); ++col) {
                    out.printf(format, mat.get(row, col));
                }
                out.println();
            }
        }
    }

    public static void printMatlab(PrintStream out, DMatrix mat) {
        out.print("[ ");
        for (int row = 0; row < mat.getNumRows(); ++row) {
            for (int col = 0; col < mat.getNumCols(); ++col) {
                out.printf("%.12E", mat.get(row, col));
                if (col + 1 >= mat.getNumCols()) continue;
                out.print(" , ");
            }
            if (row + 1 < mat.getNumRows()) {
                out.println(" ;");
                continue;
            }
            out.println(" ]");
        }
    }

    public static void printMatlab(PrintStream out, FMatrix mat) {
        out.print("[ ");
        for (int row = 0; row < mat.getNumRows(); ++row) {
            for (int col = 0; col < mat.getNumCols(); ++col) {
                out.printf(MATLAB_FORMAT, Float.valueOf(mat.get(row, col)));
                if (col + 1 >= mat.getNumCols()) continue;
                out.print(" , ");
            }
            if (row + 1 < mat.getNumRows()) {
                out.println(" ;");
                continue;
            }
            out.println(" ]");
        }
    }

    public static void print(PrintStream out, DMatrixSparseCSC m, String format) {
        if (format.equalsIgnoreCase("matlab")) {
            MatrixIO.printMatlab(out, m);
        } else {
            MatrixIO.printTypeSize(out, m);
            int length = String.format(format, -1.1123).length();
            char[] zero = new char[length];
            Arrays.fill(zero, ' ');
            zero[length / 2] = 42;
            for (int row = 0; row < m.numRows; ++row) {
                for (int col = 0; col < m.numCols; ++col) {
                    int index = m.nz_index(row, col);
                    if (index >= 0) {
                        out.printf(format, m.nz_values[index]);
                    } else {
                        out.print(zero);
                    }
                    if (col == m.numCols - 1) continue;
                    out.print(" ");
                }
                out.println();
            }
        }
    }

    public static void print(PrintStream out, FMatrixSparseCSC m, String format) {
        if (format.equalsIgnoreCase("matlab")) {
            MatrixIO.printMatlab(out, m);
        } else {
            MatrixIO.printTypeSize(out, m);
            int length = String.format(format, -1.1123).length();
            char[] zero = new char[length];
            Arrays.fill(zero, ' ');
            zero[length / 2] = 42;
            for (int row = 0; row < m.numRows; ++row) {
                for (int col = 0; col < m.numCols; ++col) {
                    int index = m.nz_index(row, col);
                    if (index >= 0) {
                        out.printf(format, Float.valueOf(m.nz_values[index]));
                    } else {
                        out.print(zero);
                    }
                    if (col == m.numCols - 1) continue;
                    out.print(" ");
                }
                out.println();
            }
        }
    }

    public static void print(PrintStream out, DMatrixSparseTriplet m, String format) {
        MatrixIO.printTypeSize(out, m);
        for (int row = 0; row < m.numRows; ++row) {
            for (int col = 0; col < m.numCols; ++col) {
                int index = m.nz_index(row, col);
                if (index >= 0) {
                    out.printf(format, m.nz_value.data[index]);
                } else {
                    out.print("   *  ");
                }
                if (col == m.numCols - 1) continue;
                out.print(" ");
            }
            out.println();
        }
    }

    public static void print(PrintStream out, FMatrixSparseTriplet m, String format) {
        MatrixIO.printTypeSize(out, m);
        for (int row = 0; row < m.numRows; ++row) {
            for (int col = 0; col < m.numCols; ++col) {
                int index = m.nz_index(row, col);
                if (index >= 0) {
                    out.printf(format, Float.valueOf(m.nz_value.data[index]));
                } else {
                    out.print("   *  ");
                }
                if (col == m.numCols - 1) continue;
                out.print(" ");
            }
            out.println();
        }
    }

    public static void printJava(PrintStream out, DMatrix mat, String format) {
        String type = mat.getType().getBits() == 64 ? "double" : "float";
        out.println("new " + type + "[][]{");
        format = format + " ";
        for (int y = 0; y < mat.getNumRows(); ++y) {
            out.print("{");
            for (int x = 0; x < mat.getNumCols(); ++x) {
                out.printf(format, mat.get(y, x));
                if (x + 1 >= mat.getNumCols()) continue;
                out.print(", ");
            }
            if (y + 1 < mat.getNumRows()) {
                out.println("},");
                continue;
            }
            out.println("}};");
        }
    }

    public static void print(PrintStream out, FMatrix mat) {
        MatrixIO.print(out, mat, DEFAULT_FLOAT_FORMAT);
    }

    public static void print(PrintStream out, FMatrix mat, String format) {
        if (format.equalsIgnoreCase("matlab")) {
            MatrixIO.printMatlab(out, mat);
        } else if (format.equalsIgnoreCase("java")) {
            MatrixIO.printJava(out, mat, format);
        } else {
            MatrixIO.printTypeSize(out, mat);
            format = format + " ";
            for (int row = 0; row < mat.getNumRows(); ++row) {
                for (int col = 0; col < mat.getNumCols(); ++col) {
                    out.printf(format, Float.valueOf(mat.get(row, col)));
                }
                out.println();
            }
        }
    }

    public static void print(PrintStream out, DMatrix mat, String format, int row0, int row1, int col0, int col1) {
        out.println("Type = submatrix , rows " + row0 + " to " + row1 + "  columns " + col0 + " to " + col1);
        format = format + " ";
        for (int y = row0; y < row1; ++y) {
            for (int x = col0; x < col1; ++x) {
                out.printf(format, mat.get(y, x));
            }
            out.println();
        }
    }

    public static void printJava(PrintStream out, FMatrix mat, String format) {
        String type = mat.getType().getBits() == 64 ? "double" : "float";
        out.println("new " + type + "[][]{");
        format = format + " ";
        for (int y = 0; y < mat.getNumRows(); ++y) {
            out.print("{");
            for (int x = 0; x < mat.getNumCols(); ++x) {
                out.printf(format, Float.valueOf(mat.get(y, x)));
                if (x + 1 >= mat.getNumCols()) continue;
                out.print(", ");
            }
            if (y + 1 < mat.getNumRows()) {
                out.println("},");
                continue;
            }
            out.println("}};");
        }
    }

    public static void print(PrintStream out, FMatrix mat, String format, int row0, int row1, int col0, int col1) {
        out.println("Type = submatrix , rows " + row0 + " to " + row1 + "  columns " + col0 + " to " + col1);
        format = format + " + " + format + "i";
        for (int y = row0; y < row1; ++y) {
            for (int x = col0; x < col1; ++x) {
                out.printf(format, Float.valueOf(mat.get(y, x)));
            }
            out.println();
        }
    }

    public static void print(PrintStream out, ZMatrix mat, String format) {
        MatrixIO.printTypeSize(out, mat);
        format = format + " + " + format + "i";
        Complex_F64 c = new Complex_F64();
        for (int y = 0; y < mat.getNumRows(); ++y) {
            for (int x = 0; x < mat.getNumCols(); ++x) {
                mat.get(y, x, c);
                out.printf(format, c.real, c.imaginary);
                if (x >= mat.getNumCols() - 1) continue;
                out.print(" , ");
            }
            out.println();
        }
    }

    private static void printTypeSize(PrintStream out, Matrix mat) {
        if (mat instanceof MatrixSparse) {
            MatrixSparse m = (MatrixSparse)mat;
            out.println("Type = " + MatrixIO.getMatrixType(mat) + " , rows = " + mat.getNumRows() + " , cols = " + mat.getNumCols() + " , nz_length = " + m.getNonZeroLength());
        } else {
            out.println("Type = " + MatrixIO.getMatrixType(mat) + " , rows = " + mat.getNumRows() + " , cols = " + mat.getNumCols());
        }
    }

    public static void print(PrintStream out, CMatrix mat, String format) {
        MatrixIO.printTypeSize(out, mat);
        format = format + " ";
        Complex_F32 c = new Complex_F32();
        for (int y = 0; y < mat.getNumRows(); ++y) {
            for (int x = 0; x < mat.getNumCols(); ++x) {
                mat.get(y, x, c);
                out.printf(format, Float.valueOf(c.real), Float.valueOf(c.imaginary));
                if (x >= mat.getNumCols() - 1) continue;
                out.print(" , ");
            }
            out.println();
        }
    }

    private static String getMatrixType(Matrix mat) {
        String type = mat.getType() == MatrixType.UNSPECIFIED ? mat.getClass().getSimpleName() : mat.getType().name();
        return type;
    }

    static class MflAccess {
        private static final boolean ENABLED;

        MflAccess() {
        }

        private static void verifyEnabled() {
            if (!ENABLED) {
                throw new IllegalStateException("Missing dependency: add maven coordinates 'us.hebi.matlab.mat:mfl-ejml:0.5.7' or later. https://github.com/HebiRobotics/MFL");
            }
        }

        static {
            boolean foundInClasspath;
            try {
                Class<Mat5Ejml> clazz = Mat5Ejml.class;
                foundInClasspath = true;
            }
            catch (NoClassDefFoundError cfe) {
                foundInClasspath = false;
            }
            ENABLED = foundInClasspath;
        }

        static class IO {
            private static final String ENTRY_NAME = "ejmlMatrix";

            IO() {
            }

            public static void saveMatlab(Matrix A, String fileName) throws IOException {
                MatFile mat = Mat5.newMatFile().addArray(ENTRY_NAME, Mat5Ejml.asArray((Matrix)A));
                Mat5.writeToFile((MatFile)mat, (String)fileName);
            }

            public static <T extends Matrix> T loadMatlab(String fileName, @Nullable T output) throws IOException {
                Mat5File mat = Mat5.readFromFile((String)fileName);
                for (MatFile.Entry entry : mat.getEntries()) {
                    if (!ENTRY_NAME.matches(entry.getName())) continue;
                    return (T)Mat5Ejml.convert((Array)entry.getValue(), output);
                }
                throw new IllegalArgumentException("File does not have expected entry: 'ejmlMatrix'");
            }
        }
    }
}

