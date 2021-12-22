/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.vision;

import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionRunner;

public class VisionThread
extends Thread {
    public VisionThread(VisionRunner<?> visionRunner) {
        super(visionRunner::runForever, "WPILib Vision Thread");
        this.setDaemon(true);
    }

    public <P extends VisionPipeline> VisionThread(VideoSource videoSource, P pipeline, VisionRunner.Listener<? super P> listener) {
        this(new VisionRunner<P>(videoSource, pipeline, listener));
    }
}

