package intern.siva.post;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fTabmyPost extends Fragment {



    public fTabmyPost() {
    }

    View view;
    RecyclerView recyclerView3;
    private RequestQueue requestQueue;
    TextView nodata;
     ArrayList<Model3owbpost> myposts =new ArrayList<>();
    public SwipeRefreshLayout refreshmypost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_f_tabmy_post, container, false);
        recyclerView3=view.findViewById(R.id.recyclerfomypost);
        recyclerView3.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        requestQueue= Volley.newRequestQueue(getActivity());
        refreshmypost=view.findViewById(R.id.refreshmypost);
        forMydata();
        refreshmypost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myposts.clear();
                forMydata();
                recyclerView3.getAdapter().notifyDataSetChanged();
                refreshmypost.setRefreshing(false);
            }
        });



        return view;

    }

    private void forMydata()  {
        String url = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/displayOwnPost/0";
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray =response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++)
                    {


                        JSONObject hit=jsonArray.getJSONObject(i);
                        String details =hit.getString("detail");
                        String image =hit.getString("image");
                        String date =hit.getString("datetime");
                        String id=hit.getString("id");
                        Log.d("id","id"+id);
                        String likes=hit.getString("total_like");
                      //  byte[] bytes= Base64.decode(image,Base64.DEFAULT);
                       // Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                        myposts.add(new Model3owbpost(date,details,id,image,likes));


                    }
                    if(jsonArray.length()==0)
                    {
                        Log.d("data","likebug ");
                        Toast.makeText(getContext(),"NO API FOUND",Toast.LENGTH_SHORT).show();
                    }
                        MypostAdapter adapter = new MypostAdapter(getActivity(), myposts);
                        recyclerView3.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


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