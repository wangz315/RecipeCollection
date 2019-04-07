package comp3350.recipecollection.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp3350.recipecollection.R;
import comp3350.recipecollection.presentation.Messages;


public class RecipeAdapter extends ArrayAdapter{

    private List list = new ArrayList();
    public RecipeAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler
    {
        ImageView poster;
        TextView title;
        TextView time;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        DataHandler handler;
        Recipe dataProvider;
        if (convertView == null)
        {
            LayoutInflater inflater  = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_view_recipes,parent,false);
            handler = new DataHandler();
            handler.poster = (ImageView)row.findViewById(R.id.recipePoster);
            handler.title = (TextView)row.findViewById(R.id.recipeTitle);
            handler.time = (TextView)row.findViewById(R.id.recipeTime);
            row.setTag(handler);
        }
        else{
            handler = (DataHandler)row.getTag();
        }

        dataProvider = (Recipe) this.getItem(position);

        handler.poster.setImageResource(dataProvider.getRecipePosterResource());
        handler.title.setText(dataProvider.getName());
        if(dataProvider.getTime() > -1) {
            handler.time.setText(Integer.toString(dataProvider.getTime()));
        }
        else{
            handler.time.setText(Messages.UNKNOWN);
        }

        return row;
    }
}
