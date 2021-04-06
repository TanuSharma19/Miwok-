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


public class PhrasesFragment extends Fragment {

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
        View rootView =inflater.inflate(R.layout.activity_phrases, container, false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("Where are you?","Minto wuksus?",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?","Tinna oyaase'na?",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...","Oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","Kuchi achit?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.","Taachi",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","Aanas'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes I'm coming.","Haa'aanam",R.raw.phrase_yes_im_coming));
        words.add(new Word("Lets's go.","Yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.","Anni'nem",R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter (getActivity(), words,R.color.category_phrases);

        ListView gridView = (ListView)rootView.findViewById(R.id.list3);

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