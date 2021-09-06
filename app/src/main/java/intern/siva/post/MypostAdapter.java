package intern.siva.post;

import android.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MypostAdapter extends RecyclerView.Adapter<MypostAdapter.Mypostholder> {
    Context context;
   public ArrayList<Model3owbpost> mypost ;
    public MypostAdapter (Context context,ArrayList<Model3owbpost> mypost)
    {
        this.context=context;
        this.mypost=mypost;
    }
    @NonNull
    @Override
    public Mypostholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mypost,parent,false);
        final Mypostholder viewHolder = new Mypostholder(view) ;
        return new Mypostholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Mypostholder holder, int position) {

        Model3owbpost myposts=mypost.get(position);
        String imageurl=myposts.getImage();

        holder.details.setText(myposts.getDetails());
        holder.dateandtime.setText(myposts.getDate());
        Glide.with(context)
                .load(imageurl).fitCenter()
                .into(holder.postedimg);
        holder.likes.setText(myposts.getLike());
        int id =Integer.parseInt(myposts.getId());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog= new AlertDialog.Builder(context);
                alertDialog.setCancelable(false)
                        .setTitle("Delete permanently?")
                        .setPositiveButton("Confirm", (dialog, which) -> {deletepost(id);})
                        .setNegativeButton("Cancel", (dialog, which) -> {})
                        .setMessage("!!!").show();
            }
        });

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context,CommentSection.class);
                i.putExtra("image",imageurl);
                i.putExtra("username", myposts.getDetails());
                i.putExtra("id",myposts.getId());
                context.startActivity(i);
            }
        });

    }

    private void deletepost(int id) {

        String url="https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/deletePost";
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        HashMap<String, Object> hashMap=new HashMap<>();

        hashMap.put("is_comment",0);
        Log.d("id","id of   user-"+id);
        hashMap.put("id",id);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url,new JSONObject(hashMap),
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        Log.d("mypost data","image-"+id);
                        Log.d("Response is ",response.toString());
                        try{
                            if(response.getString("error").equals("")) {
                                Toast.makeText(context, "deleted", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(context, "failed", Toast.LENGTH_LONG).show();

                        }catch(JSONException e){
                            Toast.makeText(context,"json error "+e.toString(),Toast.LENGTH_SHORT).show();
                            Log.d("jsonException",e.toString());
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

                Toast.makeText(context,"error "+error.toString(),Toast.LENGTH_SHORT).show();//enable the button
                Log.d("delete error","Service Error:"+error.toString());

            }
        })
        {
            @Override
            public Map<String, String> getHeaders()throws AuthFailureError{
                Map<String, String> params=new HashMap<String, String>();
                params.put("authtoken","0P1EYPE7B2OSZ198S2WTVI7BLYCP8J7QV4WCG9FHBXHBMOOD6G");
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
    }



    @Override
    public int getItemCount() {
        return mypost.size();
    }

    public class Mypostholder extends RecyclerView.ViewHolder{

       TextView details;
       TextView dateandtime;
       TextView likes;
       ImageView postedimg;
       ImageView delete;
       TextView comments;

        public Mypostholder(@NonNull View itemView) {
            super(itemView);
            dateandtime=itemView.findViewById(R.id.date);
            postedimg=itemView.findViewById(R.id.myimg);
            details=itemView.findViewById(R.id.caption);
            delete=itemView.findViewById(R.id.deletepost);
            likes=itemView.findViewById(R.id.mylikes);
            comments=itemView.findViewById(R.id.comments);



        }
    }




}
