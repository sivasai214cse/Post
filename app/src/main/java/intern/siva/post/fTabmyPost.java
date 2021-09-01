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

public class fTabmyPost extends Fragment {



    public fTabmyPost() {
        // Required empty public constructor
    }

    View view;
    RecyclerView recyclerView3;
    private RequestQueue requestQueue;
    private ArrayList<Model> post =new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ftabpost, container, false);
        recyclerView3=view.findViewById(R.id.recycler);
        recyclerView3.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        requestQueue= Volley.newRequestQueue(getActivity());
        forMydata();
        return inflater.inflate(R.layout.fragment_f_tabmy_post, container, false);

    }

    private void forMydata() { {
        String url = "https://putatoetest-k3snqinenq-uc.a.run.app/v1/api/displayOwnPost/5";
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
                        post.add(new Model(name,image,"none",String.valueOf(id),date));


                    }
                    if(jsonArray.length()==0)
                    {
                        Log.d("data","likebug ");
                        Toast.makeText(getContext(),"NO API FOUND",Toast.LENGTH_SHORT).show();
                    }
                    Postadapter adapter =new Postadapter(getActivity(),post);
                    recyclerView3.setAdapter(adapter);

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

}