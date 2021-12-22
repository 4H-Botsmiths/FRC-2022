/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

public class AddressableLEDBuffer {
    byte[] m_buffer;

    public AddressableLEDBuffer(int length) {
        this.m_buffer = new byte[length * 4];
    }

    public void setRGB(int index, int r, int g, int b) {
        this.m_buffer[index * 4] = (byte)b;
        this.m_buffer[index * 4 + 1] = (byte)g;
        this.m_buffer[index * 4 + 2] = (byte)r;
        this.m_buffer[index * 4 + 3] = 0;
    }

    public void setHSV(int index, int h, int s, int v) {
        if (s == 0) {
            this.setRGB(index, v, v, v);
            return;
        }
        int region = h / 30;
        int remainder = (h - region * 30) * 6;
        int p = v * (255 - s) >> 8;
        int q = v * (255 - (s * remainder >> 8)) >> 8;
        int t = v * (255 - (s * (255 - remainder) >> 8)) >> 8;
        switch (region) {
            case 0: {
                this.setRGB(index, v, t, p);
                break;
            }
            case 1: {
                this.setRGB(index, q, v, p);
                break;
            }
            case 2: {
                this.setRGB(index, p, v, t);
                break;
            }
            case 3: {
                this.setRGB(index, p, q, v);
                break;
            }
            case 4: {
                this.setRGB(index, t, p, v);
                break;
            }
            default: {
                this.setRGB(index, v, p, q);
            }
        }
    }

    public void setLED(int index, Color color) {
        this.setRGB(index, (int)(color.red * 255.0), (int)(color.green * 255.0), (int)(color.blue * 255.0));
    }

    public void setLED(int index, Color8Bit color) {
        this.setRGB(index, color.red, color.green, color.blue);
    }

    public int getLength() {
        return this.m_buffer.length / 4;
    }

    public Color8Bit getLED8Bit(int index) {
        return new Color8Bit(this.m_buffer[index * 4 + 2] & 0xFF, this.m_buffer[index * 4 + 1] & 0xFF, this.m_buffer[index * 4] & 0xFF);
    }

    public Color getLED(int index) {
        return new Color((double)(this.m_buffer[index * 4 + 2] & 0xFF) / 255.0, (double)(this.m_buffer[index * 4 + 1] & 0xFF) / 255.0, (double)(this.m_buffer[index * 4] & 0xFF) / 255.0);
    }
}

