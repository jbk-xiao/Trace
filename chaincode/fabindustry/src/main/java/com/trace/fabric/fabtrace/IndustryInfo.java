package com.trace.fabric.fabtrace;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbk-xiao
 * @program fabindustry
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-05-13:56
 */
public final class IndustryInfo {
    @Expose
    @SerializedName("id")
    private final String id;

    @Expose
    @SerializedName("name")
    private final String name;

    @Expose
    @SerializedName("master")
    private final String master;

    @Expose
    @SerializedName("procedure")
    private final List<ProcedureInfo> procedure = new ArrayList<>();



    public IndustryInfo(final String id, final String name, final String master) {
        this.id = id;
        this.name = name;
        this.master = master;
    }

    public IndustryInfo(final String id, final String name, final String master, final String inname,
                        final String inmaster, final Boolean start, final Long time) {
        this.id = id;
        this.name = name;
        this.master = master;
        this.procedure.add(new ProcedureInfo(inname, inmaster, start, time));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaster() {
        return master;
    }

    public List<ProcedureInfo> getProcedure() {
        return procedure;
    }

    public void addProcedureInfo(final String inName, final String inMaster, final Boolean start, final Long time) {
        this.procedure.add(new ProcedureInfo(inName, inMaster, start, time));
    }

    static final class ProcedureInfo {
        @Expose
        @SerializedName("name")
        private final String name;

        @Expose
        @SerializedName("master")
        private final String master;

        @Expose
        @SerializedName("start")
        private final Boolean start;

        @Expose
        @SerializedName("time")
        private final Long time;

        ProcedureInfo(final String name, final String master, final Boolean start, final Long time) {
            this.name = name;
            this.master = master;
            this.start = start;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public String getMaster() {
            return master;
        }

        public Boolean getStart() {
            return start;
        }

        public Long getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "ProcedureInfo{"
                   + "name= '" + name + '\''
                   + ", master= '" + master + '\''
                   + ", start= " + start
                   + ", time= " + time
                   + '}';
        }
    }

    @Override
    public String toString() {
        return "IndustryInfo{"
               + "id= '" + id + '\''
               + ", name= '" + name + '\''
               + ", master= '" + master + '\''
               + ", procedure= " + procedure
               + '}';
    }
}
