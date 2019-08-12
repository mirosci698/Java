package pl.polsl.lab.hex2dec.ui.screens.main;

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
public class MenuAdapter extends RecyclerView.Adapter {

    /**
     * Field for presenter.
     */
    private MainContract.Presenter presenter;

    /**
     * Field for data source.
     */
    private ArrayList<String> modes;

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
            description = (TextView)item.findViewById(R.id.mode);
        }
    }

    /**
     * Constructor of adapter.
     *
     * @param elementsList list of text which will be displayed
     * @param layoutRecyclerView list from layout
     * @param mainPresenter presenter of main activity
     */
    public MenuAdapter(ArrayList<String> elementsList, RecyclerView layoutRecyclerView, MainContract.Presenter mainPresenter){
        presenter = mainPresenter;
        modes = elementsList;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_layout, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting index of clicked element
                int position = recyclerView.getChildAdapterPosition(v);
                switch (position) {
                    case 0:
                        presenter.onConvertButtonClicked(position);
                        break;
                    case 1:
                        presenter.onConvertButtonClicked(position);
                        break;
                    case 2:
                        presenter.onHistoryButtonClicked();
                        break;
                    default:
                        break;

                }
            }
        });
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
        String name = modes.get(i);
        ((MenuElementHolder) viewHolder).description.setText(name);
    }

    /**
     * Method for counting items.
     *
     * @return int number of items
     */
    @Override
    public int getItemCount() {
        return modes.size();
    }

}
