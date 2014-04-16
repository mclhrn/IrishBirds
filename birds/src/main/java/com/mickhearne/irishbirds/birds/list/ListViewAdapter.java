package com.mickhearne.irishbirds.birds.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickhearne.irishbirds.birds.R;
import com.mickhearne.irishbirds.birds.model.Bird;

public class ListViewAdapter extends ArrayAdapter<Bird> implements Filterable {

	Context context;
    List<Bird> birds;
    private List<Bird> origBirdList;
    private Filter birdFilter = null;


    public ListViewAdapter(Context context, List<Bird> birds) {
    	super(context, android.R.id.content, birds);
        this.context = context;
        this.birds = birds;
        this.origBirdList = birds;
    }


    public int getCount() {
		return birds.size();
	}


	public Bird getItem(int position) {
		return birds.get(position);
	}


//	public long getItemId(int position) {
//		return birds.get(position).hashCode();
//	}


    @Override
	public View getView(int position, View convertView, ViewGroup parent) {

    	LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.list_item, null);

        Bird bird = birds.get(position);

        TextView tv = (TextView) view.findViewById(R.id.list_item_name);
        tv.setText(bird.getName());

        tv = (TextView) view.findViewById(R.id.latin);
        tv.setText(bird.getLatinName());

        ImageView iv = (ImageView) view.findViewById(R.id.thumb);
        int imageResource = context.getResources().getIdentifier(
        		bird.getImageThumb(), "drawable", context.getPackageName());
        if (imageResource != 0) {
        	iv.setImageResource(imageResource);
        }

        return view;
	}

    public void resetData() {
		birds = origBirdList;
	}

    @Override
	public Filter getFilter() {
		if (birdFilter == null)
			birdFilter = new BirdFilter();

		return birdFilter;
	}

    private class BirdFilter extends Filter {

		@Override
    	protected FilterResults performFiltering(CharSequence cs) {

    		FilterResults results = new FilterResults();

			if (cs == null || cs.length() == 0) {
				// return full list
				results.values = origBirdList;
				results.count = origBirdList.size();
			}
			else {
				// We perform filtering operation
				List<Bird> nBirdList = new ArrayList<Bird>();

				for (Bird b : birds) {
					if (b.getName().toUpperCase().startsWith(cs.toString().toUpperCase()))
						nBirdList.add(b);
				}
				results.values = nBirdList;
				results.count = nBirdList.size();
			}
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			// inform the adapter about the new list filtered
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				birds = (List<Bird>) results.values;
				notifyDataSetChanged();
			}
		}
	}
}
