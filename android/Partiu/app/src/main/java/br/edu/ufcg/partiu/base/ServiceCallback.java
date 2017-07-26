package br.edu.ufcg.partiu.base;

import retrofit2.Response;

/**
 * Created by Lucas on 17/07/2017.
 */

public interface ServiceCallback<T> {

    void onSuccess(T object, Response<T> response);

    void onError(Throwable error);
}
