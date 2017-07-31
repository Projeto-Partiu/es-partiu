package br.edu.ufcg.partiu.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by ordan on 30/07/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateEventScope {
}
