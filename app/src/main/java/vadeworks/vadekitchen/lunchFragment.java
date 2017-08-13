package vadeworks.vadekitchen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class lunchFragment extends Fragment {

    View view;
    Context context;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lunch, container, false);
        context = getContext();














//        listView = (ListView) view.findViewById(R.id.list);
//        adapter = new CustomListAdapter(context, recipeList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,long id){
//
//                String name = recipeList.get(position).getTitle();
//                String imgUrl = recipeList.get(position).getThumbnailUrl();
//                TextView v = (TextView) view.findViewById(R.id.releaseYear);
//                String x = v.getText().toString();
//
////Like this, can Get any data on item click
//                Intent intent= new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("name",name);
//                intent.putExtra("imgUrl",imgUrl);
//                startActivity(intent);
//            }
//        });
//
//        pDialog = new ProgressDialog(getContext());
//        // Showing progress dialog before making http request
//        pDialog.setIcon(R.mipmap.ic_launcher);
//        pDialog.setTitle("Some Recipe :)");
//        pDialog.setMessage("Loading...");
//        pDialog.show();
//
//        // Creating volley request obj
//        JsonArrayRequest recipeReq = new JsonArrayRequest(url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());
//                        hidePDialog();
//
//                        // Parsing json
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//
//                                JSONObject obj = response.getJSONObject(i);
//                                Recipe recipe = new Recipe();
//                                recipe.setTitle(obj.getString("title"));
//                                recipe.setThumbnailUrl(obj.getString("image"));
//                                recipe.setRating(((Number) obj.get("rating"))
//                                        .doubleValue());
//                                recipe.setYear(obj.getInt("releaseYear"));
//
//                                // Genre is json array
//                                JSONArray genreArry = obj.getJSONArray("genre");
//                                ArrayList<String> genre = new ArrayList<String>();
//                                for (int j = 0; j < genreArry.length(); j++) {
//                                    genre.add((String) genreArry.get(j));
//                                }
//                                recipe.setGenre(genre);
//
//                                // adding movie to movies array
//                                recipeList.add(recipe);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        // notifying list adapter about data changes
//                        // so that it renders the list view with updated data
//
//                        adapter.notifyDataSetChanged();
//                        listView.invalidateViews();
//                        listView.refreshDrawableState();
//                        //mPullToRefreshView.setRefreshing(false);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hidePDialog();
//
//            }
//        });
//
//        AppController.getInstance().addToRequestQueue(recipeReq);


        return view;
    }








//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//    }
//
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        hidePDialog();
//    }
//
//    private void hidePDialog() {
//        if (pDialog != null) {
//            pDialog.dismiss();
//            pDialog = null;
//            //mPullToRefreshView.setRefreshing(false);
//        }
//    }

}
