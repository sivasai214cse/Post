package intern.siva.post;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    TextView nodata;
    private ArrayList<Model3owbpost> mypost =new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_f_tabmy_post, container, false);
        recyclerView3=view.findViewById(R.id.recyclerfomypost);
        nodata=view.findViewById(R.id.noimg);
        recyclerView3.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        requestQueue= Volley.newRequestQueue(getActivity());
        forMydata();
//        ArrayList<Model> post =new ArrayList<>();
//        for(int i=0;i<10;i++)
//        { post.add(new Model("siva sai","R.drawable.mem","already Liked it","2","3hrs"));
//            Postadapter adapter =new Postadapter(getActivity(),post);
//            recyclerView3.setAdapter(adapter);
//        }
        Log.d("for nodat","datano-"+mypost);
if(mypost!=null)
{
    nodata.setVisibility(View.GONE);
}

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
                        String name =hit.getString("details");
                        String image =hit.getString("image");
                        String date =hit.getString("datetime");
                        int id=hit.getInt("id");
                        mypost.add(new Model3owbpost(date,name,String.valueOf(id),image,"3"));


                    }
                    if(jsonArray.length()==0)
                    {
                        Log.d("data","likebug ");
                        Toast.makeText(getContext(),"NO API FOUND",Toast.LENGTH_SHORT).show();
                    }
                    MypostAdapter adapter =new MypostAdapter(getActivity(),mypost);
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