/*
 * Decompiled with CFR 0.150.
 */
package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;

public final class PackageVersion
implements Versioned {
    public static final Version VERSION = VersionUtil.parseVersion("2.12.4", "com.fasterxml.jackson.core", "jackson-databind");

    @Override
    public Version version() {
        return VERSION;
    }
}

