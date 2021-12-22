/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.simulation;

public class CallbackStore
implements AutoCloseable {
    private int m_index;
    private int m_channel;
    private final int m_uid;
    private CancelCallbackFunc m_cancelCallback;
    private CancelCallbackChannelFunc m_cancelCallbackChannel;
    private CancelCallbackNoIndexFunc m_cancelCallbackNoIndex;
    private static final int kNormalCancel = 0;
    private static final int kChannelCancel = 1;
    private static final int kNoIndexCancel = 2;
    private int m_cancelType;

    public CallbackStore(int index, int uid, CancelCallbackFunc ccf) {
        this.m_cancelType = 0;
        this.m_index = index;
        this.m_uid = uid;
        this.m_cancelCallback = ccf;
    }

    public CallbackStore(int index, int channel, int uid, CancelCallbackChannelFunc ccf) {
        this.m_cancelType = 1;
        this.m_index = index;
        this.m_uid = uid;
        this.m_channel = channel;
        this.m_cancelCallbackChannel = ccf;
    }

    public CallbackStore(int uid, CancelCallbackNoIndexFunc ccf) {
        this.m_cancelType = 2;
        this.m_uid = uid;
        this.m_cancelCallbackNoIndex = ccf;
    }

    @Override
    public void close() {
        switch (this.m_cancelType) {
            case 0: {
                this.m_cancelCallback.cancel(this.m_index, this.m_uid);
                break;
            }
            case 1: {
                this.m_cancelCallbackChannel.cancel(this.m_index, this.m_channel, this.m_uid);
                break;
            }
            case 2: {
                this.m_cancelCallbackNoIndex.cancel(this.m_uid);
                break;
            }
            default: {
                assert (false);
                break;
            }
        }
        this.m_cancelType = -1;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.m_cancelType >= 0) {
                this.close();
            }
        }
        finally {
            super.finalize();
        }
    }

    static interface CancelCallbackNoIndexFunc {
        public void cancel(int var1);
    }

    static interface CancelCallbackChannelFunc {
        public void cancel(int var1, int var2, int var3);
    }

    static interface CancelCallbackFunc {
        public void cancel(int var1, int var2);
    }
}

