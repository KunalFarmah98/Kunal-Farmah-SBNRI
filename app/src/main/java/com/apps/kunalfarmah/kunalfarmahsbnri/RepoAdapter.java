package com.apps.kunalfarmah.kunalfarmahsbnri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.License;
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
        final RepoModel repo = data.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                RepoViewHolder vh = (RepoViewHolder)holder;
                if(repo.getName()==null || repo.getDescription()==null)
                    break;

                vh.name.setText(repo.getName());
                vh.desc.setText(repo.getDescription());
                vh.issues.setText(String.valueOf(repo.getOpen_cont()));

                String perm ="";
                if(repo.getAdmin())perm+="Admin, ";
                if(repo.getPull())perm+="Pull, ";
                if(repo.getPush())perm+="Push, ";

                int l = perm.length();
                vh.permissions.setText(perm.substring(0,l-2));

               vh.licencename.setText(repo.getLname());
               vh.licenseurl.setText(repo.getLurl());
               vh.licenseurl.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String url = repo.getLurl();
                       if(url.equalsIgnoreCase("Not Available")){
                           Toast.makeText(mContext,"Url Not Avaiable",Toast.LENGTH_SHORT).show();
                           return;
                       }
                       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getLurl()));
                       mContext.startActivity(intent);
                   }
               });


                break;
            case LOADING:
//                Do nothing
                break;
        }
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
        int ind = (MainActivity.currentPage-1)*10;
        for (int i=ind; i<rpList.size(); i++) {
            add(rpList.get(i));
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
        TextView name,desc,licencename,licenseurl, permissions,issues;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.description);
            issues = itemView.findViewById(R.id.issues);
            licencename = itemView.findViewById(R.id.licensename);
            licenseurl = itemView.findViewById(R.id.licenseurl);
            permissions  = itemView.findViewById(R.id.permission);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
