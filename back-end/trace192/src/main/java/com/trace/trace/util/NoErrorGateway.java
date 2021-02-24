package com.trace.trace.util;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.spi.Checkpointer;
import org.hyperledger.fabric.gateway.spi.CommitListener;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Peer;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * 模拟一个空gateway，用于在未配置fabric环境时测试
 *
 * @author jbk-xiao
 * @program trace192
 * @packagename com.trace.trace.util
 * @Description
 * @create 2021-02-16-19:26
 */
public class NoErrorGateway implements Gateway {
    @Override
    public Network getNetwork(String networkName) {
        return new Network() {
            @Override
            public Contract getContract(String chaincodeId) {
                return null;
            }

            @Override
            public Contract getContract(String chaincodeId, String name) {
                return null;
            }

            @Override
            public Gateway getGateway() {
                return null;
            }

            @Override
            public Channel getChannel() {
                return null;
            }

            @Override
            public Consumer<BlockEvent> addBlockListener(Consumer<BlockEvent> listener) {
                return null;
            }

            @Override
            public Consumer<BlockEvent> addBlockListener(Checkpointer checkpointer, Consumer<BlockEvent> listener) throws IOException {
                return null;
            }

            @Override
            public Consumer<BlockEvent> addBlockListener(long startBlock, Consumer<BlockEvent> listener) {
                return null;
            }

            @Override
            public void removeBlockListener(Consumer<BlockEvent> listener) {

            }

            @Override
            public CommitListener addCommitListener(CommitListener listener, Collection<Peer> peers, String transactionId) {
                return null;
            }

            @Override
            public void removeCommitListener(CommitListener listener) {

            }
        };
    }

    @Override
    public Identity getIdentity() {
        return null;
    }

    @Override
    public void close() {

    }
}
