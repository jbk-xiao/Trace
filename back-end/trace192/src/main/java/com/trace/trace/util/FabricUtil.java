package com.trace.trace.util;

import lombok.extern.slf4j.Slf4j;
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
    /**
     * fabric网路中peer节点所在的主机。
     */
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
     * 初始化fabric的Wallet对象，里边包括两个身份信息文件：admin.id和appUser.id
     *
     * @return 包含admin和appUser两个用户的Wallet对象实例
     */
    @Bean
    protected Wallet initWallet() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propPath));
        } catch (Exception e) {
            log.error(e.toString());
        }

        Wallet wallet = null;

        try {
            HFCAClient caClient = HFCAClient.createNewInstance("https://" + host + ":" + port, props);
            CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
            caClient.setCryptoSuite(cryptoSuite);

            // 依据id文件所在的目录创建wallet对象
            wallet = Wallets.newFileSystemWallet(Paths.get(walletPath));
            X509Identity adminIdentity = null;

            // 检查admin用户是否已经加入网络
            if ((adminIdentity = (X509Identity) wallet.get("admin")) != null) {
                log.warn("An identity for the admin user \"admin\" already exists in the wallet");
            } else {
                //admin用户未加入网络时创建其并加入，并将新的身份信息加入wallet
                final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
                enrollmentRequestTLS.addHost(host);
                enrollmentRequestTLS.setProfile("tls");
                Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
                adminIdentity = Identities.newX509Identity("Org1MSP", enrollment);
                wallet.put("admin", adminIdentity);
                log.info("Successfully enrolled user \"admin\" and imported it into the wallet");
            }
            // 判断当wallet中已存在appUser时，直接返回Wallet对象
            if (wallet.get(user) != null) {
                log.warn("An identity for the user \"" + user + "\" already exists in the wallet");
                return wallet;
            }
            // 创建X509Identity的final对象，用于创建admin的User对象
            final X509Identity finalAdminIdentity = adminIdentity;
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

            // 使用admin注册appUser，让appUser加入网络，并将appUser导入wallet
            RegistrationRequest registrationRequest = new RegistrationRequest(user);
            registrationRequest.setAffiliation("org1.department1");
            registrationRequest.setEnrollmentID(user);
            String enrollmentSecret = caClient.register(registrationRequest, admin);
            Enrollment enrollment = caClient.enroll(user, enrollmentSecret);
            Identity userId = Identities.newX509Identity("Org1MSP", adminIdentity.getCertificate(),
                    adminIdentity.getPrivateKey());
            wallet.put(user, userId);
            log.info("Successfully enrolled user \"" + user + "\" and imported it into the wallet");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wallet;
    }

    @Autowired
    private Wallet wallet;

    /**
     * 使用wallet中的appUser连接到fabric网络，获得Gateway的对象。并将Gateway的对象作为Bean加载。
     * 无fabric环境时交换方法体中注释内容。
     *
     * @return connected fabric Gateway
     * @see #getNetwork()
     */
    @Bean
    public Gateway getGateway() {
        //测试时注释掉下边所有内容
//        Gateway.Builder builder = Gateway.createBuilder();
//        Path networkConfigPath = Paths.get(conPath);
//        try {
//            builder.identity(wallet, user).networkConfig(networkConfigPath).discovery(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return builder.connect();

        //测试时取消下边内容的注释
        return new NoErrorGateway();
    }

    @Autowired
    private Gateway gateway;

    /**
     * 使用已连接入fabric网络的gateway对象连接到mychannel合约，获得Network的对象。
     * 直接使用@Bean注解，一开始就将Network的连接注入spring。
     *
     * @return 已连接到mychannel的network对象。
     */
    @Bean
    public Network getNetwork() {
        return gateway.getNetwork("mychannel");
    }
}
