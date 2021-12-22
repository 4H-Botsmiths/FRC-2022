/*
 * Decompiled with CFR 0.150.
 */
package edu.wpi.first.util.sendable;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SendableRegistry {
    private static Supplier<SendableBuilder> liveWindowFactory;
    private static final Map<Object, Component> components;
    private static int nextDataHandle;
    private static List<Component> foreachComponents;

    private static Component getOrAdd(Sendable sendable) {
        Component comp = components.get(sendable);
        if (comp == null) {
            comp = new Component(sendable);
            components.put(sendable, comp);
        } else if (comp.m_sendable == null) {
            comp.m_sendable = new WeakReference<Sendable>(sendable);
        }
        return comp;
    }

    private SendableRegistry() {
        throw new UnsupportedOperationException("This is a utility class!");
    }

    public static synchronized void setLiveWindowBuilderFactory(Supplier<SendableBuilder> factory) {
        liveWindowFactory = factory;
    }

    public static synchronized void add(Sendable sendable, String name) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        comp.m_name = name;
    }

    public static synchronized void add(Sendable sendable, String moduleType, int channel) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        comp.setName(moduleType, channel);
    }

    public static synchronized void add(Sendable sendable, String moduleType, int moduleNumber, int channel) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        comp.setName(moduleType, moduleNumber, channel);
    }

    public static synchronized void add(Sendable sendable, String subsystem, String name) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        comp.m_name = name;
        comp.m_subsystem = subsystem;
    }

    public static synchronized void addLW(Sendable sendable, String name) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        if (liveWindowFactory != null) {
            comp.m_builder = liveWindowFactory.get();
        }
        comp.m_liveWindow = true;
        comp.m_name = name;
    }

    public static synchronized void addLW(Sendable sendable, String moduleType, int channel) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        if (liveWindowFactory != null) {
            comp.m_builder = liveWindowFactory.get();
        }
        comp.m_liveWindow = true;
        comp.setName(moduleType, channel);
    }

    public static synchronized void addLW(Sendable sendable, String moduleType, int moduleNumber, int channel) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        if (liveWindowFactory != null) {
            comp.m_builder = liveWindowFactory.get();
        }
        comp.m_liveWindow = true;
        comp.setName(moduleType, moduleNumber, channel);
    }

    public static synchronized void addLW(Sendable sendable, String subsystem, String name) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        if (liveWindowFactory != null) {
            comp.m_builder = liveWindowFactory.get();
        }
        comp.m_liveWindow = true;
        comp.m_name = name;
        comp.m_subsystem = subsystem;
    }

    public static synchronized void addChild(Sendable parent, Object child) {
        Component comp = components.get(child);
        if (comp == null) {
            comp = new Component();
            components.put(child, comp);
        }
        comp.m_parent = new WeakReference<Sendable>(parent);
    }

    public static synchronized boolean remove(Sendable sendable) {
        return components.remove(sendable) != null;
    }

    public static synchronized boolean contains(Sendable sendable) {
        return components.containsKey(sendable);
    }

    public static synchronized String getName(Sendable sendable) {
        Component comp = components.get(sendable);
        if (comp == null) {
            return "";
        }
        return comp.m_name;
    }

    public static synchronized void setName(Sendable sendable, String name) {
        Component comp = components.get(sendable);
        if (comp != null) {
            comp.m_name = name;
        }
    }

    public static synchronized void setName(Sendable sendable, String moduleType, int channel) {
        Component comp = components.get(sendable);
        if (comp != null) {
            comp.setName(moduleType, channel);
        }
    }

    public static synchronized void setName(Sendable sendable, String moduleType, int moduleNumber, int channel) {
        Component comp = components.get(sendable);
        if (comp != null) {
            comp.setName(moduleType, moduleNumber, channel);
        }
    }

    public static synchronized void setName(Sendable sendable, String subsystem, String name) {
        Component comp = components.get(sendable);
        if (comp != null) {
            comp.m_name = name;
            comp.m_subsystem = subsystem;
        }
    }

    public static synchronized String getSubsystem(Sendable sendable) {
        Component comp = components.get(sendable);
        if (comp == null) {
            return "";
        }
        return comp.m_subsystem;
    }

    public static synchronized void setSubsystem(Sendable sendable, String subsystem) {
        Component comp = components.get(sendable);
        if (comp != null) {
            comp.m_subsystem = subsystem;
        }
    }

    public static synchronized int getDataHandle() {
        return nextDataHandle++;
    }

    public static synchronized Object setData(Sendable sendable, int handle, Object data) {
        Component comp = components.get(sendable);
        if (comp == null) {
            return null;
        }
        Object rv = null;
        if (comp.m_data == null) {
            comp.m_data = new Object[handle + 1];
        } else if (handle < comp.m_data.length) {
            rv = comp.m_data[handle];
        } else {
            comp.m_data = Arrays.copyOf(comp.m_data, handle + 1);
        }
        comp.m_data[handle] = data;
        return rv;
    }

    public static synchronized Object getData(Sendable sendable, int handle) {
        Component comp = components.get(sendable);
        if (comp == null || comp.m_data == null || handle >= comp.m_data.length) {
            return null;
        }
        return comp.m_data[handle];
    }

    public static synchronized void enableLiveWindow(Sendable sendable) {
        Component comp = components.get(sendable);
        if (comp != null) {
            comp.m_liveWindow = true;
        }
    }

    public static synchronized void disableLiveWindow(Sendable sendable) {
        Component comp = components.get(sendable);
        if (comp != null) {
            comp.m_liveWindow = false;
        }
    }

    public static synchronized void publish(Sendable sendable, SendableBuilder builder) {
        Component comp = SendableRegistry.getOrAdd(sendable);
        if (comp.m_builder != null) {
            comp.m_builder.clearProperties();
        }
        comp.m_builder = builder;
        sendable.initSendable(comp.m_builder);
        comp.m_builder.update();
    }

    public static synchronized void update(Sendable sendable) {
        Component comp = components.get(sendable);
        if (comp != null && comp.m_builder != null) {
            comp.m_builder.update();
        }
    }

    public static synchronized void foreachLiveWindow(int dataHandle, Consumer<CallbackData> callback) {
        CallbackData cbdata = new CallbackData();
        foreachComponents.clear();
        foreachComponents.addAll(components.values());
        for (Component comp : foreachComponents) {
            if (comp.m_builder == null || comp.m_sendable == null) continue;
            cbdata.sendable = (Sendable)comp.m_sendable.get();
            if (cbdata.sendable == null || !comp.m_liveWindow) continue;
            cbdata.name = comp.m_name;
            cbdata.subsystem = comp.m_subsystem;
            cbdata.parent = comp.m_parent != null ? (Sendable)comp.m_parent.get() : null;
            cbdata.data = comp.m_data != null && dataHandle < comp.m_data.length ? comp.m_data[dataHandle] : null;
            cbdata.builder = comp.m_builder;
            try {
                callback.accept(cbdata);
            }
            catch (Throwable throwable) {
                Throwable cause = throwable.getCause();
                if (cause != null) {
                    throwable = cause;
                }
                System.err.println("Unhandled exception calling LiveWindow for " + comp.m_name + ": ");
                throwable.printStackTrace();
                comp.m_liveWindow = false;
            }
            if (cbdata.data == null) continue;
            if (comp.m_data == null) {
                comp.m_data = new Object[dataHandle + 1];
            } else if (dataHandle >= comp.m_data.length) {
                comp.m_data = Arrays.copyOf(comp.m_data, dataHandle + 1);
            }
            comp.m_data[dataHandle] = cbdata.data;
        }
        foreachComponents.clear();
    }

    static {
        components = new WeakHashMap<Object, Component>();
        foreachComponents = new ArrayList<Component>();
    }

    public static class CallbackData {
        public Sendable sendable;
        public String name;
        public String subsystem;
        public Sendable parent;
        public Object data;
        public SendableBuilder builder;
    }

    private static class Component {
        WeakReference<Sendable> m_sendable;
        SendableBuilder m_builder;
        String m_name;
        String m_subsystem = "Ungrouped";
        WeakReference<Sendable> m_parent;
        boolean m_liveWindow;
        Object[] m_data;

        Component() {
        }

        Component(Sendable sendable) {
            this.m_sendable = new WeakReference<Sendable>(sendable);
        }

        void setName(String moduleType, int channel) {
            this.m_name = moduleType + "[" + channel + "]";
        }

        void setName(String moduleType, int moduleNumber, int channel) {
            this.m_name = moduleType + "[" + moduleNumber + "," + channel + "]";
        }
    }
}

