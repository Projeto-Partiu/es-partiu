package br.edu.ufcg.partiu.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
