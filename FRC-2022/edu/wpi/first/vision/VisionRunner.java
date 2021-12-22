/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.vision;

import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.vision.VisionPipeline;
import org.opencv.core.Mat;

public class VisionRunner<P extends VisionPipeline> {
    private final CvSink m_cvSink = new CvSink("VisionRunner CvSink");
    private final P m_pipeline;
    private final Mat m_image = new Mat();
    private final Listener<? super P> m_listener;
    private volatile boolean m_enabled = true;

    public VisionRunner(VideoSource videoSource, P pipeline, Listener<? super P> listener) {
        this.m_pipeline = pipeline;
        this.m_listener = listener;
        this.m_cvSink.setSource(videoSource);
    }

    public void runOnce() {
        Long id = CameraServerSharedStore.getCameraServerShared().getRobotMainThreadId();
        if (id != null && Thread.currentThread().getId() == id.longValue()) {
            throw new IllegalStateException("VisionRunner.runOnce() cannot be called from the main robot thread");
        }
        this.runOnceInternal();
    }

    private void runOnceInternal() {
        long frameTime = this.m_cvSink.grabFrame(this.m_image);
        if (frameTime == 0L) {
            String error = this.m_cvSink.getError();
            CameraServerSharedStore.getCameraServerShared().reportDriverStationError(error);
        } else {
            this.m_pipeline.process(this.m_image);
            this.m_listener.copyPipelineOutputs(this.m_pipeline);
        }
    }

    public void runForever() {
        Long id = CameraServerSharedStore.getCameraServerShared().getRobotMainThreadId();
        if (id != null && Thread.currentThread().getId() == id.longValue()) {
            throw new IllegalStateException("VisionRunner.runForever() cannot be called from the main robot thread");
        }
        while (this.m_enabled && !Thread.interrupted()) {
            this.runOnceInternal();
        }
    }

    public void stop() {
        this.m_enabled = false;
    }

    @FunctionalInterface
    public static interface Listener<P extends VisionPipeline> {
        public void copyPipelineOutputs(P var1);
    }
}

