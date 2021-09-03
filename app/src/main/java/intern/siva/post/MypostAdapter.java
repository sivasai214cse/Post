package intern.siva.post;

import android.content.Context;
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

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MypostAdapter extends RecyclerView.Adapter<MypostAdapter.Mypostholder> {
    Context context;
    ArrayList<Model3owbpost> mypost ;
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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletepost("5");
            }
        });

    }
    private void deletepost(String id) {
         RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(context);

        String  apiurl="https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/deletePost";
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("id" ,id);
        hashMap.put("iscomment","0");
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.POST, apiurl, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {  //Log.d("sucess","sucess");
                    if (response.getString("status")=="true") {
                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, "Unsuccessful", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(context, "json error "+e.toString(), Toast.LENGTH_SHORT).show();
                    //enable the button
                    Log.d("jsonException",e.toString());
                    e.printStackTrace();
                }}
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Unsucessful",Toast.LENGTH_SHORT).show();
                        Log.d("kkk","Service Error:"+error.toString());


                    }
                })



        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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
        return mypost.size();
    }

    public class Mypostholder extends RecyclerView.ViewHolder{

       TextView details;
       TextView dateandtime;
       TextView likes;
       ImageView postedimg;
       ImageView delete;


        public Mypostholder(@NonNull View itemView) {
            super(itemView);
            dateandtime=itemView.findViewById(R.id.date);
            postedimg=itemView.findViewById(R.id.myimg);
            details=itemView.findViewById(R.id.caption);
            delete=itemView.findViewById(R.id.deletepost);



        }
    }




}
