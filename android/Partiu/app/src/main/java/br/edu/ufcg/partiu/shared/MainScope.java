package br.edu.ufcg.partiu.shared;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by lucas on 05/08/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface MainScope {
}
