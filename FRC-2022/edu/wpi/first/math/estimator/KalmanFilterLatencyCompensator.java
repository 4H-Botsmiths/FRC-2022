/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.math.estimator;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.estimator.KalmanTypeFilter;
import edu.wpi.first.math.numbers.N1;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class KalmanFilterLatencyCompensator<S extends Num, I extends Num, O extends Num> {
    private static final int kMaxPastObserverStates = 300;
    private final List<Map.Entry<Double, ObserverSnapshot>> m_pastObserverSnapshots = new ArrayList<Map.Entry<Double, ObserverSnapshot>>();

    KalmanFilterLatencyCompensator() {
    }

    public void reset() {
        this.m_pastObserverSnapshots.clear();
    }

    public void addObserverState(KalmanTypeFilter<S, I, O> observer, Matrix<I, N1> u, Matrix<O, N1> localY, double timestampSeconds) {
        this.m_pastObserverSnapshots.add(Map.entry((Object)timestampSeconds, (Object)new ObserverSnapshot(observer, u, localY)));
        if (this.m_pastObserverSnapshots.size() > 300) {
            this.m_pastObserverSnapshots.remove(0);
        }
    }

    public <R extends Num> void applyPastGlobalMeasurement(Nat<R> rows, KalmanTypeFilter<S, I, O> observer, double nominalDtSeconds, Matrix<R, N1> y, BiConsumer<Matrix<I, N1>, Matrix<R, N1>> globalMeasurementCorrect, double timestampSeconds) {
        if (this.m_pastObserverSnapshots.isEmpty()) {
            return;
        }
        int maxIdx = this.m_pastObserverSnapshots.size() - 1;
        int low = 1;
        int high = Math.max(maxIdx, 1);
        while (low != high) {
            int mid = (low + high) / 2;
            if (this.m_pastObserverSnapshots.get(mid).getKey() < timestampSeconds) {
                low = mid + 1;
                continue;
            }
            high = mid;
        }
        double timestamp = timestampSeconds;
        int index = low;
        int indexOfClosestEntry = Math.abs(timestamp - this.m_pastObserverSnapshots.get(index - 1).getKey()) <= Math.abs(timestamp - this.m_pastObserverSnapshots.get(Math.min(index, maxIdx)).getKey()) ? index - 1 : index;
        double lastTimestamp = this.m_pastObserverSnapshots.get(indexOfClosestEntry).getKey() - nominalDtSeconds;
        for (int i = indexOfClosestEntry; i < this.m_pastObserverSnapshots.size(); ++i) {
            Double key = this.m_pastObserverSnapshots.get(i).getKey();
            ObserverSnapshot snapshot = this.m_pastObserverSnapshots.get(i).getValue();
            if (i == indexOfClosestEntry) {
                observer.setP(snapshot.errorCovariances);
                observer.setXhat(snapshot.xHat);
            }
            observer.predict(snapshot.inputs, key - lastTimestamp);
            observer.correct(snapshot.inputs, snapshot.localMeasurements);
            if (i == indexOfClosestEntry) {
                globalMeasurementCorrect.accept(snapshot.inputs, y);
            }
            lastTimestamp = key;
            this.m_pastObserverSnapshots.set(i, Map.entry((Object)key, (Object)new ObserverSnapshot(observer, snapshot.inputs, snapshot.localMeasurements)));
        }
    }

    public class ObserverSnapshot {
        public final Matrix<S, N1> xHat;
        public final Matrix<S, S> errorCovariances;
        public final Matrix<I, N1> inputs;
        public final Matrix<O, N1> localMeasurements;

        private ObserverSnapshot(KalmanTypeFilter<S, I, O> observer, Matrix<I, N1> u, Matrix<O, N1> localY) {
            this.xHat = observer.getXhat();
            this.errorCovariances = observer.getP();
            this.inputs = u;
            this.localMeasurements = localY;
        }
    }
}

