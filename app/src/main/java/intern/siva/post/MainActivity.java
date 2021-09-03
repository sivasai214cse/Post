package intern.siva.post;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String like="already Liked it";
    String apiurl = "https://putatoetest-k3snqinenq-uc.a.run.app";
    private   ArrayList<Model> post =new ArrayList<>();
    ImageView likecheck;
    ImageView postimage;
    Uri imagedata;
    Bitmap bitmap;
    String binaryimgfrmt;

    TabItem tabpost;
    TabItem tabmypost;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    private RequestQueue requestQueue;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler);
        likecheck=findViewById(R.id.like);
        postimage=findViewById(R.id.postimage);
        tabLayout=findViewById(R.id.tabLayout);
        tabpost=findViewById(R.id.tabpost);
        tabmypost=findViewById(R.id.tabmypost);
        viewPager=findViewById(R.id.viewpager);

        //recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        pagerAdapter =new pageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2)
                    pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));




        requestQueue=Volley.newRequestQueue(this);
     //   forUserData();


        postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDilog();
            }
        });

//ArrayList<Model> post =new ArrayList<>();
//for(int i=0;i<10;i++)
//{
//    post.add(new Model("siva sai","R.drawable.mem","already Liked it","2","3hrs"));
//}

    }

    private void showDilog() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_sample);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dilog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button publish =dialog.findViewById(R.id.Publish);
        Button choose =dialog.findViewById(R.id.chooseimage);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(MainActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postimage();
                dialog.dismiss();
            }
        });
        dialog.show();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d( "onActivityResult: ",String.valueOf(resultCode));
        if(resultCode!=10) {
            Uri uri = data.getData();
            imagedata=uri;
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
              //postimage.setImageBitmap(bitmap);
                convertiobinary(bitmap);

                Log.d("binary","binary -"+binaryimgfrmt);
            }catch (Exception e)
            {

            }

        }


    }

    private void convertiobinary(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        binaryimgfrmt=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);

    }

    private void postimage() {
        apiurl=apiurl+"/v1/api/addToPost";
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("image" ,binaryimgfrmt);
        hashMap.put("detail","hello");
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.POST, apiurl, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

             try {  Log.d("sucess","sucess"+imagedata);
                 if (response.getString("error")=="") {
                     Toast.makeText(MainActivity.this, "Successfully published", Toast.LENGTH_SHORT).show();
                 } else
                     Toast.makeText(MainActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

             } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "json error "+e.toString(), Toast.LENGTH_SHORT).show();
                    //enable the button
                    Log.d("jsonException",e.toString());
                    e.printStackTrace();
                }}
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Unsucessful",Toast.LENGTH_SHORT).show();
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

//    public   String forLike(int id)
//
//            {
//              String apiurl = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/postLike/"+id;
//
//                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, apiurl, null, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            try {
//                                JSONObject jsonObject= new JSONObject(response.toString());
//                                like=jsonObject.getString("error");
//
//                            } catch (JSONException e) {
//                                Log.d("Likeapi", "likebug "+e);
//                                Toast.makeText(getApplicationContext(),"NO data fetched",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getApplicationContext(),"NO data fetched",Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                    }){
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            Map<String, String>  params = new HashMap<String, String>();
//                            params.put("authtoken","0P1EYPE7B2OSZ198S2WTVI7BLYCP8J7QV4WCG9FHBXHBMOOD6G");
//                            return params;
//                        }
//                    };
//                request.setRetryPolicy(new DefaultRetryPolicy(
//                        6000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//                requestQueue.add(request);
//
//        return like;
//    }
//    private void forUserData() {
//
//        String url = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/displayPost/0";
//        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    JSONArray jsonArray =response.getJSONArray("data");
//                    for (int i=0;i<jsonArray.length();i++)
//                    {
//                        JSONObject hit=jsonArray.getJSONObject(i);
//                        String name =hit.getString("username");
//                        String image =hit.getString("image");
//                        String date =hit.getString("datetime");
//                        int id=hit.getInt("id");
//                        post.add(new Model(name,image,forLike(id),String.valueOf(id),date));
//
//
//                    }
//                    if(jsonArray.length()==0)
//                    {
//                        Log.d("data","likebug ");
//                        Toast.makeText(MainActivity.this,"NO API FOUND",Toast.LENGTH_SHORT).show();
//                    }
//                    Postadapter adapter =new Postadapter(MainActivity.this,post);
//                    recyclerView.setAdapter(adapter);
//
//                } catch (JSONException e) {
//                   e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                error.printStackTrace();
//
//
//            }
//        });
//   requestQueue.add(request);
//
//    }

}