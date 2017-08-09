package br.edu.ufcg.partiu.feed;

import java.util.List;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;
import br.edu.ufcg.partiu.model.Action;

/**
 * Created by lucas on 26/07/17.
 */

public interface FeedContract {

    interface Presenter extends BasePresenter {

        void onRefreshList();
    }

    interface View extends BaseView<Presenter> {

        void setActionList(List<Action> actionList);

        void showLoadingSpinner();

        void hideLoadingSpinner();

        void showEmptyMessage();

        void hideEmptyMessage();

        void showToast(String text);

        void setRefreshing(boolean refreshing);
    }
}
