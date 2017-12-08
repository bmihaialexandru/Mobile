package mihai.spotify2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mihai.spotify2.Model.SongEntity;

/**
 * Created by mihai on 11/5/2017.
 */

public class CustomAdapter extends ArrayAdapter<SongEntity> {

    public CustomAdapter(Context context, List<SongEntity> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SongEntity song = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song, parent, false);
        }
        TextView songString= (TextView) convertView.findViewById(R.id.songString);
        songString.setText(song.title+" - "+song.artist+" - "+ MainActivity.genres.get(Integer.parseInt(song.genre)));
        return convertView;
    }
}