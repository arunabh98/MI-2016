package com.iitb.moodindigo.mi2016;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;
import com.iitb.moodindigo.mi2016.ServerConnection.RetrofitInterface;
import com.iitb.moodindigo.mi2016.ServerConnection.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenreFragment extends Fragment implements Callback<GsonModels.GenreResponse> {

    private ProgressDialog progressDialog;
    private RecyclerView genreRecyclerView;
    private GenreListAdapter genreListAdapter;

    public GenreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        genreRecyclerView = (RecyclerView) getActivity().findViewById(R.id.genre_list);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Requesting Details");
        RetrofitInterface retrofitInterface = ServiceGenerator.createService(RetrofitInterface.class);
        retrofitInterface.getGenres().enqueue(this);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<GsonModels.GenreResponse> call, Response<GsonModels.GenreResponse> response) {
        if (response.isSuccessful()) {
            GsonModels.GenreResponse genreResponse = response.body();
            Toast.makeText(getContext(), "No. of responses : " + genreResponse.getCount(), Toast.LENGTH_SHORT).show();
            genreListAdapter = new GenreListAdapter(genreResponse.getGenreList(), new ItemCLickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    //TODO: Launch EventsFragment
                }
            });
            genreRecyclerView.setAdapter(genreListAdapter);
            genreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            Toast.makeText(getContext(), "Response code" + response.code(), Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<GsonModels.GenreResponse> call, Throwable t) {
        Toast.makeText(getContext(), "Network error occurred", Toast.LENGTH_LONG).show();
    }
}
