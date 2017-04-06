package com.u.tallerify.networking.interactor.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.u.tallerify.utils.RequestCodes;
import com.u.tallerify.utils.RouterInteractor;
import javax.annotation.Nullable;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 4/5/17.
 */

public class LocationInteractor {

    private static final @NonNull LocationInteractor instance = new LocationInteractor();

    @NonNull BehaviorSubject<String> locationSubject;

    @Nullable Subscription providerSubscription;

    private LocationInteractor() {
        locationSubject = BehaviorSubject.create();
    }

    public static @NonNull LocationInteractor instance() {
        return instance;
    }

    public @NonNull Observable<String> observeLocations() {
        // Before observing check if we have the permission + we are listening for it
        Activity activity = RouterInteractor.instance().mainRouter().getActivity();

        int permissionCheck = ContextCompat.checkSelfPermission(activity.getApplicationContext(),
            Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Re run it again for everyone that wants to start observing, so we trigger an updated event :)
            startObservingLocations();
        } else {
            ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                RequestCodes.REQUEST_LOCATION_PERMISSION.value());
        }

        return locationSubject;
    }

    public void postPermissionResults(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.REQUEST_LOCATION_PERMISSION.value() &&
                resultCode == Activity.RESULT_OK) {
            startObservingLocations();
        }
    }

    private void startObservingLocations() {
        if (providerSubscription != null && !providerSubscription.isUnsubscribed()) {
            providerSubscription.unsubscribe();
        }

        Activity activity = RouterInteractor.instance().mainRouter().getActivity();
        providerSubscription = new ReactiveLocationProvider(activity.getApplicationContext())
            .getCurrentPlace(new PlaceFilter())
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            // This wont be composed, it lasts for the whole application lifetime.
            .filter(new Func1<PlaceLikelihoodBuffer, Boolean>() {
                @Override
                public Boolean call(final PlaceLikelihoodBuffer placeLikelihoods) {
                    boolean release = !placeLikelihoods.getStatus().isSuccess();

                    if (release) {
                        placeLikelihoods.release();
                    }

                    return !release;
                }
            })
            .map(new Func1<PlaceLikelihoodBuffer, String>() {
                @Override
                public String call(final PlaceLikelihoodBuffer placeLikelihoods) {
                    Place place = placeLikelihoods.get(0).getPlace();
                    return place.getAddress() + ", " + place.getName();
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(final String s) {
                    locationSubject.onNext(s);
                }
            });
    }

}
