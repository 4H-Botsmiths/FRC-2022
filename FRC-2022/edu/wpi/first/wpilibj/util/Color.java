/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.wpilibj.util;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.util.Color8Bit;
import java.util.Objects;

public class Color {
    private static final double kPrecision = Math.pow(2.0, -12.0);
    public final double red;
    public final double green;
    public final double blue;
    public static final Color kDenim = new Color(0.0823529412, 0.376470589, 0.7411764706);
    public static final Color kFirstBlue = new Color(0.0, 0.4, 0.7019607844);
    public static final Color kFirstRed = new Color(0.9294117648, 0.1098039216, 0.1411764706);
    public static final Color kAliceBlue = new Color(0.9411764740943909, 0.972549f, 1.0);
    public static final Color kAntiqueWhite = new Color(0.9803921580314636, 0.9215686321258545, 0.843137264251709);
    public static final Color kAqua = new Color(0.0, 1.0, 1.0);
    public static final Color kAquamarine = new Color(0.49803921580314636, 1.0, 0.8313725590705872);
    public static final Color kAzure = new Color(0.9411764740943909, 1.0, 1.0);
    public static final Color kBeige = new Color(0.9607843160629272, 0.9607843160629272, 0.8627451062202454);
    public static final Color kBisque = new Color(1.0, 0.8941176533699036, 0.7686274647712708);
    public static final Color kBlack = new Color(0.0, 0.0, 0.0);
    public static final Color kBlanchedAlmond = new Color(1.0, 0.9215686321258545, 0.8039215803146362);
    public static final Color kBlue = new Color(0.0, 0.0, 1.0);
    public static final Color kBlueViolet = new Color(0.5411764979362488, 0.16862745583057404, 0.886274516582489);
    public static final Color kBrown = new Color(0.6470588445663452, 0.16470588743686676, 0.16470588743686676);
    public static final Color kBurlywood = new Color(0.8705882430076599, 0.7215686440467834, 0.529411792755127);
    public static final Color kCadetBlue = new Color(0.37254902720451355, 0.6196078658103943, 0.627451f);
    public static final Color kChartreuse = new Color(0.49803921580314636, 1.0, 0.0);
    public static final Color kChocolate = new Color(0.8235294222831726, 0.4117647111415863, 0.11764705926179886);
    public static final Color kCoral = new Color(1.0, 0.49803921580314636, 0.3137255012989044);
    public static final Color kCornflowerBlue = new Color(0.3921568691730499, 0.5843137502670288, 0.929411768913269);
    public static final Color kCornsilk = new Color(1.0, 0.972549f, 0.8627451062202454);
    public static final Color kCrimson = new Color(0.8627451062202454, 0.0784313753247261, 0.23529411852359772);
    public static final Color kCyan = new Color(0.0, 1.0, 1.0);
    public static final Color kDarkBlue = new Color(0.0, 0.0, 0.545098066329956);
    public static final Color kDarkCyan = new Color(0.0, 0.545098066329956, 0.545098066329956);
    public static final Color kDarkGoldenrod = new Color(0.7215686440467834, 0.5254902243614197, 0.04313725605607033);
    public static final Color kDarkGray = new Color(0.6627451181411743, 0.6627451181411743, 0.6627451181411743);
    public static final Color kDarkGreen = new Color(0.0, 0.3921568691730499, 0.0);
    public static final Color kDarkKhaki = new Color(0.7411764860153198, 0.7176470756530762, 0.41960784792900085);
    public static final Color kDarkMagenta = new Color(0.545098066329956, 0.0, 0.545098066329956);
    public static final Color kDarkOliveGreen = new Color(0.3333333432674408, 0.41960784792900085, 0.18431372940540314);
    public static final Color kDarkOrange = new Color(1.0, 0.5490196347236633, 0.0);
    public static final Color kDarkOrchid = new Color(0.6f, 0.19607843458652496, 0.8f);
    public static final Color kDarkRed = new Color(0.545098066329956, 0.0, 0.0);
    public static final Color kDarkSalmon = new Color(0.9137254953384399, 0.5882353186607361, 0.47843137383461);
    public static final Color kDarkSeaGreen = new Color(0.5607843399047852, 0.7372549176216125, 0.5607843399047852);
    public static final Color kDarkSlateBlue = new Color(0.2823529541492462, 0.239215686917305, 0.545098066329956);
    public static final Color kDarkSlateGray = new Color(0.18431372940540314, 0.30980393290519714, 0.30980393290519714);
    public static final Color kDarkTurquoise = new Color(0.0, 0.8078431487083435, 0.8196078538894653);
    public static final Color kDarkViolet = new Color(0.5803921818733215, 0.0, 0.827451f);
    public static final Color kDeepPink = new Color(1.0, 0.0784313753247261, 0.5764706134796143);
    public static final Color kDeepSkyBlue = new Color(0.0, 0.7490196228027344, 1.0);
    public static final Color kDimGray = new Color(0.4117647111415863, 0.4117647111415863, 0.4117647111415863);
    public static final Color kDodgerBlue = new Color(0.11764705926179886, 0.5647059082984924, 1.0);
    public static final Color kFirebrick = new Color(0.6980392336845398, 0.13333334028720856, 0.13333334028720856);
    public static final Color kFloralWhite = new Color(1.0, 0.9803921580314636, 0.9411764740943909);
    public static final Color kForestGreen = new Color(0.13333334028720856, 0.545098066329956, 0.13333334028720856);
    public static final Color kFuchsia = new Color(1.0, 0.0, 1.0);
    public static final Color kGainsboro = new Color(0.8627451062202454, 0.8627451062202454, 0.8627451062202454);
    public static final Color kGhostWhite = new Color(0.972549f, 0.972549f, 1.0);
    public static final Color kGold = new Color(1.0, 0.843137264251709, 0.0);
    public static final Color kGoldenrod = new Color(0.8549019694328308, 0.6470588445663452, 0.125490203499794);
    public static final Color kGray = new Color(0.501960813999176, 0.501960813999176, 0.501960813999176);
    public static final Color kGreen = new Color(0.0, 0.501960813999176, 0.0);
    public static final Color kGreenYellow = new Color(0.6784313917160034, 1.0, 0.18431372940540314);
    public static final Color kHoneydew = new Color(0.9411764740943909, 1.0, 0.9411764740943909);
    public static final Color kHotPink = new Color(1.0, 0.4117647111415863, 0.7058823704719543);
    public static final Color kIndianRed = new Color(0.8039215803146362, 0.3607843220233917, 0.3607843220233917);
    public static final Color kIndigo = new Color(0.29411765933036804, 0.0, 0.5098039507865906);
    public static final Color kIvory = new Color(1.0, 1.0, 0.9411764740943909);
    public static final Color kKhaki = new Color(0.9411764740943909, 0.9019607901573181, 0.5490196347236633);
    public static final Color kLavender = new Color(0.9019607901573181, 0.9019607901573181, 0.9803921580314636);
    public static final Color kLavenderBlush = new Color(1.0, 0.9411764740943909, 0.9607843160629272);
    public static final Color kLawnGreen = new Color(0.4862745f, 0.9882352948188782, 0.0);
    public static final Color kLemonChiffon = new Color(1.0, 0.9803921580314636, 0.8039215803146362);
    public static final Color kLightBlue = new Color(0.6784313917160034, 0.8470588326454163, 0.9019607901573181);
    public static final Color kLightCoral = new Color(0.9411764740943909, 0.501960813999176, 0.501960813999176);
    public static final Color kLightCyan = new Color(0.8784313797950745, 1.0, 1.0);
    public static final Color kLightGoldenrodYellow = new Color(0.9803921580314636, 0.9803921580314636, 0.8235294222831726);
    public static final Color kLightGray = new Color(0.827451f, 0.827451f, 0.827451f);
    public static final Color kLightGreen = new Color(0.5647059082984924, 0.9333333373069763, 0.5647059082984924);
    public static final Color kLightPink = new Color(1.0, 0.7137255072593689, 0.7568627595901489);
    public static final Color kLightSalmon = new Color(1.0, 0.627451f, 0.47843137383461);
    public static final Color kLightSeaGreen = new Color(0.125490203499794, 0.6980392336845398, 0.6666666865348816);
    public static final Color kLightSkyBlue = new Color(0.529411792755127, 0.8078431487083435, 0.9803921580314636);
    public static final Color kLightSlateGray = new Color(0.46666666865348816, 0.5333333611488342, 0.6f);
    public static final Color kLightSteelBlue = new Color(0.6901960968971252, 0.7686274647712708, 0.8705882430076599);
    public static final Color kLightYellow = new Color(1.0, 1.0, 0.8784313797950745);
    public static final Color kLime = new Color(0.0, 1.0, 0.0);
    public static final Color kLimeGreen = new Color(0.19607843458652496, 0.8039215803146362, 0.19607843458652496);
    public static final Color kLinen = new Color(0.9803921580314636, 0.9411764740943909, 0.9019607901573181);
    public static final Color kMagenta = new Color(1.0, 0.0, 1.0);
    public static final Color kMaroon = new Color(0.501960813999176, 0.0, 0.0);
    public static final Color kMediumAquamarine = new Color(0.4f, 0.8039215803146362, 0.6666666865348816);
    public static final Color kMediumBlue = new Color(0.0, 0.0, 0.8039215803146362);
    public static final Color kMediumOrchid = new Color(0.729411780834198, 0.3333333432674408, 0.827451f);
    public static final Color kMediumPurple = new Color(0.5764706134796143, 0.4392157f, 0.8588235378265381);
    public static final Color kMediumSeaGreen = new Color(0.23529411852359772, 0.7019608020782471, 0.4431372582912445);
    public static final Color kMediumSlateBlue = new Color(0.48235294222831726, 0.40784314274787903, 0.9333333373069763);
    public static final Color kMediumSpringGreen = new Color(0.0, 0.9803921580314636, 0.6039215922355652);
    public static final Color kMediumTurquoise = new Color(0.2823529541492462, 0.8196078538894653, 0.8f);
    public static final Color kMediumVioletRed = new Color(0.7803921699523926, 0.08235294371843338, 0.5215686559677124);
    public static final Color kMidnightBlue = new Color(0.09803921729326248, 0.09803921729326248, 0.4392157f);
    public static final Color kMintcream = new Color(0.9607843160629272, 1.0, 0.9803921580314636);
    public static final Color kMistyRose = new Color(1.0, 0.8941176533699036, 0.8823529481887817);
    public static final Color kMoccasin = new Color(1.0, 0.8941176533699036, 0.7098039388656616);
    public static final Color kNavajoWhite = new Color(1.0, 0.8705882430076599, 0.6784313917160034);
    public static final Color kNavy = new Color(0.0, 0.0, 0.501960813999176);
    public static final Color kOldLace = new Color(0.9921568632125854, 0.9607843160629272, 0.9019607901573181);
    public static final Color kOlive = new Color(0.501960813999176, 0.501960813999176, 0.0);
    public static final Color kOliveDrab = new Color(0.41960784792900085, 0.5568627715110779, 0.13725490868091583);
    public static final Color kOrange = new Color(1.0, 0.6470588445663452, 0.0);
    public static final Color kOrangeRed = new Color(1.0, 0.2705882489681244, 0.0);
    public static final Color kOrchid = new Color(0.8549019694328308, 0.4392157f, 0.8392156958580017);
    public static final Color kPaleGoldenrod = new Color(0.9333333373069763, 0.9098039269447327, 0.6666666865348816);
    public static final Color kPaleGreen = new Color(0.5960784554481506, 0.9843137264251709, 0.5960784554481506);
    public static final Color kPaleTurquoise = new Color(0.686274528503418, 0.9333333373069763, 0.9333333373069763);
    public static final Color kPaleVioletRed = new Color(0.8588235378265381, 0.4392157f, 0.5764706134796143);
    public static final Color kPapayaWhip = new Color(1.0, 0.9372549057006836, 0.8352941274642944);
    public static final Color kPeachPuff = new Color(1.0, 0.8549019694328308, 0.7254902124404907);
    public static final Color kPeru = new Color(0.8039215803146362, 0.5215686559677124, 0.24705882370471954);
    public static final Color kPink = new Color(1.0, 0.7529411911964417, 0.7960784435272217);
    public static final Color kPlum = new Color(0.8666666746139526, 0.627451f, 0.8666666746139526);
    public static final Color kPowderBlue = new Color(0.6901960968971252, 0.8784313797950745, 0.9019607901573181);
    public static final Color kPurple = new Color(0.501960813999176, 0.0, 0.501960813999176);
    public static final Color kRed = new Color(1.0, 0.0, 0.0);
    public static final Color kRosyBrown = new Color(0.7372549176216125, 0.5607843399047852, 0.5607843399047852);
    public static final Color kRoyalBlue = new Color(0.2549019753932953, 0.4117647111415863, 0.8823529481887817);
    public static final Color kSaddleBrown = new Color(0.545098066329956, 0.2705882489681244, 0.07450980693101883);
    public static final Color kSalmon = new Color(0.9803921580314636, 0.501960813999176, 0.4470588266849518);
    public static final Color kSandyBrown = new Color(0.95686274766922, 0.6431372761726379, 0.3764705955982208);
    public static final Color kSeaGreen = new Color(0.18039216101169586, 0.545098066329956, 0.34117648005485535);
    public static final Color kSeashell = new Color(1.0, 0.9607843160629272, 0.9333333373069763);
    public static final Color kSienna = new Color(0.627451f, 0.32156863808631897, 0.1764705926179886);
    public static final Color kSilver = new Color(0.7529411911964417, 0.7529411911964417, 0.7529411911964417);
    public static final Color kSkyBlue = new Color(0.529411792755127, 0.8078431487083435, 0.9215686321258545);
    public static final Color kSlateBlue = new Color(0.4156862795352936, 0.3529411852359772, 0.8039215803146362);
    public static final Color kSlateGray = new Color(0.4392157f, 0.501960813999176, 0.5647059082984924);
    public static final Color kSnow = new Color(1.0, 0.9803921580314636, 0.9803921580314636);
    public static final Color kSpringGreen = new Color(0.0, 1.0, 0.49803921580314636);
    public static final Color kSteelBlue = new Color(0.27450981736183167, 0.5098039507865906, 0.7058823704719543);
    public static final Color kTan = new Color(0.8235294222831726, 0.7058823704719543, 0.5490196347236633);
    public static final Color kTeal = new Color(0.0, 0.501960813999176, 0.501960813999176);
    public static final Color kThistle = new Color(0.8470588326454163, 0.7490196228027344, 0.8470588326454163);
    public static final Color kTomato = new Color(1.0, 0.3882353f, 0.2784314f);
    public static final Color kTurquoise = new Color(0.250980406999588, 0.8784313797950745, 0.8156862854957581);
    public static final Color kViolet = new Color(0.9333333373069763, 0.5098039507865906, 0.9333333373069763);
    public static final Color kWheat = new Color(0.9607843160629272, 0.8705882430076599, 0.7019608020782471);
    public static final Color kWhite = new Color(1.0, 1.0, 1.0);
    public static final Color kWhiteSmoke = new Color(0.9607843160629272, 0.9607843160629272, 0.9607843160629272);
    public static final Color kYellow = new Color(1.0, 1.0, 0.0);
    public static final Color kYellowGreen = new Color(0.6039215922355652, 0.8039215803146362, 0.19607843458652496);

    public Color(double red, double green, double blue) {
        this.red = Color.roundAndClamp(red);
        this.green = Color.roundAndClamp(green);
        this.blue = Color.roundAndClamp(blue);
    }

    public Color(Color8Bit color) {
        this((double)color.red / 255.0, (double)color.green / 255.0, (double)color.blue / 255.0);
    }

    public static Color fromHSV(int h, int s, int v) {
        if (s == 0) {
            return new Color((double)v / 255.0, (double)v / 255.0, (double)v / 255.0);
        }
        int region = h / 30;
        int remainder = (h - region * 30) * 6;
        int p = v * (255 - s) >> 8;
        int q = v * (255 - (s * remainder >> 8)) >> 8;
        int t = v * (255 - (s * (255 - remainder) >> 8)) >> 8;
        switch (region) {
            case 0: {
                return new Color((double)v / 255.0, (double)t / 255.0, (double)p / 255.0);
            }
            case 1: {
                return new Color((double)q / 255.0, (double)v / 255.0, (double)p / 255.0);
            }
            case 2: {
                return new Color((double)p / 255.0, (double)v / 255.0, (double)t / 255.0);
            }
            case 3: {
                return new Color((double)p / 255.0, (double)q / 255.0, (double)v / 255.0);
            }
            case 4: {
                return new Color((double)t / 255.0, (double)p / 255.0, (double)v / 255.0);
            }
        }
        return new Color((double)v / 255.0, (double)p / 255.0, (double)q / 255.0);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Color color = (Color)other;
        return Double.compare(color.red, this.red) == 0 && Double.compare(color.green, this.green) == 0 && Double.compare(color.blue, this.blue) == 0;
    }

    public int hashCode() {
        return Objects.hash(this.red, this.green, this.blue);
    }

    private static double roundAndClamp(double value) {
        double rounded = (double)Math.round((value + kPrecision / 2.0) / kPrecision) * kPrecision;
        return MathUtil.clamp(rounded, 0.0, 1.0);
    }
}

