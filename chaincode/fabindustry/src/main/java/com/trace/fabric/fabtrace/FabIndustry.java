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
 * @program fabindustry-chaincode-java
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-05-11:24
 */
@Contract(
        name = "FabIndustry",
        info = @Info(
                title = "FabIndustry contract",
                description = "The industry process during tracer contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "industry@example.com",
                        name = "fab industry",
                        url = "http://industry.example.com")))
@Default
public final class FabIndustry implements ContractInterface {
    private final Gson gson = new Gson();

    private enum FabIndustryError {
        //
        PRODUCT_NOT_FOUND,
        WRONG_PROCEDURE,
        PRODUCT_HAVE_LEFT;
    }

    enum InitData {
        //
        TIME(1611970163415L), DURATION(432000L), TEMP(10);

        private final long value;

        InitData(final long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        IndustryInfo info;
        long time = InitData.TIME.value;
        long duration = InitData.DURATION.value;
        time += InitData.TEMP.value * duration;
        info = new IndustryInfo("16119701634150000", "工厂", "李四");
        info.addProcedureInfo("烧制玻璃瓶", "李四一", true, time);
        time += duration;
        info.addProcedureInfo("烧制玻璃瓶", "李四一", false, time);
        info.addProcedureInfo("炒制", "李四二", true, time);
        time += duration;
        info.addProcedureInfo("炒制", "李四二", false, time);
        info.addProcedureInfo("罐装", "李四三", true, time);
        time += duration;
        info.addProcedureInfo("罐装", "李四三", false, time);
        info.addProcedureInfo("机器拧盖", "李重四", true, time);
        time += duration;
        info.addProcedureInfo("机器拧盖", "李重四", false, time);
        stub.putStringState(info.getId(), gson.toJson(info));
    }

    @Transaction()
    public String queryAllInfo(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<IndustryInfo> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");
        for (KeyValue result: results) {
            IndustryInfo info = gson.fromJson(result.getStringValue(), IndustryInfo.class);
            queryResults.add(info);
        }

        return gson.toJson(queryResults);
    }

    @Transaction
    public String queryInfoByID(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        IndustryInfo info;
        String state = stub.getStringState(id);
        if (state.isEmpty()) {
            String errorMesssage = String.format("找不到id为%s的产品。", id);
            System.out.println(errorMesssage);
            throw new ChaincodeException(errorMesssage, FabIndustryError.PRODUCT_NOT_FOUND.toString());
        } else {
            info = gson.fromJson(state, IndustryInfo.class);
        }
        return gson.toJson(info);
    }

    @Transaction
    public String procedureStart(final Context ctx, final String id, final String name, final String master,
                                 final String inname, final String inmaster, final long time) {
        ChaincodeStub stub = ctx.getStub();
        IndustryInfo info;
        String state = stub.getStringState(id);
        if (state.isEmpty()) {
            info = new IndustryInfo(id, name, master, inname, inmaster, true, time);
        } else {
            info = gson.fromJson(state, IndustryInfo.class);
            info.addProcedureInfo(inname, inmaster, true, time);
        }
        String infoStr = gson.toJson(info);
        stub.putStringState(id, infoStr);
        return infoStr;
    }

    @Transaction
    public String procedureEnd(final Context ctx, final String id, final String name, final String master,
                               final String inname, final String inmaster, final long time) {
        ChaincodeStub stub = ctx.getStub();
        IndustryInfo info;
        String state = stub.getStringState(id);
        if (state.isEmpty()) {
            String errorMessage = String.format("未找到编号为“%s”的产品。", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, FabIndustryError.PRODUCT_NOT_FOUND.toString());
        } else {
            info = gson.fromJson(state, IndustryInfo.class);
            List<IndustryInfo.ProcedureInfo> list = info.getProcedure();
            IndustryInfo.ProcedureInfo procedureInfo = list.get(list.size() - 1);
            if (procedureInfo.getStart() && inname.equals(procedureInfo.getName())) {
                info.addProcedureInfo(inname, inmaster, false, time);
            } else if (!procedureInfo.getStart() && inname.equals(procedureInfo.getName())) {
                String errorMessage = String.format("批号为“%s”的产品已登记离开工序“%s”。", id, inname);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, FabIndustryError.PRODUCT_HAVE_LEFT.toString());
            } else {
                String errorMessage = String.format("批号为“%s”的产品不属于当前工序。", id);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, FabIndustryError.WRONG_PROCEDURE.toString());
            }
        }
        String infoStr = gson.toJson(info);
        stub.putStringState(id, infoStr);
        return infoStr;
    }
}
