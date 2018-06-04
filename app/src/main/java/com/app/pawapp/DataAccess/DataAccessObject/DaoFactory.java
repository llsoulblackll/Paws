package com.app.pawapp.DataAccess.DataAccessObject;

import android.content.Context;

public final class DaoFactory {

    private DaoFactory(){
    }

    public static OwnerDao getOwnerDao(Context context){
        return new OwnerDao(context);
    }

    public static SurveyDao getSurveyDao(Context context){
        return new SurveyDao(context);
    }

    public static DistrictDao getDistrictDao(Context context) {
        return new DistrictDao(context);
    }

    public static PetDao getPetDao(Context context){
        return new PetDao(context);
    }

}
