package com.example.android.miwok;

public class Word {
    private final String mDefaultTranslation;
    private final String mMiwokTranslation;
    private  int mImageResourceId =IMAGE_PROVIDE;
    private static final int IMAGE_PROVIDE=-1;
    private final int mAudioResourceId;

    public Word(String defaultTranslation,String miwokTranslation,int audioResourceId)
    {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mAudioResourceId=audioResourceId;
    }

    public Word(String defaultTranslation,String miwokTranslation,int imageResourceId,int audioResourceId)
    {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mImageResourceId=imageResourceId;
        mAudioResourceId=audioResourceId;
    }

    public String getDefaultTranslation()
    {

        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {

        return mMiwokTranslation;
    }
    public int getImageResource()

    {
        return mImageResourceId;
    }
    public boolean hasImage()
    {
        return mImageResourceId !=IMAGE_PROVIDE;
    }
public int getAudioResourceId()
{
    return mAudioResourceId;
}

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
