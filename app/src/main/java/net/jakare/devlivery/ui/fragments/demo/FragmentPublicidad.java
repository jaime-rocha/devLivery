package net.jakare.devlivery.ui.fragments.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import net.jakare.devlivery.R;
import net.jakare.devlivery.ui.activities.MainActivity;
import net.jakare.devlivery.ui.adapters.ImageAdapter;
import net.jakare.devlivery.utils.constants.PublicidadConstants;

public class FragmentPublicidad extends Fragment {

    private MainActivity act;
    private Context context;

    public FragmentPublicidad() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publicidad, container, false);
        context = getActivity();

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new ImageAdapter(context, PublicidadConstants.IMAGES, PublicidadConstants.DESCRIPTIONS));
        pager.setCurrentItem(0, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            act = (MainActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
