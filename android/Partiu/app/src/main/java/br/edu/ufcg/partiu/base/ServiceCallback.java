package br.edu.ufcg.partiu.base;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * Created by Lucas on 17/07/2017.
 */

public interface ServiceCallback {

    void onSuccess(NetworkResponse response);

    void onError(VolleyError error);
}
