package comp5216.sydney.edu.au.petproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImageTranscoderType;
import com.facebook.imagepipeline.core.MemoryChunkType;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.petproject.adapter.HomePageAdapter;
import comp5216.sydney.edu.au.petproject.adapter.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String titles[] = new String[]{};
    private Context context;
    private List<Post> postList;
    private ViewPager viewPager;
    String imgUrl = null;
    JSONArray body = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(
                MainActivity.this,
                ImagePipelineConfig.newBuilder(MainActivity.this)
                        .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                        .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                        .experiment().setNativeCodeDisabled(true)
                        .build());
        context = this;
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        postList = new ArrayList<>();
//        // search result
//        ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    System.out.println("in");
//                    if (result.getResultCode() == 1) {
//                        String updatedResult = (String) result.getData().getSerializableExtra("item");
//                        Log.d("MAIN ACTIVITY", updatedResult);
//                    }
//                }
//        );

        testOKHttp();
        initView();
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 1) {
//            System.out.println(requestCode);
//            String str = data.getStringExtra("item");
//            System.out.println(str);
//        }
//    }

    private void testOKHttp() {
//        String url = "https://publicobject.com/helloworld.txt";
        String url = "http://shibe.online/api/shibes?count=1&urls=true&httpsUrls=false";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    throw new Exception(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                try {
                    body = new JSONArray(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    imgUrl = body.getString(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                 runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            body = new JSONArray(res);
//                            imgUrl = body.getString(0);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });



            }
        });


    }


    private void initView() {

        TabLayout tabLayout = findViewById(R.id.tab_layout);
//        ViewPager viewPager = findViewById(R.id.viewPager);
        HomeFragment homeFragment = new HomeFragment();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Post post1 = new Post(1, "Title one", imgUrl);
        Post post2 = new Post(2, "Title two", imgUrl);
        Post post3 = new Post(3, "Title Three", imgUrl);
        Post post4 = new Post(4, "Title Four", imgUrl);
        Post post5 = new Post(5, "Title Five", imgUrl);
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);
        postList.add(post4);
        postList.add(post5);
        viewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager(), postList));
        viewPager.setOffscreenPageLimit(3);

//        HomeFragment homeFragment = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        Bundle bundle = new Bundle();
//        bundle.putString("data", "PASSING");
//        homeFragment.setArguments(bundle);
//        fragmentList = new ArrayList<Fragment>();
//        fragmentList.add(homeFragment);
//        viewPager.setAdapter(new MainPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setCustomView(createTab("Home", R.drawable.ic_baseline_home_24)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTab("New", R.drawable.ic_baseline_add_circle_24)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTab("Chat", R.drawable.ic_baseline_comment_24)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTab("Me", R.drawable.ic_baseline_account_circle_24)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv = (TextView) tab.getCustomView().findViewById(R.id.tv_title);
                tv.setTextColor(ContextCompat.getColor(context, R.color.red));
                ImageView iv = tab.getCustomView().findViewById(R.id.iv_icon);
                iv.setColorFilter(Color.RED);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv = (TextView) tab.getCustomView().findViewById(R.id.tv_title);
                ImageView iv = tab.getCustomView().findViewById(R.id.iv_icon);
                iv.setColorFilter(Color.BLACK);
                tv.setTextColor(ContextCompat.getColor(context, R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TextView tv =  tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
                ImageView iv =  tabLayout.getTabAt(0).getCustomView().findViewById(R.id.iv_icon);
                tv.setTextColor(ContextCompat.getColor(context, R.color.red));
                iv.setColorFilter(Color.RED);
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private View createTab(String name, int iconId) {
        View newTabItem = LayoutInflater.from(this).inflate(R.layout.my_tab, null);
        TextView tx = (TextView) newTabItem.findViewById(R.id.tv_title);
        tx.setText(name);
        ImageView im = newTabItem.findViewById(R.id.iv_icon);
        im.setImageResource(iconId);
        return newTabItem;
    }
}