/*
 * Decompiled with CFR 0.150.
 */
package com.revrobotics;

public enum CANError {
    kOk(0),
    kError(1),
    kTimeout(2),
    kNotImplmented(3),
    kHALError(4),
    kCantFindFirmware(5),
    kFirmwareTooOld(6),
    kFirmwareTooNew(7),
    kParamInvalidID(8),
    kParamMismatchType(9),
    kParamAccessMode(10),
    kParamInvalid(11),
    kParamNotImplementedDeprecated(12),
    kFollowConfigMismatch(13),
    kInvalid(14),
    kSetpointOutOfRange(15);

    public final int value;

    private CANError(int value) {
        this.value = value;
    }

    public static CANError fromInt(int id) {
        for (CANError type : CANError.values()) {
            if (type.value != id) continue;
            return type;
        }
        return kInvalid;
    }
}

