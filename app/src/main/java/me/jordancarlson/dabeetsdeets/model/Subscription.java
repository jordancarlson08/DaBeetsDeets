
package me.jordancarlson.dabeetsdeets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscription {

    @SerializedName("ContactId")
    @Expose
    private String contactId;
    @SerializedName("ContactName")
    @Expose
    private Object contactName;
    @SerializedName("DateTimeCreated")
    @Expose
    private DateTimeCreated dateTimeCreated;
    @SerializedName("DateTimeModified")
    @Expose
    private DateTimeModified dateTimeModified;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("InvitationId")
    @Expose
    private String invitationId;
    @SerializedName("InviteExpires")
    @Expose
    private InviteExpires inviteExpires;
    @SerializedName("IsEnabled")
    @Expose
    private Boolean isEnabled;
    @SerializedName("IsMonitoringSessionActive")
    @Expose
    private Boolean isMonitoringSessionActive;
    @SerializedName("Permissions")
    @Expose
    private Integer permissions;
    @SerializedName("State")
    @Expose
    private Integer state;
    @SerializedName("SubscriberId")
    @Expose
    private String subscriberId;
    @SerializedName("SubscriptionId")
    @Expose
    private String subscriptionId;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Object getContactName() {
        return contactName;
    }

    public void setContactName(Object contactName) {
        this.contactName = contactName;
    }

    public DateTimeCreated getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(DateTimeCreated dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public DateTimeModified getDateTimeModified() {
        return dateTimeModified;
    }

    public void setDateTimeModified(DateTimeModified dateTimeModified) {
        this.dateTimeModified = dateTimeModified;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
    }

    public InviteExpires getInviteExpires() {
        return inviteExpires;
    }

    public void setInviteExpires(InviteExpires inviteExpires) {
        this.inviteExpires = inviteExpires;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsMonitoringSessionActive() {
        return isMonitoringSessionActive;
    }

    public void setIsMonitoringSessionActive(Boolean isMonitoringSessionActive) {
        this.isMonitoringSessionActive = isMonitoringSessionActive;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

}
