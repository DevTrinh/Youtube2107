package com.example.youtubeapp.pagination;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private final LinearLayoutManager linearLayoutManager;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

//       count the items seen on the screen
        int visibleItemCount = linearLayoutManager.getChildCount();

//        total number of records in a page
        int totalItemCount = linearLayoutManager.getItemCount();

        Toast.makeText(recyclerView.getContext(), totalItemCount+": total item count", Toast.LENGTH_SHORT).show();

//        item first
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (isLoading() || isLastPage()) {
//            no handle paging
            return;
        }
        if (firstVisibleItemPosition >= 0 && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
//            not belong two handle
            loadMoreItem();
        }
    }

    public abstract void loadMoreItem();

    public abstract boolean isLoading();

    public abstract boolean isLastPage();
}
