/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package org.ejml.simple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.ejml.UtilEjml;
import org.ejml.data.CMatrixRMaj;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixIterator;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixRMaj;
import org.ejml.data.FMatrixSparseCSC;
import org.ejml.data.Matrix;
import org.ejml.data.MatrixType;
import org.ejml.data.ReshapeMatrix;
import org.ejml.data.SingularMatrixException;
import org.ejml.data.ZMatrixRMaj;
import org.ejml.equation.Equation;
import org.ejml.ops.ConvertMatrixType;
import org.ejml.ops.DConvertMatrixStruct;
import org.ejml.ops.FConvertMatrixStruct;
import org.ejml.ops.MatrixIO;
import org.ejml.simple.AutomaticSimpleMatrixConvert;
import org.ejml.simple.ConvertToDenseException;
import org.ejml.simple.SimpleEVD;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleOperations;
import org.ejml.simple.SimpleSVD;
import org.ejml.simple.ops.SimpleOperations_CDRM;
import org.ejml.simple.ops.SimpleOperations_DDRM;
import org.ejml.simple.ops.SimpleOperations_DSCC;
import org.ejml.simple.ops.SimpleOperations_FDRM;
import org.ejml.simple.ops.SimpleOperations_FSCC;
import org.ejml.simple.ops.SimpleOperations_ZDRM;
import org.jetbrains.annotations.Nullable;

public abstract class SimpleBase<T extends SimpleBase<T>>
implements Serializable {
    static final long serialVersionUID = 2342556642L;
    protected Matrix mat;
    protected SimpleOperations ops;
    protected transient AutomaticSimpleMatrixConvert convertType = new AutomaticSimpleMatrixConvert();

    protected SimpleBase(int numRows, int numCols) {
        this.setMatrix(new DMatrixRMaj(numRows, numCols));
    }

    protected SimpleBase() {
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.convertType = new AutomaticSimpleMatrixConvert();
    }

    protected abstract T createMatrix(int var1, int var2, MatrixType var3);

    protected abstract T wrapMatrix(Matrix var1);

    public <InnerType extends Matrix> InnerType getMatrix() {
        return (InnerType)this.mat;
    }

    public DMatrixRMaj getDDRM() {
        return this.mat.getType() == MatrixType.DDRM ? (DMatrixRMaj)this.mat : (DMatrixRMaj)ConvertMatrixType.convert(this.mat, MatrixType.DDRM);
    }

    public FMatrixRMaj getFDRM() {
        return this.mat.getType() == MatrixType.FDRM ? (FMatrixRMaj)this.mat : (FMatrixRMaj)ConvertMatrixType.convert(this.mat, MatrixType.FDRM);
    }

    public ZMatrixRMaj getZDRM() {
        return this.mat.getType() == MatrixType.ZDRM ? (ZMatrixRMaj)this.mat : (ZMatrixRMaj)ConvertMatrixType.convert(this.mat, MatrixType.ZDRM);
    }

    public CMatrixRMaj getCDRM() {
        return this.mat.getType() == MatrixType.CDRM ? (CMatrixRMaj)this.mat : (CMatrixRMaj)ConvertMatrixType.convert(this.mat, MatrixType.CDRM);
    }

    public DMatrixSparseCSC getDSCC() {
        return this.mat.getType() == MatrixType.DSCC ? (DMatrixSparseCSC)this.mat : (DMatrixSparseCSC)ConvertMatrixType.convert(this.mat, MatrixType.DSCC);
    }

    public FMatrixSparseCSC getFSCC() {
        return this.mat.getType() == MatrixType.FSCC ? (FMatrixSparseCSC)this.mat : (FMatrixSparseCSC)ConvertMatrixType.convert(this.mat, MatrixType.FSCC);
    }

    protected static SimpleOperations lookupOps(MatrixType type) {
        switch (type) {
            case DDRM: {
                return new SimpleOperations_DDRM();
            }
            case FDRM: {
                return new SimpleOperations_FDRM();
            }
            case ZDRM: {
                return new SimpleOperations_ZDRM();
            }
            case CDRM: {
                return new SimpleOperations_CDRM();
            }
            case DSCC: {
                return new SimpleOperations_DSCC();
            }
            case FSCC: {
                return new SimpleOperations_FSCC();
            }
        }
        throw new RuntimeException("Unknown Matrix Type. " + (Object)((Object)type));
    }

    public T transpose() {
        T ret = this.createMatrix(this.mat.getNumCols(), this.mat.getNumRows(), this.mat.getType());
        this.ops.transpose(this.mat, ((SimpleBase)ret).mat);
        return ret;
    }

    public T mult(T B) {
        this.convertType.specify(new SimpleBase[]{this, B});
        if (this.mat.getType() != ((SimpleBase)B).getType()) {
            Method m = this.findAlternative("mult", this.mat, ((SimpleBase)B).mat, this.convertType.commonType.getClassType());
            if (m != null) {
                T ret = this.wrapMatrix(this.convertType.commonType.create(1, 1));
                this.invoke(m, this.mat, ((SimpleBase)B).mat, ((SimpleBase)ret).mat);
                return ret;
            }
        }
        Object A = this.convertType.convert(this);
        B = this.convertType.convert((SimpleBase)B);
        T ret = ((SimpleBase)A).createMatrix(this.mat.getNumRows(), ((SimpleBase)B).getMatrix().getNumCols(), ((SimpleBase)A).getType());
        ((SimpleBase)A).ops.mult(((SimpleBase)A).mat, ((SimpleBase)B).mat, ((SimpleBase)ret).mat);
        return ret;
    }

    public T kron(T B) {
        this.convertType.specify(new SimpleBase[]{this, B});
        Object A = this.convertType.convert(this);
        B = this.convertType.convert((SimpleBase)B);
        T ret = ((SimpleBase)A).createMatrix(this.mat.getNumRows() * ((SimpleBase)B).numRows(), this.mat.getNumCols() * ((SimpleBase)B).numCols(), ((SimpleBase)A).getType());
        ((SimpleBase)A).ops.kron(((SimpleBase)A).mat, ((SimpleBase)B).mat, ((SimpleBase)ret).mat);
        return ret;
    }

    public T plus(T B) {
        this.convertType.specify(new SimpleBase[]{this, B});
        Object A = this.convertType.convert(this);
        B = this.convertType.convert((SimpleBase)B);
        T ret = ((SimpleBase)A).createMatrix(this.mat.getNumRows(), this.mat.getNumCols(), ((SimpleBase)A).getType());
        ((SimpleBase)A).ops.plus(((SimpleBase)A).mat, ((SimpleBase)B).mat, ((SimpleBase)ret).mat);
        return ret;
    }

    public T minus(T B) {
        this.convertType.specify(new SimpleBase[]{this, B});
        Object A = this.convertType.convert(this);
        B = this.convertType.convert((SimpleBase)B);
        T ret = ((SimpleBase)A).createLike();
        ((SimpleBase)A).ops.minus(((SimpleBase)A).mat, ((SimpleBase)B).mat, ((SimpleBase)ret).mat);
        return ret;
    }

    public T minus(double b) {
        T ret = this.createLike();
        this.ops.minus(this.mat, b, ((SimpleBase)ret).mat);
        return ret;
    }

    public T plus(double b) {
        T ret = this.createLike();
        this.ops.plus(this.mat, b, ((SimpleBase)ret).mat);
        return ret;
    }

    public T plus(double beta, T B) {
        this.convertType.specify(new SimpleBase[]{this, B});
        Object A = this.convertType.convert(this);
        B = this.convertType.convert((SimpleBase)B);
        T ret = ((SimpleBase)A).createLike();
        ((SimpleBase)A).ops.plus(((SimpleBase)A).mat, beta, ((SimpleBase)B).mat, ((SimpleBase)ret).mat);
        return ret;
    }

    public double dot(T v) {
        this.convertType.specify(new SimpleBase[]{this, v});
        Object A = this.convertType.convert(this);
        v = this.convertType.convert((SimpleBase)v);
        if (!this.isVector()) {
            throw new IllegalArgumentException("'this' matrix is not a vector.");
        }
        if (!((SimpleBase)v).isVector()) {
            throw new IllegalArgumentException("'v' matrix is not a vector.");
        }
        return ((SimpleBase)A).ops.dot(((SimpleBase)A).mat, ((SimpleBase)v).getMatrix());
    }

    public boolean isVector() {
        return this.mat.getNumRows() == 1 || this.mat.getNumCols() == 1;
    }

    public T scale(double val) {
        T ret = this.createLike();
        this.ops.scale(this.mat, val, ((SimpleBase)ret).getMatrix());
        return ret;
    }

    public T divide(double val) {
        T ret = this.createLike();
        this.ops.divide(this.mat, val, ((SimpleBase)ret).getMatrix());
        return ret;
    }

    public T invert() {
        T ret = this.createLike();
        if (!this.ops.invert(this.mat, ((SimpleBase)ret).mat)) {
            throw new SingularMatrixException();
        }
        if (this.ops.hasUncountable(((SimpleBase)ret).mat)) {
            throw new SingularMatrixException("Solution contains uncountable numbers");
        }
        return ret;
    }

    public T pseudoInverse() {
        T ret = this.createLike();
        this.ops.pseudoInverse(this.mat, ((SimpleBase)ret).mat);
        return ret;
    }

    public T solve(T B) {
        this.convertType.specify(new SimpleBase[]{this, B});
        if (this.mat.getType() != ((SimpleBase)B).getType()) {
            Method m = this.findAlternative("solve", this.mat, ((SimpleBase)B).mat, this.convertType.commonType.getClassType());
            if (m != null) {
                T ret = this.wrapMatrix(this.convertType.commonType.create(1, 1));
                this.invoke(m, this.mat, ((SimpleBase)B).mat, ((SimpleBase)ret).mat);
                return ret;
            }
        }
        Object A = this.convertType.convert(this);
        B = this.convertType.convert((SimpleBase)B);
        T x = ((SimpleBase)A).createMatrix(this.mat.getNumCols(), ((SimpleBase)B).getMatrix().getNumCols(), ((SimpleBase)A).getType());
        if (!((SimpleBase)A).ops.solve(((SimpleBase)A).mat, ((SimpleBase)x).mat, ((SimpleBase)B).mat)) {
            throw new SingularMatrixException();
        }
        if (((SimpleBase)A).ops.hasUncountable(((SimpleBase)x).mat)) {
            throw new SingularMatrixException("Solution contains uncountable numbers");
        }
        return x;
    }

    public void setTo(T a) {
        if (((SimpleBase)a).getType() == this.getType()) {
            this.mat.setTo((Matrix)((SimpleBase)a).getMatrix());
        } else {
            this.setMatrix((Matrix)((SimpleBase)a).mat.copy());
        }
    }

    public void fill(double val) {
        try {
            this.ops.fill(this.mat, val);
        }
        catch (ConvertToDenseException e) {
            this.convertToDense();
            this.fill(val);
        }
    }

    public void zero() {
        this.fill(0.0);
    }

    public double normF() {
        return this.ops.normF(this.mat);
    }

    public double conditionP2() {
        return this.ops.conditionP2(this.mat);
    }

    public double determinant() {
        double ret = this.ops.determinant(this.mat);
        if (UtilEjml.isUncountable(ret)) {
            return 0.0;
        }
        return ret;
    }

    public double trace() {
        return this.ops.trace(this.mat);
    }

    public void reshape(int numRows, int numCols) {
        if (this.mat.getType().isFixed()) {
            throw new IllegalArgumentException("Can't reshape a fixed sized matrix");
        }
        ((ReshapeMatrix)this.mat).reshape(numRows, numCols);
    }

    public void set(int row, int col, double value) {
        this.ops.set(this.mat, row, col, value);
    }

    public void set(int index, double value) {
        if (this.mat.getType() == MatrixType.DDRM) {
            ((DMatrixRMaj)this.mat).set(index, value);
        } else if (this.mat.getType() == MatrixType.FDRM) {
            ((FMatrixRMaj)this.mat).set(index, (float)value);
        } else {
            throw new RuntimeException("Not supported yet for this matrix type");
        }
    }

    public void set(int row, int col, double real, double imaginary) {
        if (imaginary == 0.0) {
            this.set(row, col, real);
        } else {
            this.ops.set(this.mat, row, col, real, imaginary);
        }
    }

    public void setRow(int row, int startColumn, double ... values) {
        this.ops.setRow(this.mat, row, startColumn, values);
    }

    public void setColumn(int column, int startRow, double ... values) {
        this.ops.setColumn(this.mat, column, startRow, values);
    }

    public double get(int row, int col) {
        return this.ops.get(this.mat, row, col);
    }

    public double get(int index) {
        MatrixType type = this.mat.getType();
        if (type.isReal()) {
            if (type.getBits() == 64) {
                return ((DMatrixRMaj)this.mat).data[index];
            }
            return ((FMatrixRMaj)this.mat).data[index];
        }
        throw new IllegalArgumentException("Complex matrix. Call get(int,Complex64F) instead");
    }

    public void get(int row, int col, Complex_F64 output) {
        this.ops.get(this.mat, row, col, output);
    }

    public int getIndex(int row, int col) {
        return row * this.mat.getNumCols() + col;
    }

    public DMatrixIterator iterator(boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol) {
        return new DMatrixIterator((DMatrixRMaj)this.mat, rowMajor, minRow, minCol, maxRow, maxCol);
    }

    public T copy() {
        T ret = this.createLike();
        ((SimpleBase)ret).getMatrix().setTo((Matrix)this.getMatrix());
        return ret;
    }

    public int numRows() {
        return this.mat.getNumRows();
    }

    public int numCols() {
        return this.mat.getNumCols();
    }

    public int getNumElements() {
        return this.mat.getNumCols() * this.mat.getNumRows();
    }

    public void print() {
        this.mat.print();
    }

    public void print(String format) {
        this.ops.print(System.out, this.mat, format);
    }

    public String toString() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(stream);
        MatrixIO.print(p, this.mat);
        return stream.toString();
    }

    public T extractMatrix(int y0, int y1, int x0, int x1) {
        if (y0 == Integer.MAX_VALUE) {
            y0 = this.mat.getNumRows();
        }
        if (y1 == Integer.MAX_VALUE) {
            y1 = this.mat.getNumRows();
        }
        if (x0 == Integer.MAX_VALUE) {
            x0 = this.mat.getNumCols();
        }
        if (x1 == Integer.MAX_VALUE) {
            x1 = this.mat.getNumCols();
        }
        T ret = this.createMatrix(y1 - y0, x1 - x0, this.mat.getType());
        this.ops.extract(this.mat, y0, y1, x0, x1, ((SimpleBase)ret).mat, 0, 0);
        return ret;
    }

    public T extractVector(boolean extractRow, int element) {
        if (extractRow) {
            return this.extractMatrix(element, element + 1, 0, Integer.MAX_VALUE);
        }
        return this.extractMatrix(0, Integer.MAX_VALUE, element, element + 1);
    }

    public T diag() {
        return this.wrapMatrix(this.ops.diag(this.mat));
    }

    public boolean isIdentical(T a, double tol) {
        if (((SimpleBase)a).getType() != this.getType()) {
            return false;
        }
        return this.ops.isIdentical(this.mat, ((SimpleBase)a).mat, tol);
    }

    public boolean hasUncountable() {
        return this.ops.hasUncountable(this.mat);
    }

    public SimpleSVD<T> svd() {
        return new SimpleSVD(this.mat, false);
    }

    public SimpleSVD<T> svd(boolean compact) {
        return new SimpleSVD(this.mat, compact);
    }

    public SimpleEVD<T> eig() {
        return new SimpleEVD(this.mat);
    }

    public void insertIntoThis(int insertRow, int insertCol, T B) {
        this.convertType.specify(new SimpleBase[]{this, B});
        B = this.convertType.convert((SimpleBase)B);
        if (this.convertType.commonType == this.getType()) {
            this.insert(((SimpleBase)B).mat, this.mat, insertRow, insertCol);
        } else {
            Object A = this.convertType.convert(this);
            ((SimpleBase)A).insert(((SimpleBase)B).mat, ((SimpleBase)A).mat, insertRow, insertCol);
            this.setMatrix(((SimpleBase)A).mat);
        }
    }

    void insert(Matrix src, Matrix dst, int destY0, int destX0) {
        this.ops.extract(src, 0, src.getNumRows(), 0, src.getNumCols(), dst, destY0, destX0);
    }

    public T combine(int insertRow, int insertCol, T B) {
        T ret;
        this.convertType.specify(new SimpleBase[]{this, B});
        Object A = this.convertType.convert(this);
        B = this.convertType.convert((SimpleBase)B);
        if (insertRow == Integer.MAX_VALUE) {
            insertRow = this.mat.getNumRows();
        }
        if (insertCol == Integer.MAX_VALUE) {
            insertCol = this.mat.getNumCols();
        }
        int maxRow = insertRow + ((SimpleBase)B).numRows();
        int maxCol = insertCol + ((SimpleBase)B).numCols();
        if (maxRow > this.mat.getNumRows() || maxCol > this.mat.getNumCols()) {
            int M = Math.max(maxRow, this.mat.getNumRows());
            int N = Math.max(maxCol, this.mat.getNumCols());
            ret = ((SimpleBase)A).createMatrix(M, N, ((SimpleBase)A).getType());
            ((SimpleBase)ret).insertIntoThis(0, 0, A);
        } else {
            ret = ((SimpleBase)A).copy();
        }
        ((SimpleBase)ret).insertIntoThis(insertRow, insertCol, B);
        return ret;
    }

    public double elementMaxAbs() {
        return this.ops.elementMaxAbs(this.mat);
    }

    public double elementMinAbs() {
        return this.ops.elementMinAbs(this.mat);
    }

    public double elementSum() {
        return this.ops.elementSum(this.mat);
    }

    public T elementMult(T b) {
        this.convertType.specify(new SimpleBase[]{this, b});
        Object A = this.convertType.convert(this);
        b = this.convertType.convert((SimpleBase)b);
        T c = ((SimpleBase)A).createLike();
        ((SimpleBase)A).ops.elementMult(((SimpleBase)A).mat, ((SimpleBase)b).mat, ((SimpleBase)c).mat);
        return c;
    }

    public T elementDiv(T b) {
        this.convertType.specify(new SimpleBase[]{this, b});
        Object A = this.convertType.convert(this);
        b = this.convertType.convert((SimpleBase)b);
        T c = ((SimpleBase)A).createLike();
        ((SimpleBase)A).ops.elementDiv(((SimpleBase)A).mat, ((SimpleBase)b).mat, ((SimpleBase)c).mat);
        return c;
    }

    public T elementPower(T b) {
        this.convertType.specify(new SimpleBase[]{this, b});
        Object A = this.convertType.convert(this);
        b = this.convertType.convert((SimpleBase)b);
        T c = ((SimpleBase)A).createLike();
        ((SimpleBase)A).ops.elementPower(((SimpleBase)A).mat, ((SimpleBase)b).mat, ((SimpleBase)c).mat);
        return c;
    }

    public T elementPower(double b) {
        T c = this.createLike();
        this.ops.elementPower(this.mat, b, ((SimpleBase)c).mat);
        return c;
    }

    public T elementExp() {
        T c = this.createLike();
        this.ops.elementExp(this.mat, ((SimpleBase)c).mat);
        return c;
    }

    public T elementLog() {
        T c = this.createLike();
        this.ops.elementLog(this.mat, ((SimpleBase)c).mat);
        return c;
    }

    public T negative() {
        T A = this.copy();
        this.ops.changeSign(((SimpleBase)A).mat);
        return A;
    }

    public void equation(String equation, Object ... variables) {
        if (variables.length >= 25) {
            throw new IllegalArgumentException("Too many variables!  At most 25");
        }
        if (!(this.mat instanceof DMatrixRMaj)) {
            return;
        }
        Equation eq = new Equation();
        String nameThis = "A";
        int offset = 0;
        if (variables.length > 0 && variables[0] instanceof String) {
            nameThis = (String)variables[0];
            offset = 1;
            if (variables.length % 2 != 1) {
                throw new IllegalArgumentException("Expected and odd length for variables");
            }
        } else if (variables.length % 2 != 0) {
            throw new IllegalArgumentException("Expected and even length for variables");
        }
        eq.alias((DMatrixRMaj)this.mat, nameThis);
        for (int i = offset; i < variables.length; i += 2) {
            if (!(variables[i + 1] instanceof String)) {
                throw new IllegalArgumentException("String expected at variables index " + i);
            }
            Object o = variables[i];
            String name = (String)variables[i + 1];
            if (SimpleBase.class.isAssignableFrom(o.getClass())) {
                eq.alias(((SimpleBase)o).getDDRM(), name);
                continue;
            }
            if (o instanceof DMatrixRMaj) {
                eq.alias((DMatrixRMaj)o, name);
                continue;
            }
            if (o instanceof Double) {
                eq.alias((Double)o, name);
                continue;
            }
            if (o instanceof Integer) {
                eq.alias((Integer)o, name);
                continue;
            }
            String type = o == null ? "null" : o.getClass().getSimpleName();
            throw new IllegalArgumentException("Variable type not supported by Equation! " + type);
        }
        if (!equation.contains("=")) {
            equation = nameThis + " = " + equation;
        }
        eq.process(equation);
    }

    public void saveToFileBinary(String fileName) throws IOException {
        MatrixIO.saveBin((DMatrixRMaj)this.mat, fileName);
    }

    public static SimpleMatrix loadBinary(String fileName) throws IOException {
        Object mat = MatrixIO.loadBin(fileName);
        if (mat instanceof DMatrixRMaj) {
            return SimpleMatrix.wrap((DMatrixRMaj)mat);
        }
        return SimpleMatrix.wrap(new DMatrixRMaj((DMatrix)mat));
    }

    public void saveToFileCSV(String fileName) throws IOException {
        MatrixIO.saveDenseCSV((DMatrixRMaj)this.mat, fileName);
    }

    public T loadCSV(String fileName) throws IOException {
        Object mat = MatrixIO.loadCSV(fileName, true);
        T ret = this.createMatrix(1, 1, mat.getType());
        ((SimpleBase)ret).setMatrix((Matrix)mat);
        return ret;
    }

    public boolean isInBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < this.mat.getNumRows() && col < this.mat.getNumCols();
    }

    public void printDimensions() {
        System.out.println("[rows = " + this.numRows() + " , cols = " + this.numCols() + " ]");
    }

    public int bits() {
        return this.mat.getType().getBits();
    }

    public T concatColumns(SimpleBase ... matrices) {
        this.convertType.specify0(this, matrices);
        Object A = this.convertType.convert(this);
        int numCols = ((SimpleBase)A).numCols();
        int numRows = ((SimpleBase)A).numRows();
        for (int i = 0; i < matrices.length; ++i) {
            numRows = Math.max(numRows, matrices[i].numRows());
            numCols += matrices[i].numCols();
        }
        SimpleMatrix combined = SimpleMatrix.wrap(this.convertType.commonType.create(numRows, numCols));
        ((SimpleBase)A).ops.extract(((SimpleBase)A).mat, 0, ((SimpleBase)A).numRows(), 0, ((SimpleBase)A).numCols(), combined.mat, 0, 0);
        int col = ((SimpleBase)A).numCols();
        for (int i = 0; i < matrices.length; ++i) {
            Matrix m = ((SimpleBase)this.convertType.convert((SimpleBase)matrices[i])).mat;
            int cols = m.getNumCols();
            int rows = m.getNumRows();
            ((SimpleBase)A).ops.extract(m, 0, rows, 0, cols, combined.mat, 0, col);
            col += cols;
        }
        return (T)combined;
    }

    public T concatRows(SimpleBase ... matrices) {
        this.convertType.specify0(this, matrices);
        Object A = this.convertType.convert(this);
        int numCols = ((SimpleBase)A).numCols();
        int numRows = ((SimpleBase)A).numRows();
        for (int i = 0; i < matrices.length; ++i) {
            numRows += matrices[i].numRows();
            numCols = Math.max(numCols, matrices[i].numCols());
        }
        SimpleMatrix combined = SimpleMatrix.wrap(this.convertType.commonType.create(numRows, numCols));
        ((SimpleBase)A).ops.extract(((SimpleBase)A).mat, 0, ((SimpleBase)A).numRows(), 0, ((SimpleBase)A).numCols(), combined.mat, 0, 0);
        int row = ((SimpleBase)A).numRows();
        for (int i = 0; i < matrices.length; ++i) {
            Matrix m = ((SimpleBase)this.convertType.convert((SimpleBase)matrices[i])).mat;
            int cols = m.getNumCols();
            int rows = m.getNumRows();
            ((SimpleBase)A).ops.extract(m, 0, rows, 0, cols, combined.mat, row, 0);
            row += rows;
        }
        return (T)combined;
    }

    public T rows(int begin, int end) {
        return this.extractMatrix(begin, end, 0, Integer.MAX_VALUE);
    }

    public T cols(int begin, int end) {
        return this.extractMatrix(0, Integer.MAX_VALUE, begin, end);
    }

    public MatrixType getType() {
        return this.mat.getType();
    }

    public T createLike() {
        return this.createMatrix(this.numRows(), this.numCols(), this.getType());
    }

    protected void setMatrix(Matrix mat) {
        this.mat = mat;
        this.ops = SimpleBase.lookupOps(mat.getType());
    }

    @Nullable
    Method findAlternative(String method, Object ... arguments) {
        Method[] methods = this.ops.getClass().getMethods();
        for (int methodIdx = 0; methodIdx < methods.length; ++methodIdx) {
            Class<?>[] paramTypes;
            if (!methods[methodIdx].getName().equals(method) || (paramTypes = methods[methodIdx].getParameterTypes()).length != arguments.length) continue;
            boolean match = true;
            for (int j = 0; j < paramTypes.length; ++j) {
                if (arguments[j] instanceof Class) {
                    if (paramTypes[j] == arguments[j]) continue;
                    match = false;
                    break;
                }
                if (paramTypes[j] == arguments[j].getClass()) continue;
                match = false;
                break;
            }
            if (!match) continue;
            return methods[methodIdx];
        }
        return null;
    }

    public void invoke(Method m, Object ... inputs) {
        try {
            m.invoke(this.ops, inputs);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void convertToSparse() {
        switch (this.mat.getType()) {
            case DDRM: {
                DMatrixSparseCSC m = new DMatrixSparseCSC(this.mat.getNumRows(), this.mat.getNumCols());
                DConvertMatrixStruct.convert((DMatrixRMaj)this.mat, m, 0.0);
                this.setMatrix(m);
                break;
            }
            case FDRM: {
                FMatrixSparseCSC m = new FMatrixSparseCSC(this.mat.getNumRows(), this.mat.getNumCols());
                FConvertMatrixStruct.convert((FMatrixRMaj)this.mat, m, 0.0f);
                this.setMatrix(m);
                break;
            }
            case DSCC: 
            case FSCC: {
                break;
            }
            default: {
                throw new RuntimeException("Conversion not supported!");
            }
        }
    }

    public void convertToDense() {
        switch (this.mat.getType()) {
            case DSCC: {
                DMatrixRMaj m = new DMatrixRMaj(this.mat.getNumRows(), this.mat.getNumCols());
                DConvertMatrixStruct.convert((DMatrix)this.mat, (DMatrix)m);
                this.setMatrix(m);
                break;
            }
            case FSCC: {
                FMatrixRMaj m = new FMatrixRMaj(this.mat.getNumRows(), this.mat.getNumCols());
                FConvertMatrixStruct.convert((FMatrix)this.mat, (FMatrix)m);
                this.setMatrix(m);
                break;
            }
            case DDRM: 
            case FDRM: 
            case ZDRM: 
            case CDRM: {
                break;
            }
            default: {
                throw new RuntimeException("Not a sparse matrix!");
            }
        }
    }
}

