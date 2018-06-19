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
            private String vehicle= "/Vehicle";
            private String geofence="/Geofence?includeGeometries=true&roleBasedFilter=false";

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

            public String getVehicle() {
                return vehicle;
            }

            public void setVehicle(String vehicle) {
                this.vehicle = vehicle;
            }

            public String getGeofence() {
                return geofence;
            }

            public void setGeofence(String geofence) {
                this.geofence = geofence;
            }
        }
    }

    private final Notification notification = new Notification();
    public Notification getNotification(){
        return notification;
    }
    public static class Notification{

        private final OnHold onHold = new OnHold();

        public OnHold getOnHold(){
            return onHold;
        }
        public static class OnHold {
            private String to = "jason.liu@dempseywood.co.nz";

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }
        }

        private final HighPriority highPriority = new HighPriority();
        public HighPriority getHighPriority(){
            return highPriority;
        }
        public static class HighPriority {
            private String to = "jason.liu@dempseywood.co.nz";

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }
        }
    }
}
