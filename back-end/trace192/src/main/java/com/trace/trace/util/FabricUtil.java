package com.trace.trace.util;

import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

/**
 * @author jbk-xiao
 * @program trace
 * @packagename com.trace.trace.util
 * @Description
 * @create 2021-02-01-20:18
 */
@Slf4j
@Component
public class FabricUtil {
//    static {
//        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
//    }

    @Value("${fabric.system.host}")
    private String host;

    @Value("${fabric.system.port}")
    private String port;

    @Value("${fabric.system.user}")
    private String user;

    @Value("${fabric.system.wallet}")
    private String walletPath;

    @Value("${fabric.system.properties}")
    private String propPath;

    @Value("${fabric.system.ca-pem}")
    private String caPath;

    @Value("${fabric.system.conpath}")
    private String conPath;

    public FabricUtil() {

    }

    /**
     * initialize fabric wallet, create admin and appUser
     * @return initialized wallet
     */
    @Bean
    protected Wallet initWallet() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Wallet wallet = null;

        try {
            HFCAClient caClient = HFCAClient.createNewInstance("https://" + host + ":" + port, props);
            CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
            caClient.setCryptoSuite(cryptoSuite);

            // Create a wallet for managing identities
            wallet = Wallets.newFileSystemWallet(Paths.get(walletPath));
            X509Identity adminIdentity = null;

            // Check to see if we've already enrolled the admin user.
            if (wallet.get("admin") != null) {
                log.warn("An identity for the admin user \"admin\" already exists in the wallet");
            } else {
                // Enroll the admin user, and import the new identity into the wallet.
                final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
                enrollmentRequestTLS.addHost(host);
                enrollmentRequestTLS.setProfile("tls");
                Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
                adminIdentity = Identities.newX509Identity("Org1MSP", enrollment);
                wallet.put("admin", adminIdentity);
                log.info("Successfully enrolled user \"admin\" and imported it into the wallet");
            }

            if (wallet.get(user) != null) {
                log.warn("An identity for the user \"" + user + "\" already exists in the wallet");
            } else {
                if (adminIdentity == null) {
                    log.error("\"admin\" needs to be enrolled and added to the wallet first!!");
                }
                X509Identity finalAdminIdentity = adminIdentity;
                User admin = new User() {

                    @Override
                    public String getName() {
                        return "admin";
                    }

                    @Override
                    public Set<String> getRoles() {
                        return null;
                    }

                    @Override
                    public String getAccount() {
                        return null;
                    }

                    @Override
                    public String getAffiliation() {
                        return "org1.department1";
                    }

                    @Override
                    public Enrollment getEnrollment() {
                        return new Enrollment() {

                            @Override
                            public PrivateKey getKey() {
                                return finalAdminIdentity.getPrivateKey();
                            }

                            @Override
                            public String getCert() {
                                return Identities.toPemString(finalAdminIdentity.getCertificate());
                            }
                        };
                    }

                    @Override
                    public String getMspId() {
                        return "Org1MSP";
                    }

                };

                // Register the user, enroll the user, and import the new identity into the wallet.
                RegistrationRequest registrationRequest = new RegistrationRequest(user);
                registrationRequest.setAffiliation("org1.department1");
                registrationRequest.setEnrollmentID(user);
                String enrollmentSecret = caClient.register(registrationRequest, admin);
                Enrollment enrollment = caClient.enroll(user, enrollmentSecret);
                Identity userId = Identities.newX509Identity("Org1MSP", adminIdentity.getCertificate(),
                        adminIdentity.getPrivateKey());
                wallet.put(user, userId);
                log.info("Successfully enrolled user \"" + user + "\" and imported it into the wallet");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wallet;
    }

    @Autowired
    private Wallet wallet;

    /**
     * use appUser to connect to fabric network.
     * @return connected fabric Gateway
     */
    public Gateway getGateway() {
        Gateway.Builder builder = Gateway.createBuilder();
        Path networkConfigPath = Paths.get(conPath);
        try {
            builder.identity(wallet, user).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.connect();
    }

    /**
     * test speed
     * @return network to mychannel
     */
    public Network getNetwork() {
        Gateway.Builder builder = Gateway.createBuilder();
        Path networkConfigPath = Paths.get(conPath);
        try {
            builder.identity(wallet, user).networkConfig(networkConfigPath).discovery(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.connect().getNetwork("mychannel");
    }
}
