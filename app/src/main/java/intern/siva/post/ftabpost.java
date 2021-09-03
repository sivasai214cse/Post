package intern.siva.post;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ftabpost extends Fragment {







    public ftabpost() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    View view;
    String like="already Liked it";
    private RequestQueue requestQueue;
    private ArrayList<Model> post =new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ftabpost, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
         requestQueue= Volley.newRequestQueue(getActivity());

//add();
//        ArrayList<Model> post =new ArrayList<>();
//        for(int i=0;i<10;i++)
//        { post.add(new Model("siva sai","R.drawable.mem","already Liked it","2","3hrs"));
//        }
        forUserData();
    //    Postadapter adapter =new Postadapter(getActivity(),post);
    //    recyclerView.setAdapter(adapter);
        return view;
    }
    public   String forLike(int id)

    {
        String apiurl = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/postLike/"+id;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, apiurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("error")=="") {
                        like="already Liked it";
                        Toast.makeText(getActivity(), "Liked", Toast.LENGTH_SHORT).show();

                    }
                    else like = "not liked";

                } catch (JSONException e) {
                    Log.d("Likeapi", "likebug "+e);
                    Toast.makeText(getContext(),"NO data fetched",Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"NO data fetched",Toast.LENGTH_SHORT).show();

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

        return like;
    }
    private void forUserData() {
        String url = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/displayPost/0";
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray =response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject hit=jsonArray.getJSONObject(i);
                        String name =hit.getString("username");
                        String image =hit.getString("image");
                        String date =hit.getString("datetime");
                        int id=hit.getInt("id");
                        post.add(new Model(name,image,forLike(id),String.valueOf(id),date));


                    }
                    if(jsonArray.length()==0)
                    {
                        Log.d("data","likebug ");
                        Toast.makeText(getContext(),"NO API FOUND",Toast.LENGTH_SHORT).show();
                    }
                    Postadapter adapter =new Postadapter(getActivity(),post);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();


            }
        });
        requestQueue.add(request);
    }

}