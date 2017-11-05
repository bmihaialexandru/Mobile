package mihai.spotify2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mihai on 11/5/2017.
 */

public class CustomAdapter extends ArrayAdapter<Song> {

    public CustomAdapter(Context context, ArrayList<Song> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song, parent, false);
        }
        TextView songString= (TextView) convertView.findViewById(R.id.songString);
        songString.setText(song.title+" - "+song.artist);
        return convertView;
    }
}