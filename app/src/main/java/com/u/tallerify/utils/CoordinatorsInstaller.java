package com.u.tallerify.utils;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;

/**
 * This class is because the initial view group children wont get bound to their coordinators.
 * An issue has been uploaded to the Coordinator's team, once its resolved maybe it can be removed
 * this class
 *
 * Created by saguilera on 3/12/17.
 */
public final class CoordinatorsInstaller {

    public static void installBinder(@NonNull ViewGroup viewGroup, @NonNull CoordinatorProvider provider) {
        for (int i = 0 ; i < viewGroup.getChildCount() ; ++i) {
            Coordinators.bind(viewGroup.getChildAt(i), provider);
        }
        Coordinators.installBinder(viewGroup, provider);
    }

}
