package intern.siva.post;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import intern.siva.post.Model2;
import intern.siva.post.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewholder>{

    ArrayList<Model2> comment;
    Context context;

    public CommentAdapter(Context context,ArrayList<Model2> comment)
    {
        this.context=context;
        this.comment=comment;
    }

    @NonNull
    @Override
    public CommentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageandcomment,parent,false);
        final CommentViewholder viewholder =new CommentViewholder(view);
        return new CommentViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewholder holder, int position) {

       Model2 comments = comment.get(position);
       holder.commentusername.setText(comments.getUser());
       holder.usercomment.setText(comments.getBody());
       holder.commentTime.setText(comments.getTime());
        Log.d("added","comment-"+comments.getBody());


    }

    @Override
    public int getItemCount() {
        return comment.size();
    }

    public class CommentViewholder extends RecyclerView.ViewHolder
    {   TextView commentusername;
        TextView usercomment;
        TextView commentTime;


        public CommentViewholder(@NonNull View itemView) {
            super(itemView);
            commentusername=itemView.findViewById(R.id.userNameCommented);
            usercomment=itemView.findViewById(R.id.commentBody);
            commentTime=itemView.findViewById(R.id.commenttime);
        }
    }
}
