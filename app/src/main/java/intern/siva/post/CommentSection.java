package intern.siva.post;

import static androidx.recyclerview.widget.RecyclerView.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentSection extends AppCompatActivity {
   ImageView imageload;
   TextView username;
   String id;
   RecyclerView recyclerView2;
   Button commentpost;
   EditText commentbox;
    private ArrayList<Model2> comment =new ArrayList<>();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);
        imageload=findViewById(R.id.imageload);
        username=findViewById(R.id.username);
        commentbox=findViewById(R.id.commentbox);
        commentpost=findViewById(R.id.postcomment);
        String commentfrom=commentbox.getText().toString();
        recyclerView2=findViewById(R.id.recyclerviewComment);

        String image  = getIntent().getExtras().getString("image");
        String usernames=getIntent().getExtras().getString("username");

        id=getIntent().getExtras().getString("id");
        Glide.with(getApplicationContext())
                .load(image)
                .into(imageload);
        username.setText(usernames);

        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommentSection.this);
        recyclerView2.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        requestQueue=Volley.newRequestQueue(CommentSection.this);
        loadComment();
        commentpost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                postcomment();
            }
        });

    }

    private void postcomment() {
      String  apiurl="https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/commentOnPost";
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("id" ,id);
        hashMap.put("detail",commentbox.getText().toString());
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.POST, apiurl, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {  Log.d("sucess","sucess");
                    if (response.getString("status")=="true") {
                        Toast.makeText(CommentSection.this, "posted", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(CommentSection.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(CommentSection.this, "json error "+e.toString(), Toast.LENGTH_SHORT).show();
                    //enable the button
                    Log.d("jsonException",e.toString());
                    e.printStackTrace();
                }}
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CommentSection.this,"Unsucessful",Toast.LENGTH_SHORT).show();
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

    private void loadComment() {

        String url = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/displayPostComment/"+id;
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                     JSONArray jsonArray =response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject hit=jsonArray.getJSONObject(i);
                        String username1 =hit.getString("Comment_username");
                        String usercomment2 =hit.getString("comment");
                        String date =hit.getString("datatime");
                        String id=hit.getString("id");
                        comment.add(new Model2(usercomment2,username1,date,id));

                    }

                    CommentAdapter adapter =new CommentAdapter(CommentSection.this,comment);
                    recyclerView2.setAdapter(adapter);

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

}