/*
 * Decompiled with CFR 0.150.
 */
package org.ejml.ops;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseTriplet;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseTriplet;
import org.ejml.data.Matrix;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.ops.ReadCsv;

public class ReadMatrixCsv
extends ReadCsv {
    public ReadMatrixCsv(InputStream in) {
        super(in);
    }

    public <M extends Matrix> M read32() throws IOException {
        List<String> words = this.extractWords();
        if (words == null) {
            throw new IllegalArgumentException("Not enough lines in file");
        }
        if (words.size() == 3) {
            boolean real;
            int numRows = Integer.parseInt(words.get(0));
            int numCols = Integer.parseInt(words.get(1));
            boolean bl = real = words.get(2).compareToIgnoreCase("real") == 0;
            if (numRows < 0 || numCols < 0) {
                throw new IOException("Invalid number of rows and/or columns: " + numRows + " " + numCols);
            }
            if (real) {
                return (M)this.readFDRM(numRows, numCols);
            }
            return (M)this.readCDRM(numRows, numCols);
        }
        if (words.size() == 4) {
            boolean real;
            int numRows = Integer.parseInt(words.get(0));
            int numCols = Integer.parseInt(words.get(1));
            int length = Integer.parseInt(words.get(2));
            boolean bl = real = words.get(3).compareToIgnoreCase("real") == 0;
            if (numRows < 0 || numCols < 0) {
                throw new IOException("Invalid number of rows and/or columns: " + numRows + " " + numCols);
            }
            if (real) {
                return (M)this.readFSTR(numRows, numCols, length);
            }
            throw new IllegalArgumentException("Sparse complex not yet supported");
        }
        throw new IOException("Unexpected number of words on the first line. Found " + words.size());
    }

    public <M extends Matrix> M read64() throws IOException {
        List<String> words = this.extractWords();
        if (words == null) {
            throw new IllegalArgumentException("Not enough lines in file");
        }
        if (words.size() == 3) {
            boolean real;
            int numRows = Integer.parseInt(words.get(0));
            int numCols = Integer.parseInt(words.get(1));
            boolean bl = real = words.get(2).compareToIgnoreCase("real") == 0;
            if (numRows < 0 || numCols < 0) {
                throw new IOException("Invalid number of rows and/or columns: " + numRows + " " + numCols);
            }
            if (real) {
                return (M)this.readDDRM(numRows, numCols);
            }
            return (M)this.readZDRM(numRows, numCols);
        }
        if (words.size() == 4) {
            boolean real;
            int numRows = Integer.parseInt(words.get(0));
            int numCols = Integer.parseInt(words.get(1));
            int length = Integer.parseInt(words.get(2));
            boolean bl = real = words.get(3).compareToIgnoreCase("real") == 0;
            if (numRows < 0 || numCols < 0) {
                throw new IOException("Invalid number of rows and/or columns: " + numRows + " " + numCols);
            }
            if (real) {
                return (M)this.readDSTR(numRows, numCols, length);
            }
            throw new IllegalArgumentException("Sparse complex not yet supported");
        }
        throw new IOException("Unexpected number of words on the first line. Found " + words.size());
    }

    public DMatrixRMaj readDDRM(int numRows, int numCols) throws IOException {
        DMatrixRMaj A = new DMatrixRMaj(numRows, numCols);
        for (int i = 0; i < numRows; ++i) {
            List<String> words = this.extractWords();
            if (words == null) {
                throw new IOException("Too few rows found. expected " + numRows + " actual " + i);
            }
            if (words.size() != numCols) {
                throw new IOException("Unexpected number of words in column. Found " + words.size() + " expected " + numCols);
            }
            for (int j = 0; j < numCols; ++j) {
                A.set(i, j, Double.parseDouble(words.get(j)));
            }
        }
        return A;
    }

    public FMatrixRMaj readFDRM(int numRows, int numCols) throws IOException {
        FMatrixRMaj A = new FMatrixRMaj(numRows, numCols);
        for (int i = 0; i < numRows; ++i) {
            List<String> words = this.extractWords();
            if (words == null) {
                throw new IOException("Too few rows found. expected " + numRows + " actual " + i);
            }
            if (words.size() != numCols) {
                throw new IOException("Unexpected number of words in column. Found " + words.size() + " expected " + numCols);
            }
            for (int j = 0; j < numCols; ++j) {
                A.set(i, j, Float.parseFloat(words.get(j)));
            }
        }
        return A;
    }

    public ZMatrixRMaj readZDRM(int numRows, int numCols) throws IOException {
        ZMatrixRMaj A = new ZMatrixRMaj(numRows, numCols);
        int wordsCol = numCols * 2;
        for (int i = 0; i < numRows; ++i) {
            List<String> words = this.extractWords();
            if (words == null) {
                throw new IOException("Too few rows found. expected " + numRows + " actual " + i);
            }
            if (words.size() != wordsCol) {
                throw new IOException("Unexpected number of words in column. Found " + words.size() + " expected " + wordsCol);
            }
            for (int j = 0; j < wordsCol; j += 2) {
                double real = Double.parseDouble(words.get(j));
                double imaginary = Double.parseDouble(words.get(j + 1));
                A.set(i, j / 2, real, imaginary);
            }
        }
        return A;
    }

    public CMatrixRMaj readCDRM(int numRows, int numCols) throws IOException {
        CMatrixRMaj A = new CMatrixRMaj(numRows, numCols);
        int wordsCol = numCols * 2;
        for (int i = 0; i < numRows; ++i) {
            List<String> words = this.extractWords();
            if (words == null) {
                throw new IOException("Too few rows found. expected " + numRows + " actual " + i);
            }
            if (words.size() != wordsCol) {
                throw new IOException("Unexpected number of words in column. Found " + words.size() + " expected " + wordsCol);
            }
            for (int j = 0; j < wordsCol; j += 2) {
                float real = Float.parseFloat(words.get(j));
                float imaginary = Float.parseFloat(words.get(j + 1));
                A.set(i, j / 2, real, imaginary);
            }
        }
        return A;
    }

    private FMatrixSparseTriplet readFSTR(int numRows, int numCols, int length) throws IOException {
        FMatrixSparseTriplet m = new FMatrixSparseTriplet(numRows, numCols, length);
        for (int i = 0; i < length; ++i) {
            List<String> words = this.extractWords();
            if (words == null) {
                throw new IllegalArgumentException("Not enough lines in file");
            }
            if (words.size() != 3) {
                throw new IllegalArgumentException("Unexpected number of words on line " + this.getLineNumber());
            }
            int row = Integer.parseInt(words.get(0));
            int col = Integer.parseInt(words.get(1));
            float value = Float.parseFloat(words.get(2));
            m.addItem(row, col, value);
        }
        return m;
    }

    private DMatrixSparseTriplet readDSTR(int numRows, int numCols, int length) throws IOException {
        DMatrixSparseTriplet m = new DMatrixSparseTriplet(numRows, numCols, length);
        for (int i = 0; i < length; ++i) {
            List<String> words = this.extractWords();
            if (words == null) {
                throw new IllegalArgumentException("Not enough lines in file");
            }
            if (words.size() != 3) {
                throw new IllegalArgumentException("Unexpected number of words on line " + this.getLineNumber());
            }
            int row = Integer.parseInt(words.get(0));
            int col = Integer.parseInt(words.get(1));
            double value = Double.parseDouble(words.get(2));
            m.addItem(row, col, value);
        }
        return m;
    }
}

