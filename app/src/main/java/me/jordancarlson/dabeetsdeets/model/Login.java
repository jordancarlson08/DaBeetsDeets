
package me.jordancarlson.dabeetsdeets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("accountId")
    @Expose
    private String accountId;
    @SerializedName("applicationId")
    @Expose
    private String applicationId;
    @SerializedName("password")
    @Expose
    private String password;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Login(String accountId, String applicationId, String password) {
        this.accountId = accountId;
        this.applicationId = applicationId;
        this.password = password;
    }
}
