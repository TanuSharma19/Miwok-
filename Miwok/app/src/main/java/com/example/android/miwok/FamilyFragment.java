package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyFragment extends Fragment {
    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    private final MediaPlayer.OnCompletionListener mOnCompleteListner= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.activity_family, container, false);
        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("Father","Apa",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("Mother","Ato",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("Son","Angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("Daughter","Tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("Older Brother","Taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("Younger Brother","Chalatti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("Older Sister","Tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("Younger Sister","Kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("GrandMother","Omo",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("GrandFather","Poopo",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter (getActivity(), words,R.color.category_family);

        ListView gridView = (ListView)rootView.findViewById(R.id.list2);

        assert gridView != null;
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompleteListner);
                }
            }

        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void releaseMediaPlayer() {

        if (mediaPlayer != null) {

            mediaPlayer.release();


            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}