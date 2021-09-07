package intern.siva.post;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Postadapter extends RecyclerView.Adapter<Postadapter.postViewholder>{
    String totalLikes;
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
        Log.d("like","like"+posts.getLikes());

        holder.totalcomment.setText(posts.getComments());

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
        holder.liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.status=="liked it")
                {
                    String id=posts.getId();
                    fordislke(id);
                    holder.liked.setImageResource(R.drawable.like);
                    holder.status="already liked it";
                }
                else
                {
                    String id=posts.getId();
                    forlike(id);
                    holder.liked.setImageResource(R.drawable.likebold);
                    holder.status = "liked it";
                }

            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likesonpost(posts.getId());
                Intent i =new Intent(context,CommentSection.class);
                i.putExtra("image",imageurl);
                i.putExtra("username",posts.getName());
                i.putExtra("id",posts.getId());
                i.putExtra("totalLikes",holder.totalLikes);
                context.startActivity(i);


            }
        });
    }

    private void fordislke(String id) {
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(context);

        String apiurl = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/postDislike/"+id;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, apiurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject= new JSONObject(response.toString());
                    Log.d("tag",""+response);
                    if( jsonObject.getString("error").equals(""))
                        Toast.makeText(context, "Disliked", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Log.d("Likeapi", "likebug "+e);
                    Toast.makeText(context,"NO data fetched",Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"NO data fetched",Toast.LENGTH_SHORT).show();

            }


        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("authtoken","0P1EYPE7B2OSZ198S2WTVI7BLYCP8J7QV4WCG9FHBXHBMOOD6G");
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }

    private void forlike(String id) {
            {   RequestQueue requestQueue;
                requestQueue= Volley.newRequestQueue(context);

                String apiurl = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/postLike/"+id;

                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, apiurl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject jsonObject= new JSONObject(response.toString());
                               if( jsonObject.getString("error").equals(""))
                                   Toast.makeText(context, "Liked now", Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                Log.d("Likeapi", "likebug "+e);
                                Toast.makeText(context,"NO data fetched",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,"NO data fetched",Toast.LENGTH_SHORT).show();

                        }


                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("authtoken","0P1EYPE7B2OSZ198S2WTVI7BLYCP8J7QV4WCG9FHBXHBMOOD6G");
                            return params;
                        }
                    };
                request.setRetryPolicy(new DefaultRetryPolicy(
                        6000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(request);

    }

    }

    private void likesonpost(String id) {
        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(context);
        String apiurl="https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/single_post/"+id;
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, apiurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray =response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject hit=jsonArray.getJSONObject(i);
                        totalLikes =hit.getString("total_like");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();


            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("authtoken", "0P1EYPE7B2OSZ198S2WTVI7BLYCP8J7QV4WCG9FHBXHBMOOD6G");
                return params;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);


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
        String status;
        TextView comment;
        TextView totalcomment;
        String totalLikes;

        public postViewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.myimg);
            textView=itemView.findViewById(R.id.caption);
            dates=itemView.findViewById(R.id.date);
            liked=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comments);
            fullscreenimage=itemView.findViewById(R.id.myimg);
            totalcomment=itemView.findViewById(R.id.noofComments);

        }
    }
}
