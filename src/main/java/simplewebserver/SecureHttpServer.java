package simplewebserver;

import simplewebserver.core.ClientHandler;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class SecureHttpServer {
    private static final int PORT = 8443;
    private static final String KEYSTORE_FILE = "keystore.jks";
    private static final String KEYSTORE_PASSWORD = "password";

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

        try (FileInputStream keyStoreStream = new FileInputStream(KEYSTORE_FILE)) {
            keyStore.load(keyStoreStream, KEYSTORE_PASSWORD.toCharArray());
        }

        keyManagerFactory.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
        try (SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT)) {
            System.out.println("Secure server is listening on port " + PORT);

            while (true) {
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }

        }

    }
}
