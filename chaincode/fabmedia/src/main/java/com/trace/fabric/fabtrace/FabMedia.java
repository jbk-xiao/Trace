package com.trace.fabric.fabtrace;

import com.owlike.genson.Genson;
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
 * @program media-operation-chaincode-java
 * @packagename com.trace.fabric.fabtrace
 * @Description
 * @create 2021-01-29-17:29
 */
@Contract(
        name = "FabMedia",
        info = @Info(
                title = "FabMedia contract",
                description = "The media during tracer contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "media@example.com",
                        name = "fab media",
                        url = "http://media.example.com")))
@Default
public final class FabMedia implements ContractInterface {
    private final Genson genson = new Genson();

    private enum FabMediaError {
        MEDIA_NOT_FOUND,
        MEDIA_ALREADY_EXISTS
    }

    @Transaction()
    public void initLedger(final Context context) {
        ChaincodeStub stub = context.getStub();

        final long checkTime1 = 123456;
        MediaInfo media = new MediaInfo(2, "20210125_201133.mp4", "@#RVEBH$^H%BW#$GTJ%YJJH", checkTime1);
        String mediaInfo = genson.serialize(media);
        stub.putStringState(media.getFilename(), mediaInfo);
        final long checkTime2 = 456789;
        MediaInfo media2 = new MediaInfo(1, "test.jpg", "123456", checkTime2);
        String mediaInfo2 = genson.serialize(media2);
        stub.putStringState(media2.getFilename(), mediaInfo2);
    }

    @Transaction()
    public MediaInfo addMedia(final Context context, final int type, final String filename,
                              final String md5code, final long checkTime) {
        ChaincodeStub stub = context.getStub();

        String mediaState = stub.getStringState(filename);
        if (!mediaState.isEmpty()) {
            String errorMessage = String.format("Media %s already exists", filename);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, FabMediaError.MEDIA_ALREADY_EXISTS.toString());
        }

        MediaInfo media = new MediaInfo(type, filename, md5code, checkTime);
        String mediaInfo = genson.serialize(media);
        stub.putStringState(filename, mediaInfo);

        return media;
    }

    @Transaction()
    public MediaInfo queryMedia(final Context context, final String filename) {
        ChaincodeStub stub = context.getStub();

        String mediaStat = stub.getStringState(filename);
        if (mediaStat.isEmpty()) {
            String errorMessage = String.format("Media %s does not exist", filename);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, FabMediaError.MEDIA_NOT_FOUND.toString());
        }

        MediaInfo mediaInfo = genson.deserialize(mediaStat, MediaInfo.class);
        return mediaInfo;
    }

    @Transaction()
    public String queryAllMedia(final Context context) {
        ChaincodeStub stub = context.getStub();

        List<MediaInfo> queryResults = new ArrayList<>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue result: results) {
            queryResults.add(genson.deserialize(result.getStringValue(), MediaInfo.class));
        }

        final String response = genson.serialize(queryResults);

        return response;
    }
}
