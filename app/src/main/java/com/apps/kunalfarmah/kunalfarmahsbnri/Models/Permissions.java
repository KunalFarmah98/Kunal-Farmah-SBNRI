
package com.apps.kunalfarmah.kunalfarmahsbnri.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Permissions {

    @SerializedName("admin")
    @Expose
    private boolean admin;
    @SerializedName("push")
    @Expose
    private boolean push;
    @SerializedName("pull")
    @Expose
    private boolean pull;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public boolean isPull() {
        return pull;
    }

    public void setPull(boolean pull) {
        this.pull = pull;
    }



}
