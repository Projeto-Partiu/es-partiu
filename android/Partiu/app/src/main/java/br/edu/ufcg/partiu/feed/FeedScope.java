package br.edu.ufcg.partiu.feed;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by lucas on 26/07/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FeedScope {
}
