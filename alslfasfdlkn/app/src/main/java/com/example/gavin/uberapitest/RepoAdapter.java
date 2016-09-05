package com.example.gavin.uberapitest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gavin on 6/09/2016.
 */
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    private ArrayList<Repo> repoArrayList;

    public RepoAdapter(ArrayList<Repo> repos) {
        repoArrayList = repos;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_layout, parent, false);
        return new RepoViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        Repo nextRepo = repoArrayList.get(position);
        holder.bindRepo(nextRepo);
    }

    @Override
    public int getItemCount() {
        return repoArrayList.size();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRepo;
        public TextView tvOwner;
        public ImageView ivProfile;
        public Repo dataRepo;

        public RepoViewHolder(View v) {
            super(v);
            tvRepo = (TextView)v.findViewById(R.id.tv_repo);
            tvOwner = (TextView)v.findViewById(R.id.tv_owner);
            ivProfile = (ImageView)v.findViewById(R.id.iv_profile);
        }

        public void bindRepo(Repo repo) {
            dataRepo = repo;
            tvRepo.setText(repo.getName());
            tvOwner.setText(repo.getOwner().getLogin());

        }
    }
}
