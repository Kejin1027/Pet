package comp5216.sydney.edu.au.petproject.adapter;


import android.content.res.Resources;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

import comp5216.sydney.edu.au.petproject.HomeFragment;
import comp5216.sydney.edu.au.petproject.R;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeViewHolder> {

    LayoutInflater inflater = null;
    String target;
    Fragment context;
    List<Post> list;
    private ItemClickListener itemClickListener;
    public HomeListAdapter(HomeFragment context,List<Post> list, ItemClickListener itemClickListener){
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }



    public class HomeViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView post_text;
        public ImageView post_like;
        public TextView like_num;
        public ImageView user_photo;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.post_iv);
            post_like = itemView.findViewById(R.id.post_like);
            post_text = itemView.findViewById(R.id.post_content);
            like_num = itemView.findViewById(R.id.like_number);
            user_photo = itemView.findViewById(R.id.user_photo);
        }
    }

    @NonNull
    @Override
    public HomeListAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set list item layout and set click listener to start detail page
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }

        View view = inflater.inflate(R.layout.item_home, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), DetailActivity.class);
//                view.getContext().startActivities(intent);
//            }
//        });

        return new HomeViewHolder(view);
    }

    /**
     * Change dp = px
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    int minHeight = 150;
    int maxHeight = 200;

    public interface ItemClickListener{
        void onItemClick(Post post);
    }
    @Override
    public void onBindViewHolder(@NonNull HomeListAdapter.HomeViewHolder holder, int position) {
//        Random random = new Random();
//        int r = random.nextInt(256);
//        int g = random.nextInt(256);
//        int b = random.nextInt(256);
//        holder.imageView.setBackgroundColor(Color.rgb(r, g, b));
//
        int height = (int) (minHeight + Math.random() * (maxHeight - minHeight + 1));
        View view = holder.imageView;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = dp2px(height);
        view.setLayoutParams(layoutParams);

        Uri uri = Uri.parse(this.list.get(position).getImgUrl());
        holder.imageView.setImageURI(uri);

//        Picasso.get().load(this.list.get(position).getImgUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(view1 -> {
            itemClickListener.onItemClick(this.list.get(position));
        });

        holder.post_text.setText(this.list.get(position).getTitle());
        holder.post_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.post_like.setSelected(!holder.post_like.isSelected());
                if(holder.post_like.isSelected()){
                    // -1
                    int sub = Integer.parseInt((String) holder.like_num.getText()) + 1;
                    holder.like_num.setText(String.valueOf(sub));
                }else{
                    // +1
                    int sub = Integer.parseInt((String) holder.like_num.getText()) - 1;
                    holder.like_num.setText(String.valueOf(sub));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }



}
