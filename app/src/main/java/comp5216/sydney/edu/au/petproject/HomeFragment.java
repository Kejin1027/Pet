package comp5216.sydney.edu.au.petproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.petproject.adapter.HomeListAdapter;
import comp5216.sydney.edu.au.petproject.adapter.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    List<Post> arrayList;
    String urlImg;
    ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == 1) {
                    String updatedResult = (String) result.getData().getSerializableExtra("item");
                    Log.d("MAIN ACTIVITY", updatedResult);
                }
            }
    );


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView.findViewById(R.id.rv);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        news = new ArrayList<>();
//        Bundle bundle = getArguments();
//        check = bundle.getString("data");
//        adapter = new HomeListAdapter(this, check, news);
//    }
        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

//        Bundle bundle = getArguments();
//        check = bundle.getString("data");
//        adapter = new HomeListAdapter(this, check, news);
        arrayList = new ArrayList<>();
        Bundle bundle = getArguments();
        this.arrayList = (List<Post>) bundle.getSerializable("data");
        recyclerView = view.findViewById(R.id.rv);

//        arrayList.add("1");
//        arrayList.add("2");
//        arrayList.add("3");
//        arrayList.add("4");
//        arrayList.add("5");
//        arrayList.add("6");
//        arrayList.add("7");
//        arrayList.add("8");
//        arrayList.add("9");
//        arrayList.add("10");
        adapter = new HomeListAdapter(this, arrayList, new HomeListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Post post) {
                System.out.println("ITEM"+ "-" +post.getId());
                urlImg = post.getImgUrl();
            }

        });


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
//        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(20);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }
        });

        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    Log.i("CHCEK", "NO MORE ITEMS!");
                    Post post = new Post(6, "Title Six", urlImg);
                    Post post2 = new Post(7, "Title Seven", urlImg);
                    arrayList.add(post);
                    arrayList.add(post2);
//                    adapter.notifyDataSetChanged();
//                    arrayList.add("11");
//                    arrayList.add("12");
//                    arrayList.add("13");
//                    arrayList.add("14");
//                    arrayList.add("15");
//                    arrayList.add("16");
//                    arrayList.add("17");
//                    arrayList.add("18");
//                    arrayList.add("19");
//                    arrayList.add("20");
                    Log.i("COUNT", Integer.toString(adapter.getItemCount()));
                }

            }
        });

        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
//                view.getContext().startActivity(new Intent(view.getContext(), SearchActivity.class));
                myLauncher.launch(intent);
            }
        });


        view.findViewById(R.id.post_sort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = LayoutInflater.from(requireContext()).inflate(R.layout.sort_layout, null);
                PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                        true);
                popupWindow.showAsDropDown(view);

                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = view.getId();
                        if(id == R.id.sort1 || id == R.id.sort2){
                            Toast.makeText(requireContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        }
                    }
                };

                contentView.findViewById(R.id.sort1).setOnClickListener(clickListener);
                contentView.findViewById(R.id.sort2).setOnClickListener(clickListener);
            }
        });


    }




}