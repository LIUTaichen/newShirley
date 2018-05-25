package com.dempseywood.config;

import io.github.jhipster.config.JHipsterDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Fleet Management.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final BlackhawkApi blackhawkApi = new BlackhawkApi();

    public BlackhawkApi getBlackhawkApi() {
        return blackhawkApi;
    }

    public static class BlackhawkApi {

        private String username = "";

        private String password = "";

        private String applicationId = "";

        private Long companyId;

        private final Url url = new Url();

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public Long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Long companyId) {
            this.companyId = companyId;
        }

        public Url getUrl() {
            return url;
        }

        public static class Url {

            private String root = "";
            private String login = "";

            public String getRoot() {
                return root;
            }

            public void setRoot(String root) {
                this.root = root;
            }

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }
        }
    }
}
