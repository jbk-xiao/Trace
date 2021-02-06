package com.trace.fabric.fabtrace;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author jbk-xiao
 * @program fabtrace
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-04-20:07
 */
public final class TraceInfo {
    @Expose
    @SerializedName("id")
    private final String id;

    @Expose
    @SerializedName("process")
    private final ArrayList<ProcessInfo> process = new ArrayList<>();

    public TraceInfo(final String id) {
        this.id = id;
    }

    public TraceInfo(final String id, final String name, final String master,
                     final boolean enter, final long time) {
        this(id, new ProcessInfo(name, master, enter, time));
    }

    public TraceInfo(final String id, final ProcessInfo info) {
        this.id = id;
        this.process.add(info);
    }

    public String getId() {
        return id;
    }

    public ArrayList<ProcessInfo> getProcess() {
        return process;
    }

    public void addProcessInfo(final String name, final String master,
                               final boolean enter, final long time) {
        this.process.add(new ProcessInfo(name, master, enter, time));
    }

    @Override
    public String toString() {
        return "TraceInfo{"
               + "id= '" + id + '\''
               + ", process= " + process
               + '}';
    }

    static final class ProcessInfo {
        @Expose
        @SerializedName("name")
        private final String name;

        @Expose
        @SerializedName("master")
        private final String master;

        @Expose
        @SerializedName("enter")
        private final Boolean enter;

        @Expose
        @SerializedName("time")
        private final Long time;

        ProcessInfo(final String name, final String master, final Boolean enter, final Long time) {
            this.name = name;
            this.master = master;
            this.enter = enter;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public String getMaster() {
            return master;
        }

        public boolean getEnter() {
            return enter;
        }

        public long getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "ProcessInfo{"
                   + "name= '" + name + '\''
                   + ", master= '" + master + '\''
                   + ", enter= " + enter
                   + ", time= " + time
                   + '}';
        }
    }
}
