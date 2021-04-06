package com.example.android.miwok;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private final int mResourceId;
    public WordAdapter(FragmentActivity context, ArrayList<Word> words, int resourceId) {

        super(context, 0, words);
        mResourceId=resourceId;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }
        Word wordName = getItem(position);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        assert wordName != null;
        nameTextView.setText(wordName.getMiwokTranslation());
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        numberTextView.setText(wordName.getDefaultTranslation());
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.miwok_image);
        if(wordName.hasImage()) {
            iconView.setImageResource(wordName.getImageResource());
            iconView.setVisibility(View.VISIBLE);
        }
        else
        {
            iconView.setVisibility(View.GONE);
        }
        View textContainer = listItemView.findViewById(R.id.text_control);
        int color = ContextCompat.getColor(getContext(), mResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}
