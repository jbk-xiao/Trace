package com.trace.fabric.fabtrace;


import com.google.gson.Gson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbk-xiao
 * @program fabtrace
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-04-11:21
 */
@Contract(
        name = "FabTrace",
        info = @Info(
                title = "FabTrace contract",
                description = "The process of produce goods.",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "media@example.com",
                        name = "fab trace",
                        url = "http://process.trace.com")))
@Default
public final class FabTrace implements ContractInterface {
    private final Gson gson = new Gson();

    private enum FabTraceError {
        //
        ID_NOT_FOUND,
        PRODUCT_HAVE_LEFT,
        WRONG_PROCESS
    }

    enum InitData {
        //
        TIME(1611970163415L), DURATION(432000L);
        private final long value;

        InitData(final long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    @Transaction()
    public void initLedger(final Context context) {
        ChaincodeStub stub = context.getStub();
        long time;
        time = InitData.TIME.value;
        long duration;
        duration = InitData.DURATION.value;
        TraceInfo info;
        info = new TraceInfo("16119701634150000");
        info.addProcessInfo("菜籽油生产地", "张三", true, time);
        time += duration;
        info.addProcessInfo("菜籽油生产地", "张三", false, time);
        info.addProcessInfo("工厂", "李四", true, time);
        time += duration;
        info.addProcessInfo("工厂", "李四", false, time);
        info.addProcessInfo("分销商", "王五", true, time);
        time += duration;
        info.addProcessInfo("分销商", "王五", false, time);
        info.addProcessInfo("经销商超市", "麻六", true, time);
        time += duration;
        info.addProcessInfo("经销商超市", "麻六", false, time);
        stub.putStringState(info.getId(), gson.toJson(info));
        info = new TraceInfo("16119701634151111");
        info.addProcessInfo("菜籽油生产地", "张三", true, time);
        time += duration;
        info.addProcessInfo("菜籽油生产地", "张三", false, time);
        info.addProcessInfo("工厂", "李四", true, time);
        time += duration;
        info.addProcessInfo("工厂", "李四", false, time);
        info.addProcessInfo("分销商", "王五", true, time);
        time += duration;
        info.addProcessInfo("分销商", "王五", false, time);
        info.addProcessInfo("经销商超市", "麻六", true, time);
        time += duration;
        info.addProcessInfo("经销商超市", "麻六", false, time);
        stub.putStringState(info.getId(), gson.toJson(info));
        info = new TraceInfo("16119701634152222");
        info.addProcessInfo("菜籽油生产地", "张三", true, time);
        time += duration;
        info.addProcessInfo("菜籽油生产地", "张三", false, time);
        info.addProcessInfo("工厂", "李四", true, time);
        time += duration;
        info.addProcessInfo("工厂", "李四", false, time);
        info.addProcessInfo("分销商", "王五", true, time);
        time += duration;
        info.addProcessInfo("分销商", "王五", false, time);
        info.addProcessInfo("经销商超市", "麻六", true, time);
        time += duration;
        info.addProcessInfo("经销商超市", "麻六", false, time);
        stub.putStringState(info.getId(), gson.toJson(info));
        TraceInfo dueInfo = new TraceInfo("123456");
        dueInfo.addProcessInfo("工厂", "李四", true, InitData.TIME.value);
        dueInfo.addProcessInfo("工厂", "李四", false, InitData.TIME.value);
        stub.putStringState(dueInfo.getId(), gson.toJson(dueInfo));
    }

    @Transaction()
    public String objectIn(final Context context, final String id, final String name,
                              final String master, final long time) {
        ChaincodeStub stub = context.getStub();
        TraceInfo info;
        String traceState = stub.getStringState(id);
        if (traceState.isEmpty()) {
            info = new TraceInfo(id, name, master, true, time);
        } else {
            info = gson.fromJson(traceState, TraceInfo.class);
            info.addProcessInfo(name, master, true, time);
        }
        stub.putStringState(id, gson.toJson(info));
        return gson.toJson(info);
    }

    @Transaction()
    public String objectOut(final Context context, final String id, final String name, final String master,
                               final long time) {
        ChaincodeStub stub = context.getStub();
        TraceInfo info;
        String traceState = stub.getStringState(id);
        if (traceState.isEmpty()) {
            String errorMessage = String.format("未找到编号为“%s”的产品。", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, FabTraceError.ID_NOT_FOUND.toString());
        } else {
            info = gson.fromJson(traceState, TraceInfo.class);
            ArrayList<TraceInfo.ProcessInfo> list = info.getProcess();
            TraceInfo.ProcessInfo processInfo = list.get(list.size() - 1);
            if (processInfo.getEnter() && name.equals(processInfo.getName())) {
                info.addProcessInfo(name, master, false, time);
            } else if (!processInfo.getEnter() && name.equals(processInfo.getName())) {
                String errorMessage = String.format("批号为“%s”的产品已登记离开流程“%s”。", id, name);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, FabTraceError.PRODUCT_HAVE_LEFT.toString());
            } else {
                String errorMessage = String.format("批号为“%s”的产品不属于当前流程。", id);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, FabTraceError.WRONG_PROCESS.toString());
            }
        }
        String infoStr = gson.toJson(info);
        stub.putStringState(id, infoStr);
        return infoStr;
    }

    @Transaction()
    public String queryInfoByID(final Context context, final String id) {
        ChaincodeStub stub = context.getStub();
        TraceInfo info;
        String traceState = stub.getStringState(id);
        if (traceState.isEmpty()) {
            String errorMessage = String.format("未找到编号为“%s”的产品。", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, FabTraceError.ID_NOT_FOUND.toString());
        } else {
            info = gson.fromJson(traceState, TraceInfo.class);
        }
        return gson.toJson(info);
    }

    @Transaction()
    public String queryAllInfo(final Context context) {
        ChaincodeStub stub = context.getStub();
        List<TraceInfo> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");
        for (KeyValue result: results) {
            TraceInfo info = gson.fromJson(result.getStringValue(), TraceInfo.class);
            queryResults.add(info);
        }

        return gson.toJson(queryResults);
    }
}
