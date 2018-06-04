package com.app.pawapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.app.pawapp.DataAccess.DataAccessObject.DaoFactory;
import com.app.pawapp.DataAccess.DataAccessObject.PetDao;
import com.app.pawapp.DataAccess.DataAccessObject.RaceDao;
import com.app.pawapp.DataAccess.DataAccessObject.SpecieDao;
import com.app.pawapp.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class PetFragment extends Fragment {

    private TextInputEditText nameTextInputEditText;
    private TextInputEditText ageTextInputEditText;
    private TextInputEditText descTextInputEditText;
    private ImageView dogImageView;
    private MaterialBetterSpinner mbSpinnerType;
    private MaterialBetterSpinner mbSpinnerRace;

    private PetDao petDao;
    private SpecieDao specieDao;
    private RaceDao raceDao;

    public PetFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        petDao = DaoFactory.getPetDao(getContext());

        View v = inflater.inflate(R.layout.fragment_pet, container, false);

        String[] types = {"Perro","Gato"};
        String[] races = {"Pastor Alemán","Siamés"};

        mbSpinnerType = v.findViewById(R.id.spnTypes);
        mbSpinnerRace = v.findViewById(R.id.spnRaces);

        ArrayAdapter<String> arrayAdapterType= new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, types);
        arrayAdapterType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mbSpinnerType.setAdapter(arrayAdapterType);

        ArrayAdapter<String> arrayAdapterRaces = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, races);
        mbSpinnerRace.setAdapter(arrayAdapterRaces);
        arrayAdapterRaces.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        return v;
    }
}