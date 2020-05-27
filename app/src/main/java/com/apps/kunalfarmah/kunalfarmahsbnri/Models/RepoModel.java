package com.apps.kunalfarmah.kunalfarmahsbnri.Models;

import android.graphics.Movie;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "Repo")

public class RepoModel {
    public RepoModel(){

    }
    public RepoModel(@NonNull String id, String name, String description, String lkey, String lname, String lspdxId, String lurl, String lnodeId, Boolean admin, Boolean push, Boolean pull, int open_cont) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lkey = lkey;
        this.lname = lname;
        this.lspdxId = lspdxId;
        this.lurl = lurl;
        this.lnodeId = lnodeId;
        this.admin = admin;
        this.push = push;
        this.pull = pull;
        this.open_cont = open_cont;
    }

    @NonNull
    @PrimaryKey
    String id;
    String name, description;
    String lkey, lname, lspdxId, lurl, lnodeId;
    Boolean admin, push, pull;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLkey() {
        return lkey;
    }

    public void setLkey(String lkey) {
        this.lkey = lkey;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLspdxId() {
        return lspdxId;
    }

    public void setLspdxId(String lspdxId) {
        this.lspdxId = lspdxId;
    }

    public String getLurl() {
        return lurl;
    }

    public void setLurl(String lurl) {
        this.lurl = lurl;
    }

    public String getLnodeId() {
        return lnodeId;
    }

    public void setLnodeId(String lnodeId) {
        this.lnodeId = lnodeId;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getPush() {
        return push;
    }

    public void setPush(Boolean push) {
        this.push = push;
    }


    public Boolean getPull() {
        return pull;
    }

    public void setPull(Boolean pull) {
        this.pull = pull;
    }

    public int getOpen_cont() {
        return open_cont;
    }

    public void setOpen_cont(int open_cont) {
        this.open_cont = open_cont;
    }

    int open_cont;
}
