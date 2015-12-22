package cl.blackbirdhq.drivit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gustavo Salvo Lara on 22-12-2015.
 */
public class ItemModalityAdapter extends BaseAdapter {
    private Context context;
    private List<ItemModality> itemModalityList;

    public ItemModalityAdapter(Context context, List<ItemModality> itemModalityList){
        this.context = context;
        this.itemModalityList = itemModalityList;
    }

    @Override
    public int getCount(){
        return this.itemModalityList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.itemModalityList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_modality, parent, false);
        }

        ImageView image = (ImageView) rowView.findViewById(R.id.iconModality);
        TextView title = (TextView) rowView.findViewById(R.id.textModality);
        TextView desModality = (TextView) rowView.findViewById(R.id.descriptionModality);

        ItemModality itemModality = this.itemModalityList.get(position);
        image.setImageResource(itemModality.getImage());
        title.setText(itemModality.getTitle());
        desModality.setText(itemModality.getDesModality());

        return rowView;
    }
}
