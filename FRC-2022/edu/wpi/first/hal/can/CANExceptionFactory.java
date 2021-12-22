/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.hal.can;

import edu.wpi.first.hal.can.CANInvalidBufferException;
import edu.wpi.first.hal.can.CANMessageNotAllowedException;
import edu.wpi.first.hal.can.CANMessageNotFoundException;
import edu.wpi.first.hal.can.CANNotInitializedException;
import edu.wpi.first.hal.util.UncleanStatusException;

public final class CANExceptionFactory {
    static final int ERR_CANSessionMux_InvalidBuffer = -44086;
    static final int ERR_CANSessionMux_MessageNotFound = -44087;
    static final int ERR_CANSessionMux_NotAllowed = -44088;
    static final int ERR_CANSessionMux_NotInitialized = -44089;

    public static void checkStatus(int status, int messageID) {
        switch (status) {
            case 0: {
                return;
            }
            case -63080: 
            case -44086: {
                throw new CANInvalidBufferException();
            }
            case -52007: 
            case -44087: {
                throw new CANMessageNotFoundException();
            }
            case -63193: 
            case -44088: {
                throw new CANMessageNotAllowedException("MessageID = " + messageID);
            }
            case -52010: 
            case -44089: {
                throw new CANNotInitializedException();
            }
        }
        throw new UncleanStatusException("Fatal status code detected:  " + status);
    }

    private CANExceptionFactory() {
    }
}

