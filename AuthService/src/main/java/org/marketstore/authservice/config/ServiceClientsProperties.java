package org.marketstore.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "service-clients")
public class ServiceClientsProperties {

    private Map<String, Credentials> clients = new LinkedHashMap<>();

    public Map<String, Credentials> getClients() {
        return clients;
    }

    public void setClients(Map<String, Credentials> clients) {
        this.clients = clients;
    }

    public static class Credentials {
        private String clientId;
        private String clientSecret;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }
}
