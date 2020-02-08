package com.example.homeautomation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


public class ShowImageFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "imageStr";
    private String imageStr;
    private ImageView ivShow;
    private Bitmap biImage;
//    private Button btnWhite;
//    private Button btnBlack;

    private OnFragmentInteractionListener mListener;

    public ShowImageFragment() {

    }


    public static ShowImageFragment newInstance(Bitmap bitmap) {
        ShowImageFragment fragment = new ShowImageFragment();
        Bundle args = new Bundle();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        args.putByteArray("image",byteArray);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            byte[] byteArray = getArguments().getByteArray("image");
            biImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_show_image, container, false);

        ivShow = (ImageView)v.findViewById(R.id.ivShow);
//        ivShow.setImageURI(Uri.parse(imageStr));
        ivShow.setImageBitmap(biImage);
//        btnWhite = (Button)v.findViewById(R.id.btnWhite);
//        btnBlack = (Button)v.findViewById(R.id.btnBlack);


        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
