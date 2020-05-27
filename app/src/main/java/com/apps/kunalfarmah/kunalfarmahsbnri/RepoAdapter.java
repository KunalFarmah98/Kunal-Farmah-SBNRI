package com.apps.kunalfarmah.kunalfarmahsbnri;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Permissions;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<Repo> data;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public RepoAdapter(Context mContext, List<Repo> list){
        this.mContext = mContext;
        data = list;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = new RepoViewHolder(inflater.inflate(R.layout.list_item,parent,false));
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Repo repo = data.get(position);
        if(ITEM==LOADING)return;

        RepoViewHolder vh = (RepoViewHolder)holder;

        vh.name.setText(repo.getName());
        vh.desc.setText(repo.getDescription());
        vh.issues.setText(repo.getOpenIssues());

        Permissions p = repo.getPermissions();
        String perm ="";
        if(p.isAdmin())perm+="Admin, ";
        if(p.isPull())perm+="Pull, ";
        if(p.isPush())perm+="Push, ";

        int l = perm.length();
        vh.permissions.setText(perm.substring(0,l-2));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == data.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


      /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Repo rp) {
        data.add(rp);
        notifyItemInserted(data.size() - 1);
    }

    public void addAll(List<Repo> rpList) {
        for (Repo rp : rpList) {
            add(rp);
        }
    }

    public void remove(Repo rp) {
        int position = data.indexOf(rp);
        if (position > -1) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Repo());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = data.size() - 1;
        Repo item = getItem(position);

        if (item != null) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Repo getItem(int position) {
        return data.get(position);
    }


    public class RepoViewHolder extends RecyclerView.ViewHolder{
        TextView name,desc,licence,permissions,issues;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.description);
            issues = itemView.findViewById(R.id.issues);
            licence = itemView.findViewById(R.id.license);
            permissions  = itemView.findViewById(R.id.permission);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
