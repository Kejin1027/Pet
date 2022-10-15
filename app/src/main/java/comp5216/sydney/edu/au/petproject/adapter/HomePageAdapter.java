package comp5216.sydney.edu.au.petproject.adapter;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.petproject.HomeFragment;
import comp5216.sydney.edu.au.petproject.MeFragment;
import comp5216.sydney.edu.au.petproject.SearchActivity;

public class HomePageAdapter extends FragmentPagerAdapter {
    List<Post> postList = new ArrayList<>();
   public HomePageAdapter(FragmentManager fm, List<Post> value){
       super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);
       this.postList = value;
   }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        if (position == 0) return new HomeFragment();
//        if(position == 3) {
//            MeFragment meFragment = new MeFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("data", this.value);
//            meFragment.setArguments(bundle);
//            return meFragment;
//        }

        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) this.postList);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }


    @Override
    public int getCount() {
        return 4;
    }
}
