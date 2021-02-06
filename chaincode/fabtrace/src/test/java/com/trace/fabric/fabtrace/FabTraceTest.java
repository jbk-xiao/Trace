package com.trace.fabric.fabtrace;

import com.google.gson.Gson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * @author jbk-xiao
 * @program fabtrace
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-02-04-17:18
 */
public final class FabTraceTest {
    private final class MockKeyValue implements KeyValue {

        private final String key;
        private final String value;

        MockKeyValue(final String key, final String value) {
            super();
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public String getStringValue() {
            return this.value;
        }

        @Override
        public byte[] getValue() {
            return this.value.getBytes();
        }
    }
    private final class MockTraceResultIterator implements QueryResultsIterator<KeyValue> {
        private final List<KeyValue> traceInfoList;

        MockTraceResultIterator() {
            super();
            traceInfoList = new ArrayList<>();
            traceInfoList.add(new MockKeyValue("123456", "{\"id\":\"123456\",\"ProcessInfo\":["
                + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":true,\"time\":1611970163415},"
                + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":false,\"time\":1611970163415}]}"));
        }
        @Override
        public void close() throws Exception {
            // do nothing
        }

        @Override
        public Iterator<KeyValue> iterator() {
            return traceInfoList.iterator();
        }
    }
    @Test
    public void invokeUnknownTransaction() {
        FabTrace contract = new FabTrace();
        Context ctx = mock(Context.class);

        Throwable thrown = catchThrowable(() -> {
            contract.unknownTransaction(ctx);
        });

        assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                .hasMessage("Undefined contract method called");
        assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo(null);

        verifyZeroInteractions(ctx);
    }

    @Nested
    class InvokeQueryTraceInfoTransaction {
        @Test
        public void whenTraceInfoExists() {
            FabTrace contract = new FabTrace();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("123456"))
                    .thenReturn("{\"id\":\"123456\",\"process\":["
                    + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":true,\"time\":1611970163415},"
                    + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":false,\"time\":1611970163415}]}");
            String newInfo = contract.queryInfoByID(ctx, "123456");
            TraceInfo dueInfo = new TraceInfo("123456");
            dueInfo.addProcessInfo("工厂", "李四", true, FabTrace.InitData.TIME.getValue());
            dueInfo.addProcessInfo("工厂", "李四", false, FabTrace.InitData.TIME.getValue());
            System.out.println(newInfo);
            assertThat(newInfo).isEqualTo(new Gson().toJson(dueInfo));
        }
    }

    @Test
    void test() {
        TraceInfo dueInfo = new TraceInfo("123456");
        dueInfo.addProcessInfo("工厂", "李四", true, FabTrace.InitData.TIME.getValue());
        dueInfo.addProcessInfo("工厂", "李四", false, FabTrace.InitData.TIME.getValue());
        Gson gson = new Gson();
        String infoStr = gson.toJson(dueInfo);
        System.out.println(infoStr);
        System.out.println("{\"id\":\"123456\",\"process\":["
            + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":true,\"time\":1611970163415},"
            + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":false,\"time\":1611970163415}]}");
        TraceInfo info = gson.fromJson(infoStr, TraceInfo.class);
        System.out.println(info);
        System.out.println(gson.fromJson("{\"id\":\"123456\",\"process\":["
            + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":true,\"time\":1611970163415},"
            + "{\"name\":\"工厂\",\"master\":\"李四\",\"enter\":false,\"time\":1611970163415}]}", TraceInfo.class));
    }
}
