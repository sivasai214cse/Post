package intern.siva.post;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.Transliterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Postadapter extends RecyclerView.Adapter<Postadapter.postViewholder>{

    Context context;
   public ArrayList<Model> post;

    public Postadapter(Context context, ArrayList<Model> post)
    {
        this.context = context;
        this.post = post;
    }


    @NonNull
    @NotNull
    @Override
    public postViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(context).inflate(R.layout.post,parent,false);
        final postViewholder viewHolder = new postViewholder(view) ;
        return new postViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Postadapter.postViewholder holder, int position) {

    Model posts = post.get(position);
    String imageurl=posts.getImage();

    holder.textView.setText(posts.getName());
    holder.dates.setText(posts.getDate());
    Glide.with(context)
            .load(imageurl).fitCenter()
            .into(holder.imageView);
        if(posts.getLikes().equals("already Liked it"))
        {
            Log.d("like","like"+posts.getName());
            holder.liked.setImageResource(R.drawable.likebold);
        }

        holder.fullscreenimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context,CommentSection.class);
               i.putExtra("image",imageurl);
               i.putExtra("username",posts.getName());
               i.putExtra("id",posts.getId());
               context.startActivity(i);

            }
        });
//
    }

    @Override
    public int getItemCount() {
            return post.size();
    }

    public class postViewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView dates;
        ImageView liked;
        ImageView fullscreenimage;
        TextView comment;
        Button chooseimage;

        public postViewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.pic1);
            textView=itemView.findViewById(R.id.name);
            dates=itemView.findViewById(R.id.date);
            liked=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comments);
            fullscreenimage=itemView.findViewById(R.id.pic1);
            liked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"already liked",Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
