package br.edu.ufcg.partiu.model;

/**
 * Created by caiovidal on 07/08/17.
 */

public enum FilterType {
    BY_DISTANCE (1),
    BY_TIME (2);

    private int id;

    FilterType(int id) {
        this.id = id;
    }
}
