package pl.polsl.lab.hex2dec.ui.screens.third;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pl.polsl.lab.hex2dec.R;

/**
 * Implementation of adapter (needed because of using RecyclerView). Some kind of bridge between presenter and view.
 *
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class HistoryAdapter extends RecyclerView.Adapter {

    /**
     * Field for presenter.
     */
    private ThirdContract.Presenter presenter;

    /**
     * Field for data source.
     */
    private ArrayList<String> positions;

    /**
     * Field for object of list.
     */
    private RecyclerView recyclerView;

    /**
     * Implementation of view holder pattern (needed because of using RecyclerView).
     *
     * @author Mirosław Ściebura
     * @version 1.0
     */
    private class MenuElementHolder extends RecyclerView.ViewHolder {

        /**
         * Description of object of list.
         */
        public TextView description;

        /**
         * Constructor of holder.
         *
         * @param item view for constructor
         */
        public MenuElementHolder(View item) {
            super(item);
            description = (TextView)item.findViewById(R.id.position);
        }
    }

    /**
     * Constructor of adapter.
     *
     * @param elementsList list of text which will be displayed
     * @param layoutRecyclerView list from layout
     * @param mainPresenter presenter of main activity
     */
    public HistoryAdapter(ArrayList<String> elementsList, RecyclerView layoutRecyclerView, ThirdContract.Presenter mainPresenter){
        presenter = mainPresenter;
        positions = elementsList;
        recyclerView = layoutRecyclerView;
    }

    /**
     * Method executed in case of creating view holder.
     *
     * @param viewGroup ViewGroup for getting context
     * @param i finalized integer for indexing
     * @return RecyclerView.ViewHolder created view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        //layout of article and ViewHolder object
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_layout, viewGroup, false);
        return new MenuElementHolder(view);
    }

    /**
     * Method for binding view holder.
     *
     * @param viewHolder ViewHolder for setting description
     * @param i finalized integer for indexing
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        String name = positions.get(i);
        ((MenuElementHolder) viewHolder).description.setText(name);
    }

    /**
     * Method for counting items.
     *
     * @return int number of items
     */
    @Override
    public int getItemCount() {
        return positions.size();
    }

}
