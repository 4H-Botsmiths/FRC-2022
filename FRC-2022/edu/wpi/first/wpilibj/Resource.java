/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.hal.util.AllocationException;
import edu.wpi.first.hal.util.CheckedAllocationException;

public final class Resource {
    private static Resource resourceList;
    private final boolean[] m_numAllocated;
    private final int m_size;
    private final Resource m_nextResource;

    public static void restartProgram() {
        Resource r = resourceList;
        while (r != null) {
            for (int i = 0; i < r.m_size; ++i) {
                r.m_numAllocated[i] = false;
            }
            r = r.m_nextResource;
        }
    }

    public Resource(int size) {
        this.m_size = size;
        this.m_numAllocated = new boolean[size];
        for (int i = 0; i < size; ++i) {
            this.m_numAllocated[i] = false;
        }
        this.m_nextResource = resourceList;
        resourceList = this;
    }

    public int allocate() throws CheckedAllocationException {
        for (int i = 0; i < this.m_size; ++i) {
            if (this.m_numAllocated[i]) continue;
            this.m_numAllocated[i] = true;
            return i;
        }
        throw new CheckedAllocationException("No available resources");
    }

    public int allocate(int index) throws CheckedAllocationException {
        if (index >= this.m_size || index < 0) {
            throw new CheckedAllocationException("Index " + index + " out of range");
        }
        if (this.m_numAllocated[index]) {
            throw new CheckedAllocationException("Resource at index " + index + " already allocated");
        }
        this.m_numAllocated[index] = true;
        return index;
    }

    public void free(int index) {
        if (!this.m_numAllocated[index]) {
            throw new AllocationException("No resource available to be freed");
        }
        this.m_numAllocated[index] = false;
    }
}

