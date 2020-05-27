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
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.RepoModel;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<RepoModel> data;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;


    public RepoAdapter(Context mContext, List<RepoModel> list){
        this.mContext = mContext;
        data = list;
    }

    public RepoAdapter(Context mContext){
        this.mContext=mContext;
        this.data = new ArrayList<>();
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
        RepoModel repo = data.get(position);
        if(ITEM==LOADING)return;

        RepoViewHolder vh = (RepoViewHolder)holder;

        vh.name.setText(repo.getName());
        vh.desc.setText(repo.getDescription());
        vh.issues.setText(repo.getOpen_cont());

        String perm ="";
        if(repo.getAdmin())perm+="Admin, ";
        if(repo.getPull())perm+="Pull, ";
        if(repo.getPush())perm+="Push, ";

        int l = perm.length();
        vh.permissions.setText(perm.substring(0,l-2));

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == data.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


      /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(RepoModel rp) {
        data.add(rp);
        notifyItemInserted(data.size() - 1);
    }

    public void addAll(List<RepoModel> rpList) {
        for (RepoModel rp : rpList) {
            add(rp);
        }
    }

    public void remove(RepoModel rp) {
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
        add(new RepoModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = data.size() - 1;
        RepoModel item = getItem(position);

        if (item != null) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public RepoModel getItem(int position) {
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
